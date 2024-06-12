package com.abhisheksoni.graph.model;

import java.util.ArrayList;
import java.util.List;

import com.abhisheksoni.dagexecutor.model.DagExecutionMode;

public class TaskGraph {
    public TaskGraph(Builder builder) {
    }

    public void execute(DagExecutionMode dagExecutionMode) {
    }

    public void execute() {
    }

    public static class Builder {
        private List<Runnable> runnables = new ArrayList<>();

        public Builder addNode(Runnable runnable) {
            runnables.add(runnable);
            return this;
        }

        public Builder addNodes(Runnable... runnable) {
            runnables.addAll(List.of(runnable));
            return this;
        }

        public Builder addEdge(Runnable runnable1, Runnable runnable2) {
            return this;
        }

        public TaskGraph build() {
            return new TaskGraph(this);
        }
    }
}
