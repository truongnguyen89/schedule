package com.football.schedule.service.email;

import com.football.common.util.JsonCommon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.football.common.constant.Constant;
import com.football.common.email.GmailCommon;
import com.football.common.model.email.Email;
import com.football.common.response.Response;
import com.football.common.util.ArrayListCommon;
import com.football.common.repository.EmailRepository;
import com.football.schedule.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 11-Dec-18
 * Time: 4:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class EmailServiceImpl extends BaseService implements EmailService {

    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.EMAIL);
    @Autowired
    EmailRepository emailRepository;

    @Override
    public Email create(Email email) throws Exception {
        return emailRepository.save(email);
    }

    @Override
    public Response sendByGmail() throws Exception {
        List<Email> emailList = emailRepository.findByStatus(Constant.EMAIL.STATUS.NEW);
        if (ArrayListCommon.isNullOrEmpty(emailList))
            return Response.OBJECT_NOT_FOUND;
        else
            for (Email email : emailList) {
                long id = System.currentTimeMillis();
                LOGGER.info("[B][" + email.getId() + "] send email " + JsonCommon.objectToJsonNotNull(email));
                Response response = GmailCommon.send(email.getToAdress(), email.getSubject(), email.getMessage());
                if (response.getResponseCode().equals(Response.OK.getResponseCode()))
                    email.setStatus(Constant.EMAIL.STATUS.SENT);
                else
                    email.setStatus(Constant.EMAIL.STATUS.ERROR);
                emailRepository.save(email);
                LOGGER.info("[E][" + email.getId() + "][Duration = " + (System.currentTimeMillis() - id) + "] response send email " + response.toString());
            }
        return Response.OK;
    }
}
