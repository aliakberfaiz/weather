package com.company.weather.service;

import org.json.JSONObject;

public interface WeatherService {
	JSONObject getWeatherInfo(Integer pincode, String date) throws Exception;
}
