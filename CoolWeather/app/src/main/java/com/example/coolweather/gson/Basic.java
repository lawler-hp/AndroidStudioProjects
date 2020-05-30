package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 由于JSON中的一些字段可能不太适合直接作为Java字段来命名，因此这里使用了
 * 【@SerializedName】注解的方式来让JSON字段和Java字段之间建立映射关系。
 */

/*
"basic":{
    "cid":"CN101240201",
    "location":"九江",
    "parent_city":"九江",
    "admin_area":"江西",
    "cnty":"中国",
    "lat":"29.71203423",
    "lon":"115.99281311",
    "tz":"+8.00"
    }*/
public class Basic {
    @SerializedName("location")
    public String cityName;     //城市名称

    @SerializedName("cid")
    public String weatherId;    //城市ID

}
