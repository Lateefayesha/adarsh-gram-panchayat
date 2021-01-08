package com.appynitty.gp.pojo;

/**
 * Created by Ayan Dey on 20/11/18.
 */
public class AreaListPojo {

    private String area;

    public String getArea ()
    {
        return area;
    }

    public void setArea (String area)
    {
        this.area = area;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [area = "+area+"]";
    }
}
