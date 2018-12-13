package com.wx.demo.model;

import java.util.ArrayList;
import java.util.List;

public class DeskDetail {
    int destkNo = 0;
    List<DeskUser> list = new ArrayList<>();

    public int getDestkNo() {
        return destkNo;
    }

    public void setDestkNo(int destkNo) {
        this.destkNo = destkNo;
    }

    public List<DeskUser> getList() {
        return list;
    }

    public void setList(List<DeskUser> list) {
        this.list = list;
    }
}
