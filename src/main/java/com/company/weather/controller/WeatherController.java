package com.company.weather.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.weather.service.WeatherService;
import com.company.weather.utils.ResponseEntityUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class WeatherController {

	public static final String WEATHER_INFO_ENDPOINT = "/weather-info";
	
	@Autowired 
	WeatherService weatherService;
	
	@Autowired
	ResponseEntityUtils responseEntityUtils;
    
	
	@GetMapping(WEATHER_INFO_ENDPOINT)
	@ApiOperation(value = "Get weather info by pincode of an Indian city at particular date")
    public  @ResponseBody ResponseEntity<Object>  getWeatherInfo( @ApiParam(value = "Pincode of an Indian city", required = true) @RequestParam String pincode, @ApiParam(value = "Date in format of yyyy-mm-dd", required = true) @RequestParam String date) {
    	JSONObject entity=null;
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
        try {
        	Integer pincodeInt = Integer.parseInt(pincode);
        	entity=  weatherService.getWeatherInfo(pincodeInt, date);
        	 return responseEntityUtils.createJsonResponseEntity("success", null, entity);

        }catch (NumberFormatException e) {
        	return responseEntityUtils.createJsonResponseEntity("failed", "pincode is not a valid number", null);
		}
        catch (Exception e) {
        	return responseEntityUtils.createJsonResponseEntity("failed", e.getMessage(), null);
		}
        
    }
}
