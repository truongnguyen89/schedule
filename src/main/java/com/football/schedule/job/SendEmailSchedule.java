package com.football.schedule.job;

import com.football.common.constant.Constant;
import com.football.common.util.DateCommon;
import com.football.schedule.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 11-Dec-18
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class SendEmailSchedule {
    @Autowired
    EmailService emailService;

    @Scheduled(fixedDelay = 2000)
    public void sendEmail() {
        System.out.println(" >>> scheduleTaskWithFixedRate " + DateCommon.convertDateToStringByPattern(new Date(), Constant.DATE.FORMAT.FULL_DATE_SSS) + " <<<");
    }
}
