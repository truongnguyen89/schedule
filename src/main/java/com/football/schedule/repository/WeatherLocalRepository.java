package com.football.schedule.repository;

import com.football.common.model.weather.WeatherLocal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 13-Dec-18
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface WeatherLocalRepository extends CrudRepository<WeatherLocal, Long> {
    List<WeatherLocal> findByStatus(int status);

    List<WeatherLocal> findByOpenWeatherId(long openWeatherId);

}
