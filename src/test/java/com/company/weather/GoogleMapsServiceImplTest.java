package com.company.weather;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.company.weather.service.impl.GoogleMapsServiceImpl;
import com.company.weather.utils.RestClientUtils;
import com.company.weather.vo.LocationVo;

@RunWith(MockitoJUnitRunner.class)
public class GoogleMapsServiceImplTest {

    @Mock
    private RestClientUtils restClientUtils;

    @InjectMocks
    private GoogleMapsServiceImpl googleMapsService;

    @Before
    public void setup() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restClientUtils.getRestTemplate()).thenReturn(restTemplate);
    }

    @SuppressWarnings("unchecked")
	@Test
    public void getGeoInfoByPincode_ShouldReturnValidResponse_WhenGoogleMapsServiceReturnsValidResponse() {
        String pincode = "123456";
        JSONObject response = new JSONObject();
        response.put("status", "OK");
        when(restClientUtils.getRestTemplate().getForObject(any(String.class), any(Class.class))).thenReturn(response.toString());

        JSONObject result = googleMapsService.getGeoInfoByPincode(pincode);

        Assert.assertEquals(response.toString(), result.toString());
    }


    @SuppressWarnings("unchecked")
	@Test
    public void getGeoInfoByPincode_ShouldThrowHttpStatusCodeException_WhenGoogleMapsServiceReturnsInvalidResponse() {
        String pincode = "123456";
        JSONObject response = new JSONObject();
        response.put("status", "INVALID");
        when(restClientUtils.getRestTemplate().getForObject(any(String.class), any(Class.class))).thenReturn(response.toString());

        assertThrows(HttpStatusCodeException.class, () -> googleMapsService.getGeoInfoByPincode(pincode));
    }


    @Test
    public void getLocationInfoByGeoInfo_ShouldReturnValidLocationVo_WhenGivenValidJsonResponse() {
    
        JSONObject response = new JSONObject();
        JSONArray results = new JSONArray();
        JSONObject result = new JSONObject();
        JSONObject geometry = new JSONObject();
        JSONObject location = new JSONObject();

        location.put("lat", 1.234);
        location.put("lng", 2.345);
        geometry.put("location", location);
        result.put("geometry", geometry);
        results.put(result);
        response.put("results", results);

        LocationVo locationVo = googleMapsService.getLocationInfoByGeoInfo(response);

        Assert.assertEquals("1.234", locationVo.getLatitude());
        Assert.assertEquals("2.345", locationVo.getLongitude());
    }
}
