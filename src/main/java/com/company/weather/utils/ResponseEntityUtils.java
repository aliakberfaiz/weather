package com.company.weather.utils;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Service
public class ResponseEntityUtils {
	public ResponseEntity<Object> createJsonResponseEntity(String status, String message, JSONObject data) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    JSONObject entity = new JSONObject();
	    entity.put("status", status);
	    if (message != null) {
	        entity.put("message", message);
	    }
	    if (data != null) {
	        entity.put("data", data);
	    }

	    HttpStatus httpStatus = HttpStatus.OK;
	    if (!"success".equals(status)) {
	        httpStatus = HttpStatus.BAD_REQUEST;
	    }

	    return ResponseEntity.status(httpStatus).headers(headers).body(entity.toString());
	}

}
