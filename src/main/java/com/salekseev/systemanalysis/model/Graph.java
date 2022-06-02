package com.salekseev.systemanalysis.model;

import java.util.*;

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
            rightsIncidents.add(numbers);
        }

        return rightsIncidents;
    }

    private List<List<Integer>> transformToRightsIncidents2() {
        int n = adjacencyMatrix[0].length;
        List<List<Integer>> rightsIncidents = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            List<Integer> numbers = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    numbers.add(j);
                }
            }
            rightsIncidents.add(numbers);
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
                return Integer.MAX_VALUE;
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

    // lab3
    public List<Subgraph> topologicalDecomposition() {
        int countV = adjacencyMatrix[0].length;
        List<Arc> e = createArcList();
        List<Subgraph> Sub = new ArrayList<>();

        List<Integer> notUsedV = new ArrayList<>(); //список еще не использованных вершин
        for (int i = 0; i < countV; i++)
            notUsedV.add(i);
        while (notUsedV.size() > 0) {
            List<Integer> R = new ArrayList<>(); //достижимое множество
            R.add(notUsedV.get(0));

            List<Integer> Q = new ArrayList<>(); //контрдостижимое множество
            Q.add(notUsedV.get(0));

            int[] color = new int[countV];

            //формируем достижимое и контрдостижимое множества
            for (int i = 1; i < notUsedV.size(); i++) {
                for (int k = 0; k < countV; k++) {
                    if (notUsedV.contains(k))
                        color[k] = 1;
                    else
                        color[k] = 2;
                }

                if (DFS(notUsedV.get(0), notUsedV.get(i), e, color)) {
                    R.add(notUsedV.get(i));
                }

                for (int k = 0; k < countV; k++) {
                    if (notUsedV.contains(k))
                        color[k] = 1;
                    else
                        color[k] = 2;
                }

                if (DFS(notUsedV.get(i), notUsedV.get(0), e, color)) {
                    Q.add(notUsedV.get(i));
                }
            }
            //пересечение множеств R и Q
            R.retainAll(Q);
            List<Integer> intersection = new ArrayList<>(R);
            Sub.add(new Subgraph(intersection));

            for (Integer integer : intersection) {
                notUsedV.remove(integer);
            }
        }

        return Sub;
    }

    private boolean DFS(int u, int endV, List<Arc> E, int[] color) {
        color[u] = 2;

        if (u == endV) {
            return true;
        }

        for (int w = 0; w < E.size(); w++) {
            if (color[E.get(w).v2] == 1 && E.get(w).v1 == u) {
                if (DFS(E.get(w).v2, endV, E, color)) {
                    return true;
                }
                color[E.get(w).v2] = 1;
            }
        }

        return false;
    }

    private List<Arc> createArcList() {
        int n = adjacencyMatrix[0].length;
        List<Arc> arcs = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    arcs.add(new Arc(i, j));
                }
            }
        }

        return arcs;
    }

    public Integer[][] buildAdjacencyMatrix(List<Subgraph> subgraphs) {
        int n = subgraphs.size();
        List<List<Integer>> rightsIncidents = transformToRightsIncidents2();

        Integer[][] matrix = createEmptyMatrix(n, n);

        int countSub = 0;
        for (int i = 0; i < n; i++) {
            Subgraph subgraph = subgraphs.get(i);

            for (int j = 0; j < subgraph.V.size(); j++) {
                int v = subgraph.V.get(j);
                List<Integer> vs = rightsIncidents.get(v); //получили куда ведет данная вершина

                // должны найти в каких подграфах находятся данные вершины
                List<Integer> subgraphList = new ArrayList<>(search(vs, subgraphs));

                for (Integer integer : subgraphList) {
                    if (i == integer) continue;
                    matrix[i][integer] = 1;
                }
            }

        }

        return matrix;
    }

