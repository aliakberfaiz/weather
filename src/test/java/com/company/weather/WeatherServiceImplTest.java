package com.company.weather;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.company.weather.service.GeoInfoService;
import com.company.weather.service.impl.WeatherServiceImpl;
import com.company.weather.utils.RestClientUtils;
import com.company.weather.vo.LocationVo;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceImplTest {
	
  
    @InjectMocks
    private WeatherServiceImpl weatherService;
    
    @Mock
    private GeoInfoService geoInfoService;
    
  
    @Mock
    private RestClientUtils restClientUtils;
    
    @Mock
    private RestTemplate restTemplate;
    
    
    @Before
    public void setup() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restClientUtils.getRestTemplate()).thenReturn(restTemplate);
    }
  
    
	@SuppressWarnings("unchecked")
	@Test
	public void getWeatherInfo_ShouldReturnWeatherInfo_WhenGivenValidInput() throws Exception {
	    Integer pincode = 123456;
	    String date = "2022-05-05";
	    LocationVo location = new LocationVo("40.712776", "-74.005974"); 
	    String responseBody = "{\"data\":[{\"temp\":294.89,\"pressure\":1011,\"humidity\":57,\"wind_speed\":3.64,\"wind_deg\":167,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"dt\":1651756800}]}"; // Example response
	    ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

	    when(geoInfoService.getLocationByPincode(pincode)).thenReturn(location);
	    when(restClientUtils.getRestTemplate().getForEntity(any(String.class), any(Class.class))).thenReturn(responseEntity);

	    JSONObject result = weatherService.getWeatherInfo(pincode, date);

	    assertNotNull(result);
	    assertTrue(result.has("weather_info"));

	    JSONArray weatherInfoArray = result.getJSONArray("weather_info");
	    assertNotNull(weatherInfoArray);
	    assertEquals(1, weatherInfoArray.length());
	}

	@Test
	public void getWeatherInfo_ShouldThrowException_WhenGivenInvalidPincode() throws Exception {
	    Integer pincode = null;
	    String date = "2022-05-05";
	    assertThrows(Exception.class, () -> weatherService.getWeatherInfo(pincode, date));
	}

	@Test
	public void getWeatherInfo_ShouldThrowException_WhenGivenInvalidDate() throws Exception {
	    Integer pincode = 123456;
	    String date = "2022/05/05"; // Invalid date format, valid is yyyy-mm-dd

	    assertThrows(Exception.class, () -> weatherService.getWeatherInfo(pincode, date));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getWeatherInfo_ShouldThrowException_WhenWeatherApiReturnsNullResponse() throws Exception {
		Integer pincode = 123456;
		String date = "2022-05-05";
		LocationVo location = new LocationVo("40.712776", "-74.005974");

		when(geoInfoService.getLocationByPincode(pincode)).thenReturn(location);
		when(restClientUtils.getRestTemplate().getForEntity(any(String.class), any(Class.class))).thenReturn(null);
		assertThrows(Exception.class, () -> weatherService.getWeatherInfo(pincode, date));
	}




}
