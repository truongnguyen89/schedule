package com.football.schedule.job;

import com.football.common.constant.Constant;
import com.football.common.util.DateCommon;
import com.football.schedule.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 11-Dec-18
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class SendEmailSchedule {
    @Autowired
    EmailService emailService;

    @Scheduled(fixedDelay = 1000)
    public void sendEmail() {
        try {
            emailService.sendByGmail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
