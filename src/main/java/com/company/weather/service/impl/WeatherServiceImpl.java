package com.company.weather.service.impl;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.company.weather.repo.GeoInfoRepo;
import com.company.weather.service.GeoInfoService;
import com.company.weather.service.WeatherService;
import com.company.weather.utils.DateTimeUtilsService;
import com.company.weather.utils.RestClientUtils;
import com.company.weather.vo.LocationVo;
import static com.company.weather.utils.OpenWeatherApiConstants.*;
@Service
public class WeatherServiceImpl implements WeatherService {
	private static final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

	@Autowired 
	GeoInfoRepo geoInfoRepo;

	@Autowired 
	RestClientUtils restClientUtils;
	
	
	@Autowired 
	GeoInfoService geoInfoService;

	@Value("${openweather.api.key}")
	private String apiKey;
	


	@Override
	public JSONObject getWeatherInfo(Integer pincode, String date) throws Exception {
		
		
		LocationVo location = null;
		RestTemplate restTemplate = restClientUtils.getRestTemplate();
		location = geoInfoService.getLocationByPincode(pincode);
		long epochTime;
		try {
			epochTime = DateTimeUtilsService.dateToEpochConverter(date);
		} catch (ParseException e) {
			throw new Exception(
					"Couldn't parse date, date should be in format: yyyy-MM-dd ");
		}
		String url = new StringBuilder().append(OPENWEATHER_API_URL)
			    .append("?").append(LATITUDE_KEY).append("=").append(location.getLatitude())
			    .append("&").append(LONGITUDE_KEY).append("=").append(location.getLongitude())
			    .append("&").append(DATE_KEY).append("=").append(epochTime)
			    .append("&").append(API_KEY_PARAM).append("=").append(apiKey).toString(); 
		
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		JSONObject jsonResponse = new JSONObject(response.getBody());
		
		if (response.getStatusCodeValue()== 200) {
			JSONArray data = jsonResponse.getJSONArray(DATA_KEY);
			  JSONObject entity = new JSONObject();
              entity.put(WEATHER_KEY, data);

             return entity;
		} else {
			logger.error("Failed to get weather information for given location. Error code: {}", response.getStatusCodeValue());
			throw new Exception(
					"Failed to get weather information for given location.");
		}

	}

}
