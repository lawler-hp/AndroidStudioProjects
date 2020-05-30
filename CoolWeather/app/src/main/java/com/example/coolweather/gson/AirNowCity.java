package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * "air_now_city":{
 *                 "aqi":"25",       空气质量
 *                 "qlty":"优",      空气质量描述
 *                 "main":"-",      主要污染物
 *                 "pm25":"17",
 *                 "pm10":"22",
 *                 "no2":"26",      二氧化氮
 *                 "so2":"7",
 *                 "co":"0.7",
 *                 "o3":"74",       臭氧
 *                 "pub_time":"2020-05-29 20:00"        更新时间
 *             }
 */
public class AirNowCity {
    @SerializedName("aqi")
    public String aqi;

    @SerializedName("qlty")
    public String qlty;

    @SerializedName("pm25")
    public String pm25;

    @SerializedName("pm10")
    public String pm10;

    @SerializedName("no2")
    public String no2;

    @SerializedName("so2")
    public String so2;

    @SerializedName("co")
    public String co;

    @SerializedName("o3")
    public String o3;
}
