package com.salekseev.systemanalysis.view.lab5;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.UnaryOperator;

public class StructuralRedundancyView extends StackPane {

    GridPane matrix;
    Button generateMatrixButton;
    ComboBox<Integer> sizeMatrix;
    CheckBox orientedCheckBox;
    Button calculateButton;

    Label adjacencyMatrixLabel;
    VBox mainPane;

    Label RLabel = new Label();
    Label e2Label = new Label();

    private UnaryOperator<TextFormatter.Change> numberValidationFormatter;

    public StructuralRedundancyView() {
        mainPane = createView();
        getChildren().add(mainPane);
        initValidation();
    }

    private VBox createView() {
        matrix = new GridPane();
        matrix.setPadding(new Insets(0, 0, 0, 5));
        matrix.setVisible(false);
        adjacencyMatrixLabel = new Label("Матрица Смежности");
        adjacencyMatrixLabel.setVisible(false);
        adjacencyMatrixLabel.setPadding(new Insets(0, 0, 0, 5));

        RLabel.setPadding(new Insets(0, 0, 0, 5));
        e2Label.setPadding(new Insets(0, 0, 0, 5));

        RLabel.setVisible(false);
        e2Label.setVisible(false);

        return new VBox(5, createTopBar(), adjacencyMatrixLabel, matrix, RLabel, e2Label);
    }

    private HBox createTopBar() {
        generateMatrixButton = new Button("Сгенерировать");
        sizeMatrix = new ComboBox<>();
        sizeMatrix.setItems(FXCollections.observableArrayList(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));

        orientedCheckBox = new CheckBox("Ориентированный");
        orientedCheckBox.setAlignment(Pos.CENTER);
        orientedCheckBox.setPadding(new Insets(2.5, 0, 0, 0));
        orientedCheckBox.setDisable(true);

        calculateButton = new Button("Рассчитать");
        calculateButton.setDefaultButton(true);

        HBox controls = new HBox(5, generateMatrixButton, sizeMatrix, orientedCheckBox, calculateButton);
        controls.setPadding(new Insets(5, 5, 5, 5));

        return controls;
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
