package com.salekseev.systemanalysis.model;

import java.util.ArrayList;
import java.util.List;

public class Subgraph {

    public List<Integer> V;

    public Subgraph() {
        V = new ArrayList<>();
    }

    public Subgraph(List<Integer> v) {
        V = v;
    }
}
