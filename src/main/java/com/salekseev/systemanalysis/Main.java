package com.salekseev.systemanalysis;

import com.salekseev.systemanalysis.model.SizeMatrix;
import com.salekseev.systemanalysis.model.Graph;
import com.salekseev.systemanalysis.view.GeneralView;
import com.salekseev.systemanalysis.view.MainController;
import com.salekseev.systemanalysis.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

public class Main extends Application {

    private static final Integer[][] adjacencyMatrix = {
            {0,1,1,0},
            {1,0,1,0},
            {1,1,0,1},
            {0,0,1,0}
    };

    private static final Integer[][] adjacencyMatrix2 = {
            {0,1,1,0,0},
            {1,0,0,1,1},
            {1,0,0,0,1},
            {0,1,0,0,0},
            {0,1,1,0,0}
    };

    private static final Integer[][] adjacencyMatrix3 = {
            {0,1,0,0,0,0,0,1},
            {1,0,1,0,0,0,0,0},
            {0,1,0,1,0,0,0,0},
            {0,0,1,0,1,0,0,0},
            {0,0,0,1,0,1,1,0},
            {0,0,0,0,1,0,1,0},
            {0,0,0,0,1,1,0,0},
            {1,0,0,0,0,0,0,0},
    };

    private static final Integer[][] adjacencyMatrix4 = {
            {1,1,0,0,1,0},
            {1,0,1,0,1,0},
            {0,1,0,1,0,0},
            {0,0,1,0,1,1},
            {1,1,0,1,0,0},
            {0,0,0,1,0,0},
    };

    @Override
    public void start(Stage stage) {
//        SizeMatrix sizeMatrix = new SizeMatrix();
//        Graph graph = new Graph();
//        MainView mainView = new MainView();
//        MainController mainController = new MainController(sizeMatrix, graph, mainView);
//        mainController.initialize();

        GeneralView mainView = new GeneralView();
        mainView.setMinSize(1500, 600);

        Scene scene = new Scene(mainView, 1000, 600);
        JMetro jMetro = new JMetro(scene, Style.LIGHT);
        scene.getStylesheets().add(this.getClass().getResource("/css/textfield.css").toExternalForm());

        stage.setTitle("System Analysis");
        stage.setScene(scene);
        stage.show();

        Graph graph = new Graph();
        int shortWay = graph.setAdjacencyMatrix(adjacencyMatrix4)
                .setOriented(false)
                .findShortWay(0, 2);

        System.out.println(shortWay);
    }

    public static void main(String[] args) {
        launch();
    }
}