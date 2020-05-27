package com.example.coolweather.db;

import org.litepal.crud.LitePalSupport;

/**
 * countyName 记录县的名字, weatherId记录县所对应的天气id, cityId 记录当前县所属市的id值。
 */
public class County extends LitePalSupport {
    private int id;
    private String countyName;
    private int cityId;
    private int weatherId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }
}
