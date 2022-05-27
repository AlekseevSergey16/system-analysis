package com.salekseev.systemanalysis.view;

import com.salekseev.systemanalysis.model.Graph;
import com.salekseev.systemanalysis.model.SizeMatrix;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.List;

public class MainController {

    private final SizeMatrix sizeMatrix;
    private final Graph graph;
    private final MainView view;

    public MainController(SizeMatrix sizeMatrix, Graph graph, MainView mainView) {
        this.sizeMatrix = sizeMatrix;
        this.graph = graph;
        this.view = mainView;
        loadSizeMatrix();
    }

    public void initialize() {
        view.generateMatrixButton.setOnAction(this::onGenerateMatrixButtonAction);
        view.incidentMatrixButton.setOnAction(this::onIncidentMatrixButtonAction);
    }

    public void onGenerateMatrixButtonAction(ActionEvent event) {
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
        view.incidentMatrixButton.setDisable(false);
        view.orientedCheckBox.setDisable(false);
    }

    public void onIncidentMatrixButtonAction(ActionEvent event) {
        clearPane();
        Integer[][] incidentMatrix = graph.setAdjacencyMatrix(getAdjacencyMatrix())
                .setOriented(view.orientedCheckBox.isSelected())
                .transformToIncidentMatrix();
        showIncidentMatrix(incidentMatrix);

        List<List<Integer>> rightsIncidents = graph.transformToRightsIncidents();
        showRightsIncidents(rightsIncidents);
    }

    private void showIncidentMatrix(Integer[][] incidentMatrix) {
        int n = view.sizeMatrix.getValue();
        int m = incidentMatrix[0].length;
        for (var i = 0; i < n; i++) {
            for (var j = 0; j < m; j++) {
                TextField cell = view.createCellNoneValid();
                if (incidentMatrix[i][j] == 2) {
                    cell.setText("+-1");
                } else {
                    cell.setText(String.valueOf(incidentMatrix[i][j]));
                }
                view.incidentMatrix.add(cell, j, i);
            }
        }
        view.incidentMatrixLabel.setVisible(true);
    }

    private void showRightsIncidents(List<List<Integer>> rightsIncidents) {
        int i = 0;
        for (List<Integer> valueList : rightsIncidents) {
            view.rightsIncidentsList.getItems().add("G(%d)=".formatted(i + 1) + " " + valueList.toString());
            i++;
        }
        view.rightsIncidentsLabel.setVisible(true);
        view.rightsIncidentsList.setVisible(true);
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

    private void loadSizeMatrix() {
        view.sizeMatrix.setItems(sizeMatrix.getSizeMatrix());
        view.sizeMatrix.getSelectionModel().select(0);
    }

    private void clearPane() {
        view.adjacencyMatrixLabel.setVisible(true);

        view.incidentMatrix.getChildren().clear();
        view.incidentMatrixLabel.setVisible(false);

        view.rightsIncidentsList.getItems().clear();
        view.rightsIncidentsLabel.setVisible(false);
        view.rightsIncidentsList.setVisible(false);
    }

}
