package com.example.leviandres.myapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class weatherInfo {
    public static final String TAG = " WeatherInfo ";
    ArrayList<Info> info_list;
    City city;
    ArrayList<MyWeather> simpleWeatherList;

    public weatherInfo(JSONObject json_weather) throws JSONException {
        info_list = new ArrayList<Info>();
        // get information from json


        city = new City();
        JSONObject json_city = json_weather.getJSONObject("city");
        city.id = json_city.optLong("id");
        city.name = json_city.optString("name");
        city.coord = new Coord();
        city.country = json_city.optString("country");
        city.population = json_city.optLong("population");
        JSONObject json_coord = json_city.getJSONObject("coord");
        city.coord.lat = json_coord.optDouble("lat");
        city.coord.lon = json_coord.optDouble("lon");

        Log.d(TAG, "city name" + city.name + " " + "country" + city.country);

        JSONArray info_json_array = json_weather.optJSONArray("list");
        for (int i = 0; i < info_json_array.length(); i++) {
            Info tmp_info = new Info();
            JSONObject json_info = info_json_array.getJSONObject(i);
            tmp_info.dt = json_info.optLong("dt");

            tmp_info.temp = new Temp();
            JSONObject json_temp = json_info.optJSONObject("temp");
            tmp_info.temp.day = json_temp.getDouble("day");
            tmp_info.temp.min = json_temp.optDouble("min");
            tmp_info.temp.max = json_temp.optDouble("max");
            tmp_info.temp.night = json_temp.optDouble("night");
            tmp_info.temp.eve = json_temp.optDouble("eve");
            tmp_info.temp.morn = json_temp.optDouble("morn");

            tmp_info.pressure = json_info.optDouble("pressure");
            tmp_info.humidity = json_info.optLong("humidity");

            tmp_info.weather_list = new ArrayList<Weather>();
            JSONArray json_w_array = json_info.getJSONArray("weather");
            for (int j = 0; j < json_w_array.length(); j++) {
                Weather tmp_weather = new Weather();
                JSONObject json_w = json_w_array.getJSONObject(j);
                tmp_weather.id = json_w.optLong("id");
                tmp_weather.main = json_w.optString("main");
                tmp_weather.description = json_w.optString("description");
                tmp_weather.icon = json_w.optString("icon");
                tmp_info.weather_list.add(tmp_weather);
            }

            tmp_info.speed = json_info.optDouble("speed");
            tmp_info.deg = json_info.optLong("deg");
            tmp_info.clouds = json_info.optLong("clouds");

            info_list.add(tmp_info);

        }

        simpleWeatherList = new ArrayList<MyWeather>();

        for (int i = 0; i < info_list.size(); i++) {
            MyWeather simple_weather = new MyWeather();
            simple_weather.main = info_list.get(i).weather_list.get(0).main;
            simple_weather.desc = info_list.get(i).weather_list.get(0).description;
            simpleWeatherList.add(simple_weather);
        }

    }
    public static String getCity(String weather_str) throws JSONException {
        JSONObject data = new JSONObject(weather_str);
        JSONObject mCity;
        mCity=data.getJSONObject("city");
        String city;
        city=mCity.getString("name");
        return city;
    }

    public static String getCountry(String weather_str) throws JSONException {
        JSONObject data = new JSONObject(weather_str);
        JSONObject mCity;
        mCity=data.getJSONObject("city");
        String city;
        city=mCity.getString("country");
        return city;
    }

    public static ArrayList<Info> getInfoList(String weather_str) throws JSONException {
        JSONObject data = new JSONObject(weather_str);
        weatherInfo wi = new weatherInfo(data);
        return wi.info_list;
    }

    //
    public static ArrayList<MyWeather> getSimpleWeatherList(String weather_str) throws JSONException {
        JSONObject data = new JSONObject(weather_str);
        weatherInfo wi = new weatherInfo(data);
        return wi.simpleWeatherList;
    }


    public class Coord {
        public double lon, lat;
    }


    public class Weather {
        public long id;
        public String main, description, icon;
    }


    public class City {
        long id, population;
        String name, country;
        Coord coord;
    }

    public class Temp {
        double day, min, max, night, eve, morn;
    }

    public class Info {
        long dt, humidity, deg, clouds;
        Temp temp;
        double pressure, speed, rain;
        ArrayList<Weather> weather_list;
    }

}
