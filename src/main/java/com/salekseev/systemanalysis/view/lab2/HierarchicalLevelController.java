package com.salekseev.systemanalysis.view.lab2;

import com.salekseev.systemanalysis.Main;
import com.salekseev.systemanalysis.model.Graph;
import com.salekseev.systemanalysis.model.Subgraph;
import com.salekseev.systemanalysis.view.lab3.TopologicalDecompositionView;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HierarchicalLevelController {

    private final HierarchicalLevelView view;
    private final Graph graph = new Graph();

    public HierarchicalLevelController(HierarchicalLevelView view) {
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
        Graph graph = new Graph().setAdjacencyMatrix(getAdjacencyMatrix()).setOriented(true);
        Map<Integer, Map<Integer, Integer>> levels = graph.getMapLevels();

        int count = 3;
        for (var entry : levels.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();
            Label label = new Label("Иерархический уровень %d".formatted(key));
            view.resultMatrixPane.add(label, 0, count + key);
            count++;

            for (var entry2 : value.entrySet()) {
                var key2 = entry2.getKey();
                var value2 = entry2.getValue();

                Label label2 = new Label("%d (%d)".formatted(value2, key2));
                view.resultMatrixPane.add(label2, 0, count + key);
                count++;
            }
        }

        view.resultMatrixPane.setVisible(true);

        Integer[][] adjacencyMatrix = graph.getNewAdjacencyMatrix(levels);

        Integer[][] incidentMatrix = graph.setOriented(true).setAdjacencyMatrix(adjacencyMatrix).transformToIncidentMatrix();

        int n = adjacencyMatrix[0].length;
        int m = incidentMatrix[0].length;
        for (var i = 0; i < n; i++) {
            for (var j = 0; j < m; j++) {
                TextField cell = view.createCellNoneValid();
                if (incidentMatrix[i][j] == 2) {
                    cell.setText("+-1");
                } else {
                    cell.setText(String.valueOf(incidentMatrix[i][j]));
                }
                view.subgraphMatrix.add(cell, j, i);
            }
        }
    }

    private void bindFields() {
        view.calculateButton.disableProperty().bind(view.matrix.visibleProperty().not());
    }

    private void clearPane() {
        view.resultMatrixPane.setVisible(false);
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
