package com.company.weather.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherInfoVo implements Serializable {
	private static final long serialVersionUID = 651471338158067972L;
	private String weatherInfo;
}
