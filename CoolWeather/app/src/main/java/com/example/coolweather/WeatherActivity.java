package com.example.coolweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolweather.gson.AirQuality;
import com.example.coolweather.gson.Forecast;
import com.example.coolweather.gson.Suggestion;
import com.example.coolweather.gson.Weather;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {


    public ScrollView weatherLayout;
    public SwipeRefreshLayout swipeRefresh;
    public DrawerLayout drawerLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private String weatherId;
    private Button navButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // 初始化各控件
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navButton = (Button)findViewById(R.id.nav_button);

        SharedPreferences prefs = getSharedPreferences("WeatherDate",MODE_PRIVATE);
        String weatherString = prefs.getString("weather",null);
        String airQualityString = prefs.getString("airQuality",null);
        if (weatherString != null || airQualityString != null){
            // 有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            weatherId = weather.basic.weatherId;
            AirQuality airQuality = Utility.handleAQIWeatherResponse(airQualityString);
            showAQIWeatherInfo(airQuality);
            showWeatherInfo(weather);
        }else {
            //无缓存时去服务器查询天气
            weatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);        //隐藏页面（占着位置）
            requestWeather(weatherId);
        }

        swipeRefresh.setOnRefreshListener(()->requestWeather(weatherId));
        navButton.setOnClickListener(v->drawerLayout.openDrawer(GravityCompat.START));
    }

    /**
     * 根据天气ID请求城市天气
     * @param weatherId 城市ID
     */
    public void requestWeather(final String weatherId) {
        String AQIUri = "https://free-api.heweather.net/s6/air?location=" + weatherId + "&key=09112f0e675d4b86a1540bc66c11ee6e";
        HttpUtil.sendOkHttpRequest(AQIUri, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(()-> Toast.makeText(WeatherActivity.this,"获取天气质量数据失败",Toast.LENGTH_SHORT).show());
                swipeRefresh.setRefreshing(false);  //停止刷新
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final AirQuality airQuality = Utility.handleAQIWeatherResponse(responseText);
                runOnUiThread(()->{
                    if (airQuality != null && "ok".equals(airQuality.status)) {
                        SharedPreferences.Editor editor = getSharedPreferences("WeatherDate",MODE_PRIVATE).edit();
                        editor.putString("airQuality", responseText).apply();
                        showAQIWeatherInfo(airQuality);
                    } else {
                        Toast.makeText(WeatherActivity.this, "获取天气质量信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);  //停止刷新
                    }
                });
            }
        });

        String weatherUrl = "https://free-api.heweather.net/s6/weather?location=" + weatherId + "&key=09112f0e675d4b86a1540bc66c11ee6e";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        runOnUiThread(() -> Toast.makeText(WeatherActivity.this, "获取天气数据失败", Toast.LENGTH_SHORT).show());
                        swipeRefresh.setRefreshing(false);  //停止刷新
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseText = response.body().string();
                        final Weather weather = Utility.handleWeatherResponse(responseText);
                        runOnUiThread(()->{
                            if (weather != null && "ok".equals(weather.status)) {
                                SharedPreferences.Editor editor = getSharedPreferences("WeatherDate",MODE_PRIVATE).edit();
                                editor.putString("weather", responseText).apply();
                                showWeatherInfo(weather);
                            } else {
                                Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                                swipeRefresh.setRefreshing(false);  //停止刷新
                            }
                        });
                    }
        });
    }

    /**
     * 处理并展示AQIWeather实体类中的数据。
     * @param airQuality    AQI
     */
    private void showAQIWeatherInfo(AirQuality airQuality) {
        aqiText.setText(airQuality.airNowCity.aqi);
        pm25Text.setText(airQuality.airNowCity.pm25);

    }

    /**
     * 处理并展示Weather实体类中的数据。
     * @param weather 天气
     */
    private void showWeatherInfo(Weather weather) {
        weatherId = weather.basic.weatherId;
        String cityName = weather.basic.cityName;
        String updateTime = weather.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.info;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);

        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.info);
            maxText.setText(forecast.tmpMax);
            minText.setText(forecast.tmpMin);
            forecastLayout.addView(view);
        }

        String[] suggestionArray = new String[8];
        for (int i = 0; i < weather.suggestionList.size(); i++) {
            suggestionArray[i] = weather.suggestionList.get(i).info;
        }
        comfortText.setText(suggestionArray[0]);
        carWashText.setText(suggestionArray[6]);
        sportText.setText(suggestionArray[3]);
        weatherLayout.setVisibility(View.VISIBLE);
        swipeRefresh.setRefreshing(false);  //停止刷新
/*        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);*/
    }
}
