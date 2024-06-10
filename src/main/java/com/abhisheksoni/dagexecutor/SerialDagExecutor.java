package com.abhisheksoni.dagexecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import com.abhisheksoni.graph.model.GraphData;
import com.abhisheksoni.task.BaseTask;
import com.abhisheksoni.task.Task;

public class SerialDagExecutor implements DagExecutor {
    @Override
    public void execute(GraphData graphData) {
        Map<String, Integer> dependenciesByTaskName = new HashMap<>();
        for (Entry<String, ArrayList<Task>> entrySet : graphData.adjacencyGraph().entrySet()) {
            entrySet.getValue().forEach(taskB -> {
                String taskAName = entrySet.getKey();
                String taskBName = ((BaseTask) taskB).getName();
                dependenciesByTaskName.putIfAbsent(taskAName, 0);
                dependenciesByTaskName.putIfAbsent(taskBName, 0);
                dependenciesByTaskName.put(taskBName, dependenciesByTaskName.get(taskBName) + 1);
            });
        }
        Queue<String> queue = new LinkedList<>();
        for (Entry<String, Integer> taskDependencies : dependenciesByTaskName.entrySet()) {
            if (taskDependencies.getValue().equals(0)) {
                queue.add(taskDependencies.getKey());
            }
        }
        while (!queue.isEmpty()) {
            String currentTaskName = queue.remove();
            Task currentTask = graphData.tasksByName().get(currentTaskName);
            currentTask.run();
            graphData.adjacencyGraph().get(currentTaskName).forEach(neighbouringTask -> {
                String neighbouringTaskName = ((BaseTask) neighbouringTask).getName();
                int neighbouringTaskNewDependency = dependenciesByTaskName.get(neighbouringTaskName) - 1;
                dependenciesByTaskName.put(neighbouringTaskName, neighbouringTaskNewDependency);
                if (neighbouringTaskNewDependency == 0) {
                    queue.add(neighbouringTaskName);
                }
            });
        }
    }
}
