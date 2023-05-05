package com.company.weather.service.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.company.weather.service.GoogleMapsService;
import com.company.weather.utils.RestClientUtils;
import com.company.weather.vo.LocationVo;
import static com.company.weather.utils.GoogleMapsConstants.*;
import static com.company.weather.utils.AppConstants.STATUS_OK;
@Service
public class GoogleMapsServiceImpl  implements GoogleMapsService{
	 	
	 	
	 	
	    @Autowired RestClientUtils restClientUtils;
	    
	    @Value("${googlemaps.api.key}")
	    String apiKey;
	    
	   

	    @SuppressWarnings("serial")
		public JSONObject getGeoInfoByPincode(String pincode) {
	    	RestTemplate restTemplate = restClientUtils.getRestTemplate();
	        StringBuilder urlBuilder = new StringBuilder(BASE_URL);
	        urlBuilder.append("?").append(ADDRESS_PARAM).append("=").append(pincode).append(",")
	        .append(COUNTRY_PARAM).append("&").append(API_KEY_PARAM).append("=").append(apiKey);
	        String url = urlBuilder.toString();

	        String response = restTemplate.getForObject(url, String.class);

	        JSONObject jsonObject = new JSONObject(response);

	        if (jsonObject.getString("status").equals(STATUS_OK)) {
	            return jsonObject;
	        } else {
	            throw new HttpStatusCodeException(HttpStatus.BAD_REQUEST, "Failed to get geo info for pincode " + pincode) {};
	        }
	    }
	    
	    public LocationVo getLocationInfoByGeoInfo(JSONObject response) {
	        JSONArray results = response.getJSONArray(RESULTS_KEY);
	        JSONObject result = results.getJSONObject(0);
	        JSONObject geometry = result.getJSONObject(GEOMETRY_KEY);
	        JSONObject location = geometry.getJSONObject(LOCATION_KEY);

	        String latitude = String.valueOf(location.getDouble(LATITUDE_KEY));
	        String longitude = String.valueOf(location.getDouble(LONGITUDE_KEY));
	        LocationVo locationVo = new LocationVo(latitude, longitude);
	        return locationVo;
	    }
}
