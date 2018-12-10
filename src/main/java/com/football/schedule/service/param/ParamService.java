package com.football.schedule.service.param;

import com.football.common.model.param.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 06-Dec-18
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public interface ParamService {
    Param create(Param param) throws Exception;

    Param findById(String type, String code) throws Exception;

    List<Param> findByStatus(int status) throws Exception;

    Iterable<Param> findAll() throws Exception;

    Param update(Param param) throws Exception;
}
