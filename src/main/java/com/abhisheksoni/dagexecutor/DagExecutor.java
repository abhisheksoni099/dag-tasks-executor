package com.abhisheksoni.dagexecutor;

import com.abhisheksoni.graph.model.GraphData;

public interface DagExecutor {
    void execute(GraphData graphData);
}
