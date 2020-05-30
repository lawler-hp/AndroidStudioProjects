package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

public class AirQuality {

    public String status;

    @SerializedName("air_now_city")
    public AirNowCity airNowCity;

}
