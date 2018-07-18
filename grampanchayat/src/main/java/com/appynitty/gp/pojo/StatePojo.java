package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 5/7/18.
 */
public class StatePojo implements Serializable {

    private String id;
    private String state_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    @Override
    public String toString() {
        return state_name;
    }
}
