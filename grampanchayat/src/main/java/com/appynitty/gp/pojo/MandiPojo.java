package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by Richali Pradhan Gupte on 02-10-2018.
 */
public class MandiPojo implements Serializable {

    private String min;
    private String max;
    private String state;
    private String date;
    private String maxPrev;
    private String variety;
    private String id;
    private String datePrev;
    private String unit;
    private String commodity;
    private String subvariety1;
    private String minPrev;
    private String mandi;

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMaxPrev() {
        return maxPrev;
    }

    public void setMaxPrev(String maxPrev) {
        this.maxPrev = maxPrev;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatePrev() {
        return datePrev;
    }

    public void setDatePrev(String datePrev) {
        this.datePrev = datePrev;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getSubvariety1() {
        return subvariety1;
    }

    public void setSubvariety1(String subvariety1) {
        this.subvariety1 = subvariety1;
    }

    public String getMinPrev() {
        return minPrev;
    }

    public void setMinPrev(String minPrev) {
        this.minPrev = minPrev;
    }

    public String getMandi() {
        return mandi;
    }

    public void setMandi(String mandi) {
        this.mandi = mandi;
    }

    @Override
    public String toString() {
        return "MandiPojo{" +
                "min='" + min + '\'' +
                ", max='" + max + '\'' +
                ", state='" + state + '\'' +
                ", date='" + date + '\'' +
                ", maxPrev='" + maxPrev + '\'' +
                ", variety='" + variety + '\'' +
                ", id='" + id + '\'' +
                ", datePrev='" + datePrev + '\'' +
                ", unit='" + unit + '\'' +
                ", commodity='" + commodity + '\'' +
                ", subvariety1='" + subvariety1 + '\'' +
                ", minPrev='" + minPrev + '\'' +
                ", mandi='" + mandi + '\'' +
                '}';
    }
}