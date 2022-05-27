package com.salekseev.systemanalysis.view;

import com.salekseev.systemanalysis.model.Graph;
import com.salekseev.systemanalysis.model.SizeMatrix;
import com.salekseev.systemanalysis.view.lab4.ShortestPathController;
import com.salekseev.systemanalysis.view.lab4.ShortestPathView;
import com.salekseev.systemanalysis.view.lab5.StructuralRedundancyController;
import com.salekseev.systemanalysis.view.lab5.StructuralRedundancyView;
import javafx.scene.control.Control;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class GeneralView extends VBox {

    TabPane tabPane = new TabPane();

    public GeneralView() {
        this.getChildren().add(createView());
    }

    private Control createView() {
        SizeMatrix sizeMatrix = new SizeMatrix();
        Graph graph = new Graph();
        MainView mainView = new MainView();
        MainController mainController = new MainController(sizeMatrix, graph, mainView);
        mainController.initialize();

        ShortestPathView shortestPathView = new ShortestPathView();
        var shortestPathController = new ShortestPathController(shortestPathView);

        var structuralRedundancyView = new StructuralRedundancyView();
        var structuralRedundancyController = new StructuralRedundancyController(structuralRedundancyView);

        Tab lab1 = new Tab("Лаб-1", mainView);
        Tab lab4 = new Tab("Лаб-4", shortestPathView);
        Tab lab5 = new Tab("Лаб-5", structuralRedundancyView);

        tabPane.setPrefSize(1500, 600);
        tabPane.getTabs().addAll(lab1, lab4, lab5);

        return tabPane;
    }

}
