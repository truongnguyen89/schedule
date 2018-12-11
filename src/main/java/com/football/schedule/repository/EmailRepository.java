package com.football.schedule.repository;

import com.football.common.model.email.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 11-Dec-18
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface EmailRepository extends CrudRepository<Email, Long> {
    List<Email> findByStatus(int status);
}
