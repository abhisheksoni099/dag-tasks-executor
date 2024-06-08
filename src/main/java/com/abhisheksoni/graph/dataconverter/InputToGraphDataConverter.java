package com.abhisheksoni.graph.dataconverter;

import com.abhisheksoni.graph.model.GraphData;

public interface InputToGraphDataConverter<T> {
    GraphData toGraphData(T input);
}
