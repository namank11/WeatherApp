package com.example.naman.weatherapp.Model;

import java.util.List;

public class OpenWeatherApp {
    private coord coor;
    private List<weather> weather;
    private String base;
    private main ma;
    private wind wi;
    private clouds cl;
    private int dt;
    private sys sy;
    private int id;
    private String name;
    private int cod;

    public OpenWeatherApp() {
    }

    public OpenWeatherApp(coord coor, List<weather> weatherList, String base, main ma, wind wi, clouds cl, int dt, sys sy, int id, String name, int cod) {
        this.coor = coor;
        this.weather = weatherList;
        this.base = base;
        this.ma = ma;
        this.wi = wi;
        this.cl = cl;
        this.dt = dt;
        this.sy = sy;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    public coord getCoor() {
        return coor;
    }

    public void setCoor(coord coor) {
        this.coor = coor;
    }

    public String getBase() {
        return base;
    }

    public List<com.example.naman.weatherapp.Model.weather> getWeather() {
        return weather;
    }

    public void setWeather(List<com.example.naman.weatherapp.Model.weather> weather) {
        this.weather = weather;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public main getMa() {
        return ma;
    }

    public void setMa(main ma) {
        this.ma = ma;
    }

    public wind getWi() {
        return wi;
    }

    public void setWi(wind wi) {
        this.wi = wi;
    }

    public clouds getCl() {
        return cl;
    }

    public void setCl(clouds cl) {
        this.cl = cl;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public sys getSy() {
        return sy;
    }

    public void setSy(sys sy) {
        this.sy = sy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
}
