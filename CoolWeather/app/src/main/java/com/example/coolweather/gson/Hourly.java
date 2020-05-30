package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 *             "hourly":[
 *                 {
 *                     "cloud":"100",
 *                     "cond_code":"305",
 *                     "cond_txt":"小雨",
 *                     "dew":"19",
 *                     "hum":"96",
 *                     "pop":"55",
 *                     "pres":"1000",
 *                     "time":"2020-05-29 22:00",
 *                     "tmp":"21",
 *                     "wind_deg":"51",
 *                     "wind_dir":"东北风",
 *                     "wind_sc":"1-2",
 *                     "wind_spd":"11"
 *                     }
 *                     .....
 *                     ]
 *
 */

public class Hourly {
    @SerializedName("time")
    public String hourlyTime;

    @SerializedName("cond_code")
    public String cond_code;

    @SerializedName("tmp")
    public String tmp;
}
