package com.football.schedule.job;

import com.football.common.constant.Constant;
import com.football.common.model.weather.WeatherLocal;
import com.football.schedule.service.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 13-Dec-18
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class WeatherSchedule {
    @Autowired
    WeatherService weatherService;


    @Scheduled(fixedDelay = 660000, initialDelay = 150000)
    public void get5DayWeather() {
        System.out.println("Begin get5DayWeather ");
        List<WeatherLocal> weatherLocalList = weatherService.findByStatus(Constant.STATUS_OBJECT.ACTIVE);
        System.out.println("Doing get5DayWeather " + weatherLocalList.size());
        for (WeatherLocal weatherLocal : weatherLocalList) {
            System.out.println("Doing get5DayWeather " + weatherLocal.getOpenWeatherId());
            weatherService.get5DayWeather(weatherLocal.getOpenWeatherId());
        }
        System.out.println("End get5DayWeather ");
    }

    @Scheduled(fixedDelay = 660000, initialDelay = 100000)
    public void importWatherLocal() {
        System.out.println("Begin importWatherLocal ");
        weatherService.importWatherLocal();
        System.out.println("End importWatherLocal ");
    }

}
