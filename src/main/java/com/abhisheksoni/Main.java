package com.abhisheksoni;

import java.util.Date;

import com.abhisheksoni.dagexecutor.DagExecutors;
import com.abhisheksoni.dagexecutor.model.DagExecutionMode;
import com.abhisheksoni.graph.model.TaskGraph;
import com.abhisheksoni.util.Constant;

public class Main {
    public static void main(String[] args) {
        Runnable t1 = createANewTask(2);
        Runnable t2 = createANewTask(1);
        Runnable t3 = createANewTask(3);
        Runnable t4 = createANewTask(1);
        Runnable t5 = createANewTask(1);

        new TaskGraph.Builder()
            .addNodes(t1, t2, t3, t4, t5)
            .addEdge(t1, t2)
            .addEdge(t1, t3)
            .addEdge(t2, t4)
            .addEdge(t3, t4)
            .addEdge(t4, t5)
            .build().execute();


        long startTime = new Date().getTime();
        DagExecutors.executeTasksFromFileName(Constant.INPUT_FILE_NAME, DagExecutionMode.PARALLEL);
        long endTime = new Date().getTime();
        float executionTime = (endTime - startTime) / 1000;
        System.out.println("Execution time: " + executionTime + " seconds");
    }

    private static Runnable createANewTask(int sleepTimeInSeconds) {
        return () -> {
            try {
                Thread.sleep(sleepTimeInSeconds * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException("Sleeping thread interrupted " + e.getMessage());
            }
        };
    }
}
