package com.football.schedule.service.area;

import com.football.common.constant.Constant;
import com.football.common.file.ExcelCommon;
import com.football.common.model.area.Area;
import com.football.common.model.area.CityProvincial;
import com.football.common.model.area.Commune;
import com.football.common.model.area.CountyDistrict;
import com.football.common.repository.AreaRepository;
import com.football.common.repository.CityProvincialRepository;
import com.football.common.repository.CommuneRepository;
import com.football.common.repository.CountyDistrictRepository;
import com.football.common.util.ArrayListCommon;
import com.football.common.util.JsonCommon;
import com.football.schedule.service.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 17-May-18
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AreaServiceImpl extends BaseService implements AreaService {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.CATEGORY);

    @Autowired
    CityProvincialRepository cityProvincialRepository;
    @Autowired
    CountyDistrictRepository countyDistrictRepository;
    @Autowired
    CommuneRepository communeRepository;
    @Autowired
    AreaRepository areaRepository;

    @Override
    public boolean importFromExcelFile(File file) {
        try {
            if (!file.exists())
                return false;
            else {
                List list = null;
                list = ExcelCommon.readExcelFile(file, 1, 0, 7);
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        long id = System.currentTimeMillis();
                        Object[] object = (Object[]) list.get(i);
                        int k = 0;
                        String cityName = object[k] != null ? object[k++].toString().trim() : "";
                        String cityCode = object[k] != null ? object[k++].toString().trim() : "";
                        String districtName = object[k] != null ? object[k++].toString().trim() : "";
                        String districtCode = object[k] != null ? object[k++].toString().trim() : "";
                        String communeName = object[k] != null ? object[k++].toString().trim() : "";
                        String communecode = object[k] != null ? object[k++].toString().trim() : "";
                        String communeLevel = object[k] != null ? object[k++].toString().trim() : "";

                        long cityProvincialId = 0L;
                        List<CityProvincial> cityProvincialList = cityProvincialRepository.findByCode(cityCode);
                        if (ArrayListCommon.isNullOrEmpty(cityProvincialList)) {
                            CityProvincial cityProvincial = new CityProvincial();
                            cityProvincial.setCode(cityCode);
                            cityProvincial.setName(cityName);
                            cityProvincial.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                            cityProvincialId = cityProvincialRepository.save(cityProvincial).getId();
                        } else {
                            cityProvincialId = cityProvincialList.get(0).getId();
                        }

                        List<Area> areaCity = areaRepository.findByCityProvincialIdAndType(cityProvincialId, Constant.AREA.LEVEL.CITY);
                        if (ArrayListCommon.isNullOrEmpty(areaCity)) {
                            Area area = new Area();
                            area.setCityProvincialId(cityProvincialId);
                            area.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                            area.setType(Constant.AREA.LEVEL.CITY);
                            areaRepository.save(area);
                        }
                        long countyDistrictId = 0L;
                        List<CountyDistrict> countyDistrictList = countyDistrictRepository.findByCode(districtCode);
                        if (ArrayListCommon.isNullOrEmpty(countyDistrictList)) {
                            CountyDistrict countyDistrict = new CountyDistrict();
                            countyDistrict.setCityProvincialId(cityProvincialId);
                            countyDistrict.setCode(districtCode);
                            countyDistrict.setName(districtName);
                            countyDistrict.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                            countyDistrictId = countyDistrictRepository.save(countyDistrict).getId();
                        } else {
                            countyDistrictId = countyDistrictList.get(0).getId();
                        }

                        List<Area> areaDistrict = areaRepository.findByCityProvincialIdAndCountyDistrictIdAndType(cityProvincialId, countyDistrictId, Constant.AREA.LEVEL.DISTRICT);
                        if (ArrayListCommon.isNullOrEmpty(areaDistrict)) {
                            Area area = new Area();
                            area.setCityProvincialId(cityProvincialId);
                            area.setCountyDistrictId(countyDistrictId);
                            area.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                            area.setType(Constant.AREA.LEVEL.DISTRICT);
                            areaRepository.save(area);
                        }
                        long communeId = 0L;
                        List<Commune> communeList = communeRepository.findByCode(communecode);
                        if (ArrayListCommon.isNullOrEmpty(communeList)) {
                            Commune commune = new Commune();
                            commune.setCountyDistrictId(countyDistrictId);
                            commune.setCode(communecode);
                            commune.setName(communeName);
                            commune.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                            commune.setLevel(communeLevel);
                            communeId = communeRepository.save(commune).getId();
                        } else {
                            communeId = communeList.get(0).getId();
                        }

                        List<Area> areaCommune = areaRepository.findByCityProvincialIdAndCountyDistrictIdAndCommuneIdAndType(
                                cityProvincialId, countyDistrictId, communeId, Constant.AREA.LEVEL.COMMUNE
                        );

                        if (ArrayListCommon.isNullOrEmpty(areaCommune)) {
                            Area area = new Area();
                            area.setCityProvincialId(cityProvincialId);
                            area.setCountyDistrictId(countyDistrictId);
                            area.setCommuneId(communeId);
                            area.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                            area.setType(Constant.AREA.LEVEL.COMMUNE);
                            areaRepository.save(area);
                        }
                        LOGGER.info(" >>> " + (System.currentTimeMillis() - id) + " >>> " + JsonCommon.objectToJsonNotNull(list.get(i)));
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception when importFromExcelFile ", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean importFromExcelFile() {
        long rownum = areaRepository.count();
        if (rownum > 0)
            return true;
        else {
            File file = null;
            try {
                file = new ClassPathResource("area.xls").getFile();
                if (!file.exists())
                    return false;
                else
                    return importFromExcelFile(file);
            } catch (IOException e) {
                LOGGER.error("IOException ", e);
                return false;
            }
        }
    }
}
