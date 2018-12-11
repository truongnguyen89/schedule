package com.football.schedule.repository;

import com.football.common.model.area.CountyDistrict;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 06-Dec-18
 * Time: 10:03 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface CountyDistrictRepository extends CrudRepository<CountyDistrict, Long> {
    List<CountyDistrict> findByCode(String code);
}
