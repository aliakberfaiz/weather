package com.company.weather.service;

import com.company.weather.vo.LocationVo;

public interface GeoInfoService {
	public LocationVo getLocationByPincode(Integer pincode) throws Exception;
}
