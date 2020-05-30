package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/*
"now":{
        "cloud":"100",      云量
        "cond_code":"399",  实况天气状况代码
        "cond_txt":"雨",    实况天气状况描述
        "fl":"21",          体感温度，默认单位：摄氏度
        "hum":"96",         相对湿度
        "pcpn":"0.9",       降水量
        "pres":"1001",      大气压强
        "tmp":"19",         温度，默认单位：摄氏度
        "vis":"16",         能见度，默认单位：公里
        "wind_deg":"209",   风向360角度
        "wind_dir":"西南风", 风向
        "wind_sc":"1",      风力
        "wind_spd":"2"      风速，公里/小时
        }
*/

public class Now {
    @SerializedName("tmp")          //温度
    public String temperature;

    @SerializedName("cond_txt")     //实况天气状况描述
    public String info;

    @SerializedName("cond_code")    //实况天气状况代码
    public String cond_code;

    @SerializedName("fl")   //体感温度
    public String fl;

    @SerializedName("wind_dir")     //风向
    public String wind_dir;

    @SerializedName("wind_sc")      //风力
    public String wind_sc;

    @SerializedName("wind_spd")      //风速
    public String wind_spd;
}
