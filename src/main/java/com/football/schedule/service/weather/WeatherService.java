package com.football.schedule.service.weather;

import com.football.common.model.weather.WeatherInfo;
import com.football.common.model.weather.WeatherLocal;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 13-Dec-18
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public interface WeatherService {
    WeatherInfo getCurrentWeatherData(long localId);

    List<WeatherInfo> get5DayWeather(long localId);

    boolean importWeatherLocalFromFileJson();

    List<WeatherLocal> findByStatus(int status);
}
