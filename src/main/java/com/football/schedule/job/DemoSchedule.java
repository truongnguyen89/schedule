package com.football.schedule.job;

import com.football.common.constant.Constant;
import com.football.common.email.GmailCommon;
import com.football.common.util.DateCommon;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 10-Dec-18
 * Time: 2:58 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class DemoSchedule {
    @Scheduled(fixedDelay = 20000)
    public void scheduleTaskWithFixedRate() {
        System.out.println(" >>> scheduleTaskWithFixedRate " + DateCommon.convertDateToStringByPattern(new Date(), Constant.DATE.FORMAT.FULL_DATE_SSS) + " <<<");
    }

    @Scheduled(cron = "0 0 16,19 * * *")
    public void scheduleTaskWithCronExpression() {
        GmailCommon.send("nqtruong@ecpay.vn", "Test gui mail",
                "Thoi gian hien tai la " + DateCommon.convertDateToStringByPattern(new Date(), Constant.DATE.FORMAT.FULL_DATE_SSS));
    }
}
