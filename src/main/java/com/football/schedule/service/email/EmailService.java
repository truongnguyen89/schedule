package com.football.schedule.service.email;

import com.football.common.model.email.Email;
import com.football.common.response.Response;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 11-Dec-18
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */

@Service
public interface EmailService {

    Email create(Email email) throws Exception;

    Response sendByGmail() throws Exception;
}
