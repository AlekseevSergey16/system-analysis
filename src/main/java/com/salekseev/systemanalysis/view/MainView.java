package com.salekseev.systemanalysis.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.function.UnaryOperator;

public class MainView extends StackPane {

    GridPane matrix;
    Button generateMatrixButton;
    Button incidentMatrixButton;
    ComboBox<Integer> sizeMatrix;
    CheckBox orientedCheckBox;
    Label adjacencyMatrixLabel;
    Label incidentMatrixLabel;
    VBox mainPane;

    GridPane incidentMatrix;
    ListView<String> rightsIncidentsList;
    Label rightsIncidentsLabel;

    private UnaryOperator<TextFormatter.Change> numberValidationFormatter;

    public MainView() {
        mainPane = createView();
        getChildren().add(mainPane);
        initValidation();
    }

    private VBox createView() {
        matrix = new GridPane();
        matrix.setPadding(new Insets(0, 0, 0, 5));
        adjacencyMatrixLabel = new Label("Матрица Смежности");
        adjacencyMatrixLabel.setVisible(false);
        adjacencyMatrixLabel.setPadding(new Insets(0, 0, 0, 5));

        return new VBox(5, createTopBar(), adjacencyMatrixLabel, matrix, createResultMatrixPane());
    }

    private HBox createTopBar() {
        generateMatrixButton = new Button("Сгенерировать");
        sizeMatrix = new ComboBox<>();

        orientedCheckBox = new CheckBox("Ориентированный");
        orientedCheckBox.setAlignment(Pos.CENTER);
        orientedCheckBox.setPadding(new Insets(2.5, 0, 0, 0));
        orientedCheckBox.setDisable(true);

        incidentMatrixButton = new Button("Перевести");
        incidentMatrixButton.setDefaultButton(true);
        incidentMatrixButton.setDisable(true);

        HBox controls = new HBox(5, generateMatrixButton, sizeMatrix, orientedCheckBox, incidentMatrixButton);
        controls.setPadding(new Insets(5, 5, 5, 5));

        return controls;
    }

    private GridPane createResultMatrixPane() {
        incidentMatrixLabel = new Label("Матрица Инцидентности");
        incidentMatrixLabel.setVisible(false);

        rightsIncidentsLabel = new Label("Множество Правых Инциденций");
        rightsIncidentsLabel.setVisible(false);

        incidentMatrix = new GridPane();
        rightsIncidentsList = new ListView<>();
        rightsIncidentsList.setPrefSize(50, 50);
        rightsIncidentsList.setVisible(false);

        GridPane resultMatrixPane = new GridPane();
        resultMatrixPane.setHgap(10);
        resultMatrixPane.setPadding(new Insets(5, 5, 5, 5));
        resultMatrixPane.add(incidentMatrixLabel, 0, 0);
        resultMatrixPane.add(incidentMatrix, 0, 1);
        resultMatrixPane.add(rightsIncidentsLabel, 1, 0);
        resultMatrixPane.add(rightsIncidentsList, 1, 1);

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
