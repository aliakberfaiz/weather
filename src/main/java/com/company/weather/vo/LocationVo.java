package com.company.weather.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationVo implements Serializable {
	private static final long serialVersionUID = 1429817351833007333L;
	private String latitude;
	private String longitude;

}
