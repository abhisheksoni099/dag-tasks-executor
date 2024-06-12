package com.abhisheksoni.dagexecutor;

import com.abhisheksoni.dagexecutor.model.DagExecutionMode;

public class DagExecutorFactory {
    public static DagExecutor getInstance(DagExecutionMode executorMode) {
        switch (executorMode) {
            case SERIAL:
                return new SerialDagExecutor();
            case PARALLEL:
                return new ParallelDagExecutor();
            default:
                throw new RuntimeException("Invalid exeutor mode provided");
        }
    }
}
