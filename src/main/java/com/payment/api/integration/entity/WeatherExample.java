package com.payment.api.integration.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "weather")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherExample {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public
	Long weatherId;

	@Column(unique = true)
	String cityName;
	
	String weatherType;
	String temperature;

}
