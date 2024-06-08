package com.abhisheksoni;

import java.util.Date;

import com.abhisheksoni.dagexecutor.DagExecutors;
import com.abhisheksoni.dagexecutor.model.ExecutorMode;
import com.abhisheksoni.util.Constant;

public class Main {
    public static void main(String[] args) {
        long startTime = new Date().getTime();
        DagExecutors.executeTasksFromFileName(Constant.INPUT_FILE_NAME, ExecutorMode.SERIAL);
        long endTime = new Date().getTime();
        float executionTime = (endTime - startTime) / 1000;
        System.out.println("Execution time: " + executionTime + " seconds");
    }
}
