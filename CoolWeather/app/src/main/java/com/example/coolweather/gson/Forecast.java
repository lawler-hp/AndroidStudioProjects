package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/*
"daily_forecast":[
                {
                    "cond_code_d":"306",    	白天天气状况代码
                    "cond_code_n":"305",        夜间天气状况代码
                    "cond_txt_d":"中雨",         白天天气状况描述
                    "cond_txt_n":"小雨",
                    "date":"2020-05-29",
                    "hum":"91",
                    "mr":"11:02",       月升时间
                    "ms":"00:04",       月落时间
                    "pcpn":"5.1",
                    "pop":"80",
                    "pres":"1000",
                    "sr":"05:16",       日出时间
                    "ss":"19:11",       日落时间
                    "tmp_max":"23",     最高温度
                    "tmp_min":"20",     最低温度
                    "uv_index":"1",     紫外线强度指数
                    "vis":"24",
                    "wind_deg":"2",
                    "wind_dir":"北风",
                    "wind_sc":"1-2",
                    "wind_spd":"8"
                }
                ......
                ]
*/

public class Forecast {

    @SerializedName("date")
    public String date;     //日期

    @SerializedName("tmp_max")
    public String tmpMax;   //最高温度

    @SerializedName("tmp_min")
    public String tmpMin;   //最低温度

    @SerializedName("cond_txt_d")
    public String info;     //天气描述
}
