package com.appynitty.gp.pojo;

/**
 * Created by MiTHUN on 29/1/18.
 */

public class MenuPojo {

    private String menuName;
    private Integer image;

    public MenuPojo(String menuName) {
        this.menuName = menuName;
    }

    public MenuPojo(String menuName, Integer image) {
        this.menuName = menuName;
        this.image = image;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
