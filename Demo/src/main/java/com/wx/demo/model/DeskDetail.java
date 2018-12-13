package com.wx.demo.model;

import java.util.ArrayList;
import java.util.List;

public class DeskDetail {
    int deskNo = 0;
    List<DeskUser> list = new ArrayList<>();

    public int getDeskNo() {
        return deskNo;
    }

    public void setDeskNo(int deskNo) {
        this.deskNo = deskNo;
    }

    public List<DeskUser> getList() {
        return list;
    }

    public void setList(List<DeskUser> list) {
        this.list = list;
    }
}
