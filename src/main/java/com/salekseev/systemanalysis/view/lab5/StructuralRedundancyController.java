package com.salekseev.systemanalysis.view.lab5;

import com.salekseev.systemanalysis.model.Graph;
import com.salekseev.systemanalysis.view.lab4.ShortestPathView;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class StructuralRedundancyController {

    private final StructuralRedundancyView view;
    private final Graph graph = new Graph();

    public StructuralRedundancyController(StructuralRedundancyView view) {
        this.view = view;
        this.initialize();
    }

    private void initialize() {
        view.generateMatrixButton.setOnAction(this::generateMatrixButtonOnAction);
        view.calculateButton.setOnAction(this::calculateButtonOnAction);
        bindFields();
    }

    private void generateMatrixButtonOnAction(ActionEvent event) {
        clearPane();
        view.matrix.getChildren().clear();
        int n = view.sizeMatrix.getValue();
        for (var i = 0; i < n; i++) {
            for (var j = 0; j < n; j++) {
                TextField cell = view.createCell();
                cell.setText(String.valueOf(0));
                view.matrix.add(cell, j, i);
            }
        }
        view.adjacencyMatrixLabel.setVisible(true);
        view.matrix.setVisible(true);
    }

    private void calculateButtonOnAction(ActionEvent event) {
        clearPane();
        double[] result = graph.setAdjacencyMatrix(getAdjacencyMatrix())
                .setOriented(view.orientedCheckBox.isSelected())
                .structuralRedundancy();

        view.RLabel.setText("R = " + result[0]);
        view.e2Label.setText("e2 = " + result[1]);

        view.RLabel.setVisible(true);
        view.e2Label.setVisible(true);
    }

    private void bindFields() {
        view.calculateButton.disableProperty().bind(view.matrix.visibleProperty().not());
        view.orientedCheckBox.disableProperty().bind(view.matrix.visibleProperty().not());
    }

    private void clearPane() {
        view.RLabel.setVisible(false);
        view.e2Label.setVisible(false);
    }

    private Integer[][] getAdjacencyMatrix() {
        int n = view.sizeMatrix.getValue();
        TextField[][] gridPaneArray = initializeGridPaneArray();

        Integer[][] adjacencyMatrix = new Integer[n][n];

        for (var i = 0; i < n; i++) {
            for (var j = 0; j < n; j++) {
                if (gridPaneArray[i][j].getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Ошибка! Не все ячейки матрицы заполнены!");
                    alert.show();
                }
                int value = Integer.parseInt(gridPaneArray[i][j].getText());
                adjacencyMatrix[i][j] = value;
            }
        }

        return adjacencyMatrix;
    }

    private TextField[][] initializeGridPaneArray() {
        int n = view.sizeMatrix.getValue();
        TextField[][] gridPaneArray = new TextField[n][n];
        for(Node node : view.matrix.getChildren()) {
            gridPaneArray[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = (TextField) node;
        }

        return gridPaneArray;
    }

}
