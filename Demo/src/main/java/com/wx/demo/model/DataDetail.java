package com.wx.demo.model;

import java.util.ArrayList;
import java.util.List;

public class DataDetail {
    String name = "";
    int category = 0;
    List<DataDetail> children = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public List<DataDetail> getChildren() {
        return children;
    }

    public void setChildren(List<DataDetail> children) {
        this.children = children;
    }
}
