package com.football.schedule.job;

import com.football.common.model.email.Email;
import com.football.common.model.user.User;
import com.football.common.util.ArrayListCommon;
import com.football.common.util.DateCommon;
import com.football.common.util.JsonCommon;
import com.football.schedule.component.DataAccess;
import com.football.schedule.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author : truongnq
 * @Date time: 2018-12-28 22:44
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserNotificationSchedule {
    @Autowired
    UserService userService;

    @Autowired
    DataAccess dataAccess;

//    @Scheduled(fixedDelay = 1000)
//    public void sendEmailLogin() {
//        try {
//            List<User> userList = userService.findByUpdatedAtAfterOrderByUpdatedAt(DateCommon.addSecond(new Date(), -5));
//            if (!ArrayListCommon.isNullOrEmpty(userList))
//                for (User user : userList) {
//                    Email email = new Email("nqtruong@ecpay.vn", "[" + user.getId() + "]Mới đăng nhập", JsonCommon.objectToJsonLog(user));
//                    dataAccess.saveEmail(email);
//                }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Scheduled(fixedDelay = 1000)
//    public void sendEmailSigup() {
//        try {
//            List<User> userList = userService.findByCreatedAtAfterOrderById(DateCommon.addSecond(new Date(), -5));
//            if (!ArrayListCommon.isNullOrEmpty(userList))
//                for (User user : userList) {
//                    Email email = new Email("nqtruong@ecpay.vn", "[" + user.getId() + "]Mới đăng ký", JsonCommon.objectToJsonLog(user));
//                    dataAccess.saveEmail(email);
//                }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
