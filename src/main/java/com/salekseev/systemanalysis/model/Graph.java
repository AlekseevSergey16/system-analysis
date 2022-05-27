package com.salekseev.systemanalysis.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private Integer[][] adjacencyMatrix;
    private boolean oriented;

    public Graph setAdjacencyMatrix(Integer[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
        return this;
    }

    public Graph setOriented(boolean oriented) {
        this.oriented = oriented;
        return this;
    }

    public Integer[][] transformToIncidentMatrix() {
        if (oriented) {
            return transformToIncidentMatrixOriented();
        } else {
            return transformToIncidentMatrixNotOriented();
        }
    }

    public List<List<Integer>> transformToRightsIncidents() {
        int n = adjacencyMatrix[0].length;
        List<List<Integer>> rightsIncidents = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            List<Integer> numbers = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    numbers.add(j + 1);
                }
            }
            if (!numbers.isEmpty()) {
                rightsIncidents.add(numbers);
            }
        }

        return rightsIncidents;
    }

    private Integer[][] transformToIncidentMatrixNotOriented() {
        int n = adjacencyMatrix[0].length;

        int m = calculateEdgeNumberNotOriented();

        Integer[][] incidentMatrix = createEmptyMatrix(n, m);

        int edge = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (adjacencyMatrix[i][j] == 1)  {
                    incidentMatrix[i][edge] = 1;
                    incidentMatrix[j][edge] = 1;
                    edge++;
                }
            }
        }

        return incidentMatrix;
    }

    private Integer[][] transformToIncidentMatrixOriented() {
        int n = adjacencyMatrix[0].length;

        int m = calculateEdgeNumber();

        Integer[][] incidentMatrix = createEmptyMatrix(n, m);

        int edge = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (adjacencyMatrix[i][j] == 1)  {
                    incidentMatrix[i][edge] = 1;
                    incidentMatrix[j][edge] = -1;
                    edge++;
                }
            }
        }

        return incidentMatrix;
    }

    private Integer[][] createEmptyMatrix(int row, int column) {
        Integer[][] incidentMatrix = new Integer[row][column];
        for (var i = 0; i < row; i++) {
            for (var j = 0; j < column; j++) {
                incidentMatrix[i][j] = 0;
            }
        }

        return incidentMatrix;
    }

    private int calculateEdgeNumber() {
        int n = adjacencyMatrix[0].length;
        int m = 0;
        for (var i = 0; i < n; i++) {
            for (var j = 0; j < n; j++) {
                if (adjacencyMatrix[i][j] > 0) {
                    m++;
                }
            }
        }
        return m;
    }

    private int calculateEdgeNumberNotOriented() {
        int n = adjacencyMatrix[0].length;
        int m = 0;
        for (var i = 0; i < n; i++) {
            for (var j = i + 1; j < n; j++) {
                if (adjacencyMatrix[i][j] > 0) {
                    m++;
                }
            }
        }
        return m;
    }

    public Integer[][] calculateShortestPath() {
        int n = adjacencyMatrix[0].length;

        Integer[][] shortestPathMatrix = new Integer[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (adjacencyMatrix[i][j] == i) {
                    shortestPathMatrix[i][j] = 0;
                }

                shortestPathMatrix[i][j] = findShortWay(i, j);

            }
        }

        return shortestPathMatrix;
    }

    public int findShortWay(int startIndex, int endIndex) {
        return test(startIndex, endIndex);
    }

    public int test(int start, int end) {
        int n = adjacencyMatrix[0].length;
        Edge[] edges = new Edge[n];
        edges[end] = new Edge(end, 0);

        int ui = 0;

        for (int i = 0; i < n; i++) {
            if (i == end) {
                continue;
            }
            if (adjacencyMatrix[i][end] == 1) {
                edges[i] = new Edge(i, 1);
                ui = 1;
            }
        }

        int way = test6(start, ui, edges);
        return way;
    }

    public int test5(int start, int ui, Edge[] edges) {
        if (edges[start] != null) {
            return edges[start].getU();
        }

        int ui2 = ui;
        int n = adjacencyMatrix[0].length;

        for (int j = 0; j < edges.length; j++) {
            if (edges[j] != null && edges[j].getU() == ui2) {
                int row = edges[j].getIndex();
                for (var i = 0; i < n; i++) {
                    if (adjacencyMatrix[row][i] == 1) {
                        for (var k = 0; k < n; k++) {
                            if (edges[i] == null) {
                                if (ui2 == ui) {
                                    ui++;
                                }
                                edges[i] = new Edge(i, ui);
                            }
                        }
                    }
                }
            }
        }

        return test5(start, ui, edges);
    }

    public int test6(int start, int ui, Edge[] edges) {
        int count = 0;
        while (true) {
            if (count > 1000) {
                return 0;
            }
            if (edges[start] != null) {
                return edges[start].getU();
            }

            int ui2 = ui;
            int n = adjacencyMatrix[0].length;

            for (int j = 0; j < edges.length; j++) {
                if (edges[j] != null && edges[j].getU() == ui2) {
                    int row = edges[j].getIndex();
                    for (var i = 0; i < n; i++) {
                        if (adjacencyMatrix[row][i] == 1) {
                            for (var k = 0; k < n; k++) {
                                if (edges[i] == null) {
                                    if (ui2 == ui) {
                                        ui++;
                                    }
                                    edges[i] = new Edge(i, ui);
                                }
                            }
                        }
                    }
                }
            }
            count++;
        }
    }

    //lab5 0 = R, 1 = e2
    public double[] structuralRedundancy() {
        int n = adjacencyMatrix[0].length;
        int countE = oriented ? calculateEdgeNumber() : calculateEdgeNumberNotOriented();
        double R = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                R += adjacencyMatrix[i][j];
        R = R * 0.5 / (double)(n - 1) - 1;

        double e2 = 0;
        for (int i = 0; i < n; i++)
        {
            int degreeV = 0;
            for (int j = 0; j < n; j++)
                degreeV += adjacencyMatrix[i][j];
            e2 += degreeV * degreeV;
        }
        e2 -= 4 * countE * countE / (double)n;

        return new double[]{R, e2};
    }

}
