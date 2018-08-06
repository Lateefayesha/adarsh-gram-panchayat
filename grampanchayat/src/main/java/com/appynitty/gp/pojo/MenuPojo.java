package com.appynitty.gp.pojo;

/**
 * Created by MiTHUN on 29/1/18.
 */

public class MenuPojo {

    private String menuName;
    private Integer image;
    private String color;

    public MenuPojo(String menuName) {
        this.menuName = menuName;
    }

    public MenuPojo(String menuName, String color) {
        this.menuName = menuName;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
