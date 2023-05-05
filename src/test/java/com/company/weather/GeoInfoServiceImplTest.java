package com.company.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.weather.model.GeoInfo;
import com.company.weather.repo.GeoInfoRepo;
import com.company.weather.service.GoogleMapsService;
import com.company.weather.service.impl.GeoInfoServiceImpl;
import com.company.weather.vo.LocationVo;

@ExtendWith(MockitoExtension.class)
public class GeoInfoServiceImplTest {

    @Mock
    private GeoInfoRepo geoInfoRepo;

    @Mock
    private GoogleMapsService googleMapsService;

    @InjectMocks
    private GeoInfoServiceImpl geoInfoService;

    @Test
    public void getLocationByPincode_ShouldReturnLocationVo_WhenGeoInfoExistsInDatabase() throws Exception {
        Integer pincode = 123456;
        GeoInfo geoInfo = new GeoInfo(pincode,"-0.1278","51.5074");
        Optional<GeoInfo> optGeoInfo = Optional.of(geoInfo);
        when(geoInfoRepo.findById(pincode)).thenReturn(optGeoInfo);

        LocationVo result = geoInfoService.getLocationByPincode(pincode);

        assertNotNull(result);
        assertEquals("51.5074", result.getLatitude());
        assertEquals("-0.1278", result.getLongitude());
    }

    @Test
    public void getLocationByPincode_ShouldReturnLocationVo_WhenGeoInfoDoesNotExistInDatabase() throws Exception {
        int pincode = 123456;
        JSONObject response = new JSONObject();
        response.put("status", "OK");
        JSONObject location = new JSONObject();
        location.put("lat", 51.5074);
        location.put("lng", -0.1278);
        JSONObject geometry = new JSONObject();
        geometry.put("location", location);
        JSONObject result = new JSONObject();
        result.put("geometry", geometry);
        response.append("results", result);

        when(geoInfoRepo.findById(pincode)).thenReturn(Optional.empty());
        when(googleMapsService.getGeoInfoByPincode(String.valueOf(pincode))).thenReturn(response);
        when(googleMapsService.getLocationInfoByGeoInfo(response)).thenReturn(new LocationVo("51.5074", "-0.1278"));

        LocationVo locationVo = geoInfoService.getLocationByPincode(pincode);

        assertNotNull(locationVo);
        assertEquals("51.5074", locationVo.getLatitude());
        assertEquals("-0.1278", locationVo.getLongitude());
    }

    @Test
    public void getLocationByPincode_ShouldThrowException_WhenGoogleMapsServiceReturnsNullResponse() throws Exception {
        int pincode = 123456;
        when(geoInfoRepo.findById(pincode)).thenReturn(Optional.empty());
        when(googleMapsService.getGeoInfoByPincode(String.valueOf(pincode))).thenReturn(null);

        assertThrows(Exception.class, () -> {
            geoInfoService.getLocationByPincode(pincode);
        });
    }
    
    @Test
    public void getLocationByPincode_ShouldThrowException_WhenPincodeIsInvalid() throws Exception {
        int pincode = 123;
        assertThrows(Exception.class, () -> {
            geoInfoService.getLocationByPincode(pincode);
        });
    }

 
    }