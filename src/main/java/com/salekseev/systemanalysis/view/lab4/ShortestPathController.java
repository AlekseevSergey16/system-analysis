package com.salekseev.systemanalysis.view.lab4;

import com.salekseev.systemanalysis.model.Graph;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ShortestPathController {

    private final ShortestPathView view;
    private final Graph graph = new Graph();

    public ShortestPathController(ShortestPathView view) {
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
        Integer[][] shortestPathMatrix = graph.setAdjacencyMatrix(getAdjacencyMatrix())
                .calculateShortestPath();

        int n = view.sizeMatrix.getValue();
        int m = shortestPathMatrix[0].length;
        for (var i = 0; i < n; i++) {
            for (var j = 0; j < m; j++) {
                TextField cell = view.createCellNoneValid();
                cell.setText(String.valueOf(shortestPathMatrix[i][j]));
                view.shortestPathMatrix.add(cell, j, i);
            }
        }
        view.resultMatrixPane.setVisible(true);
    }

    private void bindFields() {
        view.calculateButton.disableProperty().bind(view.matrix.visibleProperty().not());
    }

    private void clearPane() {
        view.adjacencyMatrixLabel.setVisible(false);
        view.resultMatrixPane.setVisible(false);
        view.shortestPathMatrix.getChildren().clear();
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
