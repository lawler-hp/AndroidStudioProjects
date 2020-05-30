package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;
/*"update":{
        "loc":"2020-05-29 20:27",
        "utc":"2020-05-29 12:27"
        }*/
public class Update {

    @SerializedName("loc")
    public String updateTime;   //当地时间

    @SerializedName("utc")
    public String utc;   //UTC时间

}
