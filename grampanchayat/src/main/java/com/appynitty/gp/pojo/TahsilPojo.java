package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 5/7/18.
 */
public class TahsilPojo implements Serializable {

    private String id;
    private String name;

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

    @Override
    public String toString() {
        return name;
    }
}
