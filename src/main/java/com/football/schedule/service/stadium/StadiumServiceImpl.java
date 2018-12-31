package com.football.schedule.service.stadium;

import com.football.common.model.stadium.Stadium;
import com.football.common.repository.StadiumRepository;
import com.football.schedule.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 04-Dec-18
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class StadiumServiceImpl extends BaseService implements StadiumService {
    @Autowired
    StadiumRepository stadiumRepository;

    @Override
    public Stadium create(Stadium stadium) throws Exception {
        return stadiumRepository.save(stadium);
    }

    @Override
    public Stadium findById(Long id) throws Exception {
        return stadiumRepository.findOne(id);
    }

    @Override
    public List<Stadium> findByStatus(int status) throws Exception {
        return stadiumRepository.findByStatus(status);
    }

    @Override
    public Iterable<Stadium> findAll() throws Exception {
        return stadiumRepository.findAll();
    }

    @Override
    public Stadium update(Stadium stadium) throws Exception {
        return stadiumRepository.save(stadium);
    }
}
