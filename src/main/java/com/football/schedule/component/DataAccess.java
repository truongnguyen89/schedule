package com.football.schedule.component;

import com.football.common.model.email.Email;
import com.football.schedule.repository.EmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DataAccess {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataAccess.class);

    @Autowired
    EmailRepository emailRepository;

    ExecutorService executorService;
    private int numThreads = 10;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(numThreads);
    }

    public void saveEmail(Email email) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                emailRepository.save(email);
                try {
                } catch (Exception e) {
                    LOGGER.error("Exception saveToken ", e);
                }
            }
        });
    }

}
