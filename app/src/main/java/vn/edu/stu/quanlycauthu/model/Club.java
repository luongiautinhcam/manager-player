package vn.edu.stu.quanlycauthu.model;

import java.io.Serializable;

public class Club implements Serializable {
    String id;
    String name;

    public Club() {
    }

    public Club(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
