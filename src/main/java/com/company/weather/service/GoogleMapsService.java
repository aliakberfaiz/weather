package com.company.weather.service;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.company.weather.vo.LocationVo;

@Service
public interface GoogleMapsService {
 
 
    public JSONObject getGeoInfoByPincode(String pincode);
    
    public LocationVo getLocationInfoByGeoInfo(JSONObject response);
}
