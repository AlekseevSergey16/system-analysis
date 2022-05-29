package com.salekseev.systemanalysis.view.lab3;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.UnaryOperator;

public class TopologicalDecompositionView extends ScrollPane {

    Button generateMatrixButton;
    ComboBox<Integer> sizeMatrix;
    Button calculateButton;

    Label adjacencyMatrixLabel;
    GridPane matrix;

    Label decompositionLabel;
    ListView<String> subgraphListView;

    Label incidentLabel;
    GridPane subgraphMatrix;

    GridPane resultMatrixPane;

    VBox mainPane;

    private UnaryOperator<TextFormatter.Change> numberValidationFormatter;

    public TopologicalDecompositionView() {
        mainPane = createView();
        this.setContent(mainPane);
        initValidation();
    }

    private VBox createView() {
        matrix = new GridPane();
        matrix.setPadding(new Insets(0, 0, 0, 5));
        matrix.setVisible(false);
        adjacencyMatrixLabel = new Label("Матрица Смежности");
        adjacencyMatrixLabel.setVisible(false);
        adjacencyMatrixLabel.setPadding(new Insets(0, 0, 0, 5));

        return new VBox(5, createTopBar(), adjacencyMatrixLabel, matrix, createResultMatrixPane());
    }

    private HBox createTopBar() {
        generateMatrixButton = new Button("Сгенерировать");
        sizeMatrix = new ComboBox<>();
        sizeMatrix.setItems(FXCollections.observableArrayList(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
        sizeMatrix.getSelectionModel().select(0);

        calculateButton = new Button("Рассчитать");
        calculateButton.setDefaultButton(true);

        HBox controls = new HBox(5, generateMatrixButton, sizeMatrix, calculateButton);
        controls.setPadding(new Insets(5, 5, 5, 5));

        return controls;
    }

    private GridPane createResultMatrixPane() {
        decompositionLabel = new Label("Топологическая декомпозиция");
        subgraphListView = new ListView<>();
        subgraphListView.setPrefSize(400, 155);

        resultMatrixPane = new GridPane();
        resultMatrixPane.setHgap(10);
        resultMatrixPane.setPadding(new Insets(5, 5, 5, 5));

        incidentLabel = new Label("Матрица Инцидентности");
        subgraphMatrix = new GridPane();

        resultMatrixPane.add(decompositionLabel, 0, 0);
        resultMatrixPane.add(subgraphListView, 0, 1);
        resultMatrixPane.add(incidentLabel, 1, 0);
        resultMatrixPane.add(subgraphMatrix, 1, 1);

        resultMatrixPane.setVisible(false);

        return resultMatrixPane;
    }

    public TextField createCell() {
        TextField cell = new TextField();
        cell.setPrefHeight(30);
        cell.setPrefWidth(50);
        cell.setAlignment(Pos.CENTER);
        cell.setEditable(true);
        cell.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        return cell;
    }

    public TextField createCellNoneValid() {
        TextField cell = new TextField();
        cell.setPrefHeight(30);
        cell.setPrefWidth(50);
        cell.setAlignment(Pos.CENTER);
        cell.setEditable(false);
        return cell;
    }

    private void initValidation() {
        numberValidationFormatter = change -> {
            if (change.getText().matches("^-?\\d+$") || change.getText().equals("")){
                return change;
            } else {
                change.setText("");
                change.setRange(change.getRangeStart(), change.getRangeStart());
                return change;
            }
        };
    }

}
