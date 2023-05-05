package com.company.weather.service.impl;

import java.util.Optional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.weather.model.GeoInfo;
import com.company.weather.repo.GeoInfoRepo;
import com.company.weather.service.GeoInfoService;
import com.company.weather.service.GoogleMapsService;
import com.company.weather.vo.LocationVo;

@Service
public class GeoInfoServiceImpl implements GeoInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(GeoInfoServiceImpl.class);
	
	@Autowired 
	GeoInfoRepo geoInfoRepo;
	
	@Autowired 
	GoogleMapsService googleMapsService;
	
	

	@Override
	public LocationVo getLocationByPincode(Integer pincode) throws Exception {

		if(isValidPincode(pincode)!= true) {
			throw new Exception("Invalid pincode entered");
		}
		Optional<GeoInfo> opt= geoInfoRepo.findById(pincode);
		GeoInfo geoInfo = null;
		JSONObject response = null;
		LocationVo locationVo = null;
		if(opt.isEmpty()) {
			try {
				response = googleMapsService.getGeoInfoByPincode(pincode.toString());
			}catch(Exception e) {
				logger.error("Error occured while fetching geo info: {}, Cause: {}", e.getMessage(), e.getCause());
			} 
			if(response!= null) {
				locationVo = googleMapsService.getLocationInfoByGeoInfo(response);
				geoInfo = new GeoInfo(pincode, locationVo.getLatitude(),locationVo.getLongitude());
				geoInfoRepo.save(geoInfo);
			}else {
				logger.error("Couldn't fetch data from google maps api!");
				throw new Exception("Couldn't get pincode geo information");
			}
			
		}
		else {
			geoInfo = opt.get();
			locationVo = new LocationVo(geoInfo.getLatitude(), geoInfo.getLongitude());
		}
		return locationVo;
	}
	
	private static boolean isValidPincode(Integer pincode) {
	    if (pincode == null) {
	        return false;
	    }
	    String pincodeStr = pincode.toString();
	    if (pincodeStr.length() != 6) {
	        return false;
	    }
	    String regex = "^[1-9]\\d{5}$";
	    return pincodeStr.matches(regex);
	}

}
