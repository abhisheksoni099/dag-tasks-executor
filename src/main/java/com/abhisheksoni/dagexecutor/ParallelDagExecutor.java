package com.abhisheksoni.dagexecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Queue;

import com.abhisheksoni.graph.model.GraphData;
import com.abhisheksoni.task.BaseTask;
import com.abhisheksoni.task.Task;

public class ParallelDagExecutor implements DagExecutor {
    private ExecutorService executorService;

    public ParallelDagExecutor() {
        this.executorService = Executors.newCachedThreadPool();
    }

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
            CountDownLatch latch = new CountDownLatch(queue.size());
            List<String> taskNamesForExecution = new ArrayList<>(queue.size());
            while (!queue.isEmpty()) {
                taskNamesForExecution.add(queue.remove());
            }
            for (String currentTaskName : taskNamesForExecution) {
                Task currentTask = graphData.tasksByName().get(currentTaskName);
                executeTask(currentTaskName, currentTask, dependenciesByTaskName, graphData, queue, latch);
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException("Running tasks interrupted");
            }
        }
    }

    private void executeTask(String taskName, Task task, Map<String, Integer> dependenciesByTaskName, GraphData graphData, Queue<String> queue, CountDownLatch latch) {
        this.executorService.submit(() -> {
            task.run();
            resolveDependencies(taskName, task, dependenciesByTaskName, graphData, queue, latch);
        });
    }

    private void resolveDependencies(String taskName, Task task, Map<String, Integer> dependenciesByTaskName, GraphData graphData, Queue<String> queue, CountDownLatch latch) {
        graphData.adjacencyGraph().get(taskName).forEach(neighbouringTask -> {
            String neighbouringTaskName = ((BaseTask) neighbouringTask).getName();
            int neighbouringTaskNewDependency = dependenciesByTaskName.get(neighbouringTaskName) - 1;
            dependenciesByTaskName.put(neighbouringTaskName, neighbouringTaskNewDependency);
            if (neighbouringTaskNewDependency == 0) {
                queue.add(neighbouringTaskName);
            }
        });
        latch.countDown();
    }
}
