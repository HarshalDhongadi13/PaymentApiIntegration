package com.payment.api.integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.api.integration.entity.WeatherExample;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherExample, Long> {

	Optional<WeatherExample> findByCityName(String cityName);

	Optional<WeatherExample> findByWeatherId(Long id);

}
