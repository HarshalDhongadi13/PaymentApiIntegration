package com.payment.api.integration.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.payment.api.integration.entity.WeatherExample;
import com.payment.api.integration.repository.WeatherRepository;

@Service
public class WeatherService {

	@Autowired
	private WeatherRepository weatherRepository;

//	@Cacheable(value="weather", key="#cityNAme")
//	@Cacheable(value="weather", key="#cityNAme")
	@Cacheable(value = "weather", key = "#p0")
	public WeatherExample getWEatherFromCity(String cityNAme) {
		WeatherExample obj = weatherRepository.findByCityName(cityNAme).get();
		System.out.println(obj != null ? "Fetched successfully from database" : "Error fetching from dstabase");
		return obj;
	}

	public String saveWeatherDetails(WeatherExample weatherExample) {
		weatherRepository.save(weatherExample);

		return "Successfully persisted to db";
	}

//	@CachePut(value="weather", key="#weatherExample.weatherId")
	@CachePut(value = "weather", key = "#p0.cityName")
	public WeatherExample updateWeather(WeatherExample weatherExample) {
		WeatherExample obj = weatherRepository.findByWeatherId(weatherExample.weatherId).get();
		obj.setCityName(weatherExample.getCityName());
		obj.setTemperature(weatherExample.getTemperature());
		obj.setWeatherType(weatherExample.getWeatherType());
		weatherRepository.save(obj);
		return obj;
	}

}
