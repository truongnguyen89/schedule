package com.football.schedule.service.weather;

import com.football.common.model.weather.WeatherInfo;
import com.football.common.model.weather.WeatherLocal;
import com.football.common.provider.openWeather.OpenWeatherService;
import com.football.common.resource.ResourceCommon;
import com.football.common.util.ArrayListCommon;
import com.football.common.util.BeanCommon;
import com.football.schedule.repository.WeatherInfoRepository;
import com.football.schedule.repository.WeatherLocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 13-Dec-18
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class WeatherServiceImpl implements WeatherService {
    @Autowired
    OpenWeatherService openWeatherService;

    @Autowired
    WeatherInfoRepository weatherInfoRepository;

    @Autowired
    WeatherLocalRepository weatherLocalRepository;

    @Override
    public WeatherInfo getCurrentWeatherData(long localId) {
        return null;
    }

    @Override
    public List<WeatherInfo> get5DayWeather(long localId) {
        List<WeatherInfo> weatherInfoList = openWeatherService.get5DayWeather(localId);
        if (!ArrayListCommon.isNullOrEmpty(weatherInfoList))
            for (WeatherInfo w : weatherInfoList
                    ) {
                List<WeatherInfo> weatherInfoListOld = weatherInfoRepository.findByWeatherLocalIdAndLongTimeKey(w.getWeatherLocalId(), w.getLongTimeKey());
                if (ArrayListCommon.isNullOrEmpty(weatherInfoListOld))
                    weatherInfoRepository.save(w);
                else {
                    BeanCommon.copyPropertiesIgnoreNull(w, weatherInfoListOld.get(0));
                    weatherInfoRepository.save(weatherInfoListOld.get(0));
                }
            }
        return weatherInfoList;
    }

    @Override
    public List<WeatherLocal> importWatherLocal() {
        List<WeatherLocal> weatherLocalList = new ArrayList<>();
//        File file = new ResourceCommon().getFile("city.list.json");
        File file = null;
        try {
            file = new ClassPathResource("city.list.json").getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file == null)
            return weatherLocalList;
        else {
            weatherLocalList = openWeatherService.getWatherLocalFromFile(file);
            if (!ArrayListCommon.isNullOrEmpty(weatherLocalList))
                for (WeatherLocal w : weatherLocalList) {
                    List<WeatherLocal> old = weatherLocalRepository.findByOpenWeatherId(w.getOpenWeatherId());
                    if (ArrayListCommon.isNullOrEmpty(old))
                        weatherLocalRepository.save(w);
                }
        }
        return weatherLocalList;
    }

    @Override
    public List<WeatherLocal> findByStatus(int status) {
        return weatherLocalRepository.findByStatus(status);
    }
}
