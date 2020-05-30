package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 *             "lifestyle":[
 *                 {
 *                     "type":"comf",
 *                     "brf":"舒适",          生活指数简介
 *                     "txt":"白天温度适宜，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"
 *                 }
 *                 .....
 *                 ]
 *                 生活指数类型
 *                 comf：舒适度指数、
 *                 drsg：穿衣指数、
 *                 flu：感冒指数、
 *                 sport：运动指数、
 *                 trav：旅游指数、
 *                 uv：紫外线指数、
 *                 cw：洗车指数、
 *                 air：空气污染扩散条件指数
 */
public class Suggestion {
    @SerializedName("type")
    public String type;

    @SerializedName("brf")
    public String brf;

    @SerializedName("txt")
    public String info;
}
