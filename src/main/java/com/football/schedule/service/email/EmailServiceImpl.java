package com.football.schedule.service.email;

import com.football.common.constant.Constant;
import com.football.common.email.GmailCommon;
import com.football.common.model.email.Email;
import com.football.common.response.Response;
import com.football.common.util.ArrayListCommon;
import com.football.schedule.repository.EmailRepository;
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
    @Autowired
    EmailRepository emailRepository;

    @Override
    public Email create(Email email) throws Exception {
        return emailRepository.save(email);
    }

    @Override
    public Response sendByGmail(Email email) throws Exception {
        List<Email> emailList = emailRepository.findByStatus(Constant.EMAIL.STATUS.NEW);
        if (ArrayListCommon.isNullOrEmpty(emailList))
            return Response.OBJECT_NOT_FOUND;
        else
            for (Email email1: emailList) {
                GmailCommon.send(email.getToAdress(), email.getSubject(), email.getMessage());
            }
        return Response.OK;
    }
}
