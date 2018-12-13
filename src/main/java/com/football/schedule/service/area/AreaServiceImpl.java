package com.football.schedule.service.area;

import com.football.common.constant.Constant;
import com.football.common.file.ExcelCommon;
import com.football.common.model.area.Area;
import com.football.common.model.area.CityProvincial;
import com.football.common.model.area.Commune;
import com.football.common.model.area.CountyDistrict;
import com.football.common.util.ArrayListCommon;
import com.football.common.util.JsonCommon;
import com.football.schedule.repository.AreaRepository;
import com.football.schedule.repository.CityProvincialRepository;
import com.football.schedule.repository.CommuneRepository;
import com.football.schedule.repository.CountyDistrictRepository;
import com.football.schedule.service.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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

                        List<Area> areaList = areaRepository.findByCityProvincialIdAndCountyDistrictIdAndCommuneId(
                                cityProvincialId, countyDistrictId, communeId
                        );

                        if (!ArrayListCommon.isNullOrEmpty(areaList)) {
                            areaList.get(0).setStatus(Constant.STATUS_OBJECT.ACTIVE);
                            areaRepository.save(areaList.get(0));
                        } else {
                            List<Area> areaListDistrict = areaRepository.findByCityProvincialIdAndCountyDistrictId(
                                    cityProvincialId, countyDistrictId
                            );
                            //Save ban ghi cho quan
                            if (ArrayListCommon.isNullOrEmpty(areaListDistrict)) {
                                Area area = new Area();
                                area.setCityProvincialId(cityProvincialId);
                                area.setCountyDistrictId(countyDistrictId);
                                area.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                                areaRepository.save(area);
                            }
                            //Save ban ghi den huyen
                            Area area = new Area();
                            area.setCityProvincialId(cityProvincialId);
                            area.setCountyDistrictId(countyDistrictId);
                            area.setCommuneId(communeId);
                            area.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                            areaRepository.save(area);
                        }
                        LOGGER.info(" >>> " + (System.currentTimeMillis() - id) + " >>> " + JsonCommon.objectToJsonNotNull(list.get(i)));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean importFromExcelFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists())
            return false;
        else
            return importFromExcelFile(file);
    }
}
