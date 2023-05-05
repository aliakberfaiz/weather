package com.company.weather.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class RestClientUtils {
	private RestTemplate restTemplate = null;

	RestClientUtils() {
		this.restTemplate = new RestTemplate();
	}
	public RestTemplate getRestTemplate() {
		return this.restTemplate;
	}
}
