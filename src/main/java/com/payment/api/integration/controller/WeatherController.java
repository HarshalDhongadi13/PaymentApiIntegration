package com.payment.api.integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payment.api.integration.entity.WeatherExample;
import com.payment.api.integration.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

	@Autowired
	private WeatherService weatherService;

	@PostMapping("/save")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> saveWeather(@RequestBody WeatherExample weatherExample) {

		String result = weatherService.saveWeatherDetails(weatherExample);

		return new ResponseEntity<String>(result, HttpStatus.CREATED);
	}

	@GetMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<WeatherExample> getWeather(@RequestParam("city") String cityName) {

		WeatherExample weatherObj = weatherService.getWEatherFromCity(cityName);
		return new ResponseEntity<WeatherExample>(weatherObj, HttpStatus.OK);
	}

	@PutMapping("/update")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<WeatherExample> updateWeather(@RequestBody WeatherExample weatherExample) {

		WeatherExample weatherObj = weatherService.updateWeather(weatherExample);
		return new ResponseEntity<WeatherExample>(weatherObj, HttpStatus.OK);
	}

}
