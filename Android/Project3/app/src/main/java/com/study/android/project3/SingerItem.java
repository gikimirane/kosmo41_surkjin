package com.study.android.project3;

public class SingerItem {
    private String day;
    private int weather;
    private String special;
    private String contents;
    private int resId;
    private String picture;

    public SingerItem(String day, int weather, String special, String contents, int resId){
        this.day = day;
        this.weather = weather;
        this.special = special;
        this.contents = contents;
        this.resId = resId;
    }

    public SingerItem(String day, int weather, String special, String contents, String picture){
        this.day = day;
        this.weather = weather;
        this.special = special;
        this.contents = contents;
        this.picture = picture;
    }

    public SingerItem(String day, int weather, String contents, int resId){
        this.day = day;
        this.weather = weather;
        this.contents = contents;
        this.resId = resId;
    }

    public SingerItem(String day, String picture){
        this.day = day;
        this.picture = picture;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getWeather() {
        return weather;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getResId() {return resId;}

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getPicture() { return picture; }

    public void setPicture(String picture) { this.picture = picture; }
}
