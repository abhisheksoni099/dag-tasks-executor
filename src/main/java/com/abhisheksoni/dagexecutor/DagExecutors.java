package com.abhisheksoni.dagexecutor;

import java.io.File;

import com.abhisheksoni.dagexecutor.model.ExecutorMode;
import com.abhisheksoni.graph.dataconverter.FileInputToGraphDataConverter;
import com.abhisheksoni.graph.dataconverter.InputToGraphDataConverter;
import com.abhisheksoni.graph.model.GraphData;
import com.abhisheksoni.util.Constant;

public class DagExecutors {
    public static void executeTasksFromFileName(String fileName, ExecutorMode executorMode) {
        InputToGraphDataConverter<File> inputToGraphConverter =  new FileInputToGraphDataConverter();
        File file = new File(Constant.INPUT_BASE_LOCATION + fileName);
        GraphData graphData = inputToGraphConverter.toGraphData(file);
        DagExecutor dagExecutor = DagExecutorFactory.getInstance(executorMode);
        dagExecutor.execute(graphData);
    }
}