//    private Integer[] findDuplicates(List<Integer> list1, List<Integer> list2) {
//        Set<Integer> result = new HashSet<>();
//
//        result.addAll(list1);
//        result.addAll(list2);
//
//        return result.toArray();
//    }

    private Set<Integer> search(List<Integer> vs, List<Subgraph> subgraphs) {
        Set<Integer> result = new HashSet<>();

        for (Integer val1 : vs) {
            for (int i = 0; i < subgraphs.size(); i++) {
                for (int j = 0; j < subgraphs.get(i).V.size(); j++) {
                    if (val1 == subgraphs.get(i).V.get(j)) {
                        result.add(i);
                    }
                }
            }
        }
        return result;
    }

//    private boolean checkForContours() {
//        int matrixSize = adjacencyMatrix[0].length;
//        for (int row = 0; row < matrixSize; row++) {
//            for (int column = 0; column < matrixSize; column++) {
//                if (adjacencyMatrix[row][column] == 1) {
//                    let path = getPath(adjacencyMatrix, matrixSize, row, column)
//                    if (path === null) {
//                        return true
//                    }
//                }
//            }
//        }
//        return false
//    }

    public Map<Integer, Map<Integer, Integer>> getMapLevels() {
        int matrixSize = adjacencyMatrix[0].length;

        // @ts-ignore
        var mapLevels = new HashMap<Integer, Map<Integer, Integer>>();
        // @ts-ignore
        var mainMap = new HashMap<Integer, Integer>();


        var vertices = new ArrayList<Integer>();

        var newNumber = 1;
        var level = 0;

        var isVertixN0 = true;

        // @ts-ignore
        var map = new HashMap<Integer, Integer>();
        for (var column = 0; column < matrixSize; column++) {
            for (var row = 0; row < matrixSize; row++) {
                if (adjacencyMatrix[row][column] == 1) {
                    isVertixN0 = false;
                    break;
                }
            }
            if (isVertixN0) {
                vertices.add(column);
                mainMap.put(column + 1, newNumber);
                map.put(column + 1, newNumber);
                newNumber++;
            } else {
                isVertixN0 = true;
            }
        }
        mapLevels.put(level, map);

        var pastLevel = level;
        level++;

        while (true) {
            // @ts-ignore
            var map1 = new HashMap<Integer, Integer>();

            System.out.println(mapLevels);
            for (var column = 0; column < matrixSize; column++) {
                if (vertices.contains(column)) {
                    continue;
                } else {
                    var isVertexLevel = true;

                    for (var row = 0; row < matrixSize; row++) {
                        if (adjacencyMatrix[row][column] == 1) {
                            var keyRow = row;

                            if (!mainMap.containsKey(keyRow + 1)) {
                                isVertexLevel = false;
                            }
                        }
                    }

                    if (isVertexLevel) {
                        vertices.add(column);
                        map1.put(column + 1, newNumber);
                        newNumber++;
                    } else {
                        isVertexLevel = true;
                    }
                }
            }

            if (map1.size() == 0) {
                break;
            }

            map1.forEach((key, value) -> mainMap.put(key, value));

            mapLevels.put(level, map1);

            pastLevel = level;
            level++;
        }

        System.out.println(mapLevels);
        return mapLevels;
    }

    public Integer[][] getNewAdjacencyMatrix(Map<Integer, Map<Integer, Integer>> mapLevels) {
        var matrixSize = adjacencyMatrix[0].length;
        var arr = createEmptyMatrix(matrixSize, matrixSize);

        var newRow = 0;
        var newColumn = 0;

        for (var row = 0; row < matrixSize; row++) {
            for (var column = 0; column < matrixSize; column++) {
                if (adjacencyMatrix[row][column] == 1) {

                    for(var entry : mapLevels.entrySet()) {
                        var value = entry.getValue();
                        if (value.get(row + 1) != null) {
                            newRow = value.get(row + 1) - 1;
                        }
                        if (value.get(column + 1) != null) {
                            newColumn = value.get(column + 1) - 1;
                        }
                    }

                    arr[newRow][newColumn] = 1;
                }
            }
        }
        return arr;
    }

}
