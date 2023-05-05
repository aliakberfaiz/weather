package com.company.weather.repo;

import org.springframework.data.repository.CrudRepository;

import com.company.weather.model.GeoInfo;


public interface GeoInfoRepo extends CrudRepository<GeoInfo, Integer>  {

}
