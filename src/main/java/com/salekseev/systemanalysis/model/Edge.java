package com.salekseev.systemanalysis.model;

public class Edge {

    private Integer index;
    private Integer u;

    public Edge() {
    }

    public Edge(Integer index) {
        this.index = index;
    }

    public Edge(Integer index, Integer u) {
        this.index = index;
        this.u = u;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getU() {
        return u;
    }

    public void setU(Integer u) {
        this.u = u;
    }

}
