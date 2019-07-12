package com.appynitty.ghantagaditracker.pojo;

/**
 * Created by Ayan Dey on 11/7/19.
 */
public class LocalMenuPojo {

    private int menuId;
    private String menuName;
    private String menuColor;

    public LocalMenuPojo(int menuId, String menuName, String menuColor) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuColor = menuColor;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuColor() {
        return menuColor;
    }

    public void setMenuColor(String menuColor) {
        this.menuColor = menuColor;
    }
}
