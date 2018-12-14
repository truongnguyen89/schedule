package com.football.schedule.repository;

import com.football.common.model.area.Area;
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
public interface AreaRepository extends CrudRepository<Area, Long> {
    List<Area> findByCityProvincialIdAndCountyDistrictIdAndCommuneIdAndType(
            long cityProvincialId,
            long countyDistrictId,
            long communeId,
            int type
    );

    List<Area> findByCityProvincialIdAndCountyDistrictIdAndType(
            long cityProvincialId,
            long countyDistrictId,
            int type
    );

    List<Area> findByCityProvincialIdAndType(
            long cityProvincialId,
            int type
    );
}
