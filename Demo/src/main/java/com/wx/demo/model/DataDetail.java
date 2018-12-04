package com.wx.demo.model;

import java.util.ArrayList;
import java.util.List;

public class DataDetail {
    String name = "";
    List<GridItem> info = new ArrayList<>();
    List<DataDetail> children = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GridItem> getInfo() {
        return info;
    }

    public void setInfo(List<GridItem> info) {
        this.info = info;
    }

    public List<DataDetail> getChildren() {
        return children;
    }

    public void setChildren(List<DataDetail> children) {
        this.children = children;
    }
}
