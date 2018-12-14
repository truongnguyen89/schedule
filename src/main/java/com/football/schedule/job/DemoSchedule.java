package com.football.schedule.job;

import com.football.common.constant.Constant;
import com.football.schedule.repository.StadiumManagerRepository;
import com.football.schedule.repository.StadiumRepository;
import com.football.schedule.repository.SubStadiumRepository;
import com.football.schedule.repository.UserRepository;
import com.football.schedule.service.area.AreaService;
import com.football.schedule.service.weather.WeatherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 10-Dec-18
 * Time: 2:58 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class DemoSchedule {

    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.APPLICATION);

    @Autowired
    UserRepository userRepository;

    @Autowired
    StadiumRepository stadiumRepository;

    @Autowired
    StadiumManagerRepository stadiumManagerRepository;

    @Autowired
    SubStadiumRepository subStadiumRepository;

    @Autowired
    AreaService areaService;

    @Autowired
    WeatherService weatherService;

    @Scheduled(fixedDelay = 2000000)
    public void insertDataDemoSchedule() {
//        for (int i = 0; i < 10; i++) {
//            int k = i % 2;
//            if (k == 0) {
//                User user = new User("nguoichoi" + i,
//                        "matkhaunguoichoi" + i,
//                        "Người chơi " + i,
//                        "nqtruong@ecpay.vn",
//                        "0962266682",
//                        "Địa chỉ người chơi " + i,
//                        k + 1,
//                        1
//                );
//                User userNew = userRepository.save(user);
//
//                //Dat san
//            } else {
//                User user = new User(
//                        "quanly" + i,
//                        "matkhauquanly" + i,
//                        "Quản lý " + i,
//                        "nqtruong@ecpay.vn",
//                        "0962266682",
//                        "Địa chỉ quản lý " + i,
//                        k + 1,
//                        1
//                );
//                User userNew = userRepository.save(user);
//
//                int numberOfSubStadium = NumberCommon.getRandomBetweenRange(1, 6);
//                //Tao san va quan ly san
//                Stadium stadium = new Stadium(
//                        "Sân bóng số " + i,
//                        "Địa chỉ sân bóng số " + i,
//                        "nqtruong@ecpay.vn",
//                        "0962266682",
//                        (long) NumberCommon.getRandomBetweenRange(1, 100),
//                        numberOfSubStadium,
//                        k + 1,
//                        1
//                );
//
//                Stadium stadiumNew = stadiumRepository.save(stadium);
//
//                StadiumManager stadiumManager = new StadiumManager();
//                stadiumManager.setStadiumId(stadiumNew.getId());
//                stadiumManager.setUserId(userNew.getId());
//                stadiumManager.setType(1);
//                stadiumManager.setStatus(Constant.STATUS_OBJECT.ACTIVE);
//                stadiumManagerRepository.save(stadiumManager);
//
//                //Tao san nho
//                for (int j = 1; j <= numberOfSubStadium; j++) {
//                    SubStadium subStadium = new SubStadium();
//                    subStadium.setStadiumId(stadiumNew.getId());
//                    subStadium.setNumber(j + "");
//                    subStadium.setLength(40);
//                    subStadium.setWidth(20);
//                    subStadium.setType(1);
//                    subStadium.setStatus(Constant.STATUS_OBJECT.ACTIVE);
//                    subStadiumRepository.save(subStadium);
//                }
//
//            }
//        }
    }

    @Scheduled(fixedDelay = 6000, initialDelay = 100000)
    public void importDataTest() {
        long id = System.currentTimeMillis();
        LOGGER.info("[B][" + id + "] >>>>>>>>>>>>>>>>>>>>>>>>>> Start WeatherSchedule.importDataTest ...");
        weatherService.importWeatherLocalFromFileJson();
        LOGGER.info("[E][" + id + "][Duration = " + (System.currentTimeMillis() - id) + "] >>>>>>>>>>>>>>>>>>>>>>>>>> End WeatherSchedule.importDataTest ...");
    }
}
