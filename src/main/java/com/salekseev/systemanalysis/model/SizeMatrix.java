package com.salekseev.systemanalysis.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class SizeMatrix {

    private final ObservableList<Integer> sizeMatrix;

    public SizeMatrix() {
        this.sizeMatrix = FXCollections.observableArrayList(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    public ObservableList<Integer> getSizeMatrix() {
        return sizeMatrix;
    }

}
