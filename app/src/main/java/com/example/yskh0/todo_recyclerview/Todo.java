package com.example.yskh0.todo_recyclerview;

import java.io.Serializable;

public class Todo implements Serializable {

    //resIdはラジオボタン(色)判別用
    private int resId;
    private String contents;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Todo(int resId, String contents) {

        this.resId = resId;
        this.contents = contents;
    }
}
