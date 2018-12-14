package com.football.schedule.job;

import com.football.common.util.JsonCommon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.APPLICATION);
    @Autowired
    WeatherService weatherService;


    @Scheduled(fixedDelay = 6000, initialDelay = 15000)
    public void get5DayWeather() {
        long id = System.currentTimeMillis();
        LOGGER.info("[B][" + id + "] >>>>>>>>>>>>>>>>>>>>>>>>>> Start WeatherSchedule.get5DayWeather ...");
        List<WeatherLocal> weatherLocalList = weatherService.findByStatus(Constant.STATUS_OBJECT.ACTIVE);
        LOGGER.info("[" + id + "] weatherLocalList.size = " + weatherLocalList.size());
        for (WeatherLocal weatherLocal : weatherLocalList) {
            weatherService.get5DayWeather(weatherLocal.getOpenWeatherId());
            LOGGER.info("[" + id + "][Duration = " + (System.currentTimeMillis() - id) + "] end get5DayWeather for " + JsonCommon.objectToJsonNotNull(weatherLocal));
            id = System.currentTimeMillis();
        }
        LOGGER.info("[E][" + id + "][Duration = " + (System.currentTimeMillis() - id) + "] >>>>>>>>>>>>>>>>>>>>>>>>>> End WeatherSchedule.get5DayWeather ...");
    }

}
