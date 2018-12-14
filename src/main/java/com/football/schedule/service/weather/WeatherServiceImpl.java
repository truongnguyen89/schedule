package com.football.schedule.service.weather;

import com.football.common.cache.Cache;
import com.football.common.constant.Constant;
import com.football.common.model.param.Param;
import com.football.common.model.param.ParamKey;
import com.football.common.model.weather.WeatherInfo;
import com.football.common.model.weather.WeatherLocal;
import com.football.common.provider.openWeather.OpenWeatherService;
import com.football.common.resource.ResourceCommon;
import com.football.common.util.ArrayListCommon;
import com.football.common.util.BeanCommon;
import com.football.schedule.repository.ParamRepository;
import com.football.schedule.repository.WeatherInfoRepository;
import com.football.schedule.repository.WeatherLocalRepository;
import com.football.schedule.service.param.ParamService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.APPLICATION);

    @Autowired
    OpenWeatherService openWeatherService;

    @Autowired
    WeatherInfoRepository weatherInfoRepository;

    @Autowired
    WeatherLocalRepository weatherLocalRepository;

    @Autowired
    ParamRepository paramRepository;

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
    public boolean importWeatherLocalFromFileJson() {
        if (Constant.SYSTEM.IMPORT_DATA_WEATHER_LOCAL_TEST.NO == Cache.getIntParamFromCache(Constant.PARAMS.TYPE.SYSTEM, Constant.PARAMS.CODE.IMPORT_DATA_WEATHER_LOCAL_TEST, 3)) {
            List<WeatherLocal> weatherLocalList = new ArrayList<>();
            File file = null;
            try {
                file = new ClassPathResource("city.list.json").getFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (file == null)
                return false;
            else {
                weatherLocalList = openWeatherService.getWatherLocalFromFile(file);
                if (!ArrayListCommon.isNullOrEmpty(weatherLocalList))
                    for (WeatherLocal w : weatherLocalList) {
                        List<WeatherLocal> old = weatherLocalRepository.findByOpenWeatherId(w.getOpenWeatherId());
                        if (ArrayListCommon.isNullOrEmpty(old))
                            weatherLocalRepository.save(w);
                    }
            }
            Param p = new Param();
            ParamKey key = new ParamKey(Constant.PARAMS.TYPE.SYSTEM, Constant.PARAMS.CODE.IMPORT_DATA_WEATHER_LOCAL_TEST);
            p.setParamKey(key);
            p.setValue(Constant.SYSTEM.IMPORT_DATA_WEATHER_LOCAL_TEST.YES + "");
            p.setStatus(Constant.STATUS_OBJECT.ACTIVE);
            p.setCreatedAt();
            paramRepository.save(p);
            return true;
        } else
            return false;
    }

    @Override
    public List<WeatherLocal> findByStatus(int status) {
        return weatherLocalRepository.findByStatus(status);
    }
}
