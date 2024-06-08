package com.abhisheksoni.graph.model;

import java.util.ArrayList;
import java.util.Map;

import com.abhisheksoni.task.Task;

public record GraphData(Map<String, ArrayList<Task>> adjacencyGraph, Map<String, Task> tasksByName) {}
