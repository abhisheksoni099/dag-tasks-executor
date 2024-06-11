package com.abhisheksoni.graph.dataconverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import com.abhisheksoni.graph.model.GraphData;
import com.abhisheksoni.task.SleepTask;
import com.abhisheksoni.task.Task;

public class FileInputToGraphDataConverter implements InputToGraphDataConverter<File> {
    private static final String TASK_SUFFIX = "task:";
    private static final String EDGE_SUFFIX = "edge:";
    private static final String EDGE_DELIMITER = " -> ";

    @Override
    public GraphData toGraphData(File file) {
        Map<String, ArrayList<Task>> adjacencyGraph = new ConcurrentHashMap<>();
        Map<String, Task> tasksByName = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(TASK_SUFFIX)) {
                    parseInputLineAndAddSleepTask(line, tasksByName);
                } else if (line.startsWith(EDGE_SUFFIX)) {
                    parseInputLineAndAddAdjacencyGraphEdge(line, adjacencyGraph, tasksByName);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read the input file");
        }
        return new GraphData(adjacencyGraph, tasksByName);
    }

    private void parseInputLineAndAddSleepTask(String line, Map<String, Task> tasksByName) {
        StringTokenizer stringTokenizer = new StringTokenizer(line.substring(TASK_SUFFIX.length()));
        String taskName = stringTokenizer.nextToken();
        int sleepTimeInSeconds = Integer.valueOf(stringTokenizer.nextToken());
        SleepTask sleepTask = new SleepTask(taskName, sleepTimeInSeconds);
        tasksByName.put(taskName, sleepTask);
    }

    private void parseInputLineAndAddAdjacencyGraphEdge(String line, Map<String, ArrayList<Task>> adjacencyGraph, Map<String, Task> tasksByName) {
        StringTokenizer stringTokenizer = new StringTokenizer(line.substring(EDGE_SUFFIX.length()), EDGE_DELIMITER);
        String taskAName = stringTokenizer.nextToken();
        String taskBName = stringTokenizer.nextToken();
        adjacencyGraph.putIfAbsent(taskAName, new ArrayList<>());
        adjacencyGraph.putIfAbsent(taskBName, new ArrayList<>());
        adjacencyGraph.get(taskAName).add(tasksByName.get(taskBName));
    }
}
