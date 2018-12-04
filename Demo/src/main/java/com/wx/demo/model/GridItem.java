package com.wx.demo.model;

public class GridItem {
    String name;
    double bb;
    double cell;
    double fold;
    double unassigned;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBb() {
        return bb;
    }

    public void setBb(double bb) {
        this.bb = bb;
    }

    public double getCell() {
        return cell;
    }

    public void setCell(double cell) {
        this.cell = cell;
    }

    public double getFold() {
        return fold;
    }

    public void setFold(double fold) {
        this.fold = fold;
    }

    public double getUnassigned() {
        return unassigned;
    }

    public void setUnassigned(double unassigned) {
        this.unassigned = unassigned;
    }
}
