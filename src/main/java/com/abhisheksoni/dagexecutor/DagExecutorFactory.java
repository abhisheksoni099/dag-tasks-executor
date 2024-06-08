package com.abhisheksoni.dagexecutor;

import com.abhisheksoni.dagexecutor.model.ExecutorMode;

public class DagExecutorFactory {
    public static DagExecutor getInstance(ExecutorMode executorMode) {
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
