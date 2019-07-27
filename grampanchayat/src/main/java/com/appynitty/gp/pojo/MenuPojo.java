package com.appynitty.gp.pojo;

import android.graphics.drawable.Drawable;

/**
 * Created by MiTHUN on 29/1/18.
 */

public class MenuPojo {

    private String menuName;
    private Integer image;
    private String color;
    private String menuId;
    private Drawable menuIcon;

    public MenuPojo(String menuName, String color) {
        this.menuName = menuName;
        this.color = color;
    }

    public MenuPojo(String menuName, String color, String id, Drawable icon) {
        this.menuName = menuName;
        this.color = color;
        this.menuId = id;
        this.menuIcon = icon;
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

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public Drawable getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(Drawable menuIcon) {
        this.menuIcon = menuIcon;
    }
}
