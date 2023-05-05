package com.company.weather.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "geo_info")
public class GeoInfo {
    @Id
    @Column(name = "pincode")
    private Integer pincode;
    
    @Column(name = "longitude")
    private String longitude;
    
    @Column(name = "latitude")
    private String latitude;
}
