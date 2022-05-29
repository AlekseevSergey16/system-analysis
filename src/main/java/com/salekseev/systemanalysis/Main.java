package com.salekseev.systemanalysis;

import com.salekseev.systemanalysis.model.Arc;
import com.salekseev.systemanalysis.model.SizeMatrix;
import com.salekseev.systemanalysis.model.Graph;
import com.salekseev.systemanalysis.model.Subgraph;
import com.salekseev.systemanalysis.view.GeneralView;
import com.salekseev.systemanalysis.view.MainController;
import com.salekseev.systemanalysis.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.util.ArrayList;
import java.util.List;

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

    public static final Integer[][] orientedAdjacencyMatrix5 = {
            {0,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,1,0,0,0},
            {0,1,0,0,0,0,0,0,0,1},
            {0,0,1,0,1,0,0,1,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,0,1},
            {0,0,0,0,0,1,0,1,0,0},
            {0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0}
    };

    public static final Integer[][] lab3 = {
            {0,1,0,0,1,1,0,0,0,0},//1
            {1,0,0,0,0,0,0,0,0,0},//2
            {0,1,0,1,1,0,0,0,0,0},//3
            {0,0,0,0,0,0,0,0,1,0},//4
            {1,0,0,0,0,0,1,0,0,0},//5
            {0,0,0,0,1,0,0,1,0,1},//6
            {0,0,0,1,0,0,0,0,0,0},//7
            {0,0,0,0,0,0,1,0,0,1},//8
            {0,0,0,0,0,0,1,0,0,0},//9
            {0,0,0,0,0,0,0,1,0,0} //10
    };

    @Override
    public void start(Stage stage) {
        GeneralView mainView = new GeneralView();
        mainView.setMinSize(1500, 600);

        Scene scene = new Scene(mainView, 1000, 600);
        JMetro jMetro = new JMetro(scene, Style.LIGHT);
        scene.getStylesheets().add(this.getClass().getResource("/css/textfield.css").toExternalForm());

        stage.setTitle("System Analysis");
        stage.setScene(scene);
        stage.show();

        List<Arc> arcs = new ArrayList<>();
        List<Subgraph> sub = new ArrayList<>();
        int n = lab3[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (lab3[i][j] == 1) {
                    arcs.add(new Arc(i, j));
                }
            }
        }

        Graph graph = new Graph();

        System.out.println(sub);
    }

    public static void main(String[] args) {
        launch();
    }
}