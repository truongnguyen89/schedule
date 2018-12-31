package com.football.schedule.service.user;

import com.football.common.model.user.User;
import com.football.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * UserRepository: Truong Nguyen
 * Date: 26-Nov-18
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User create(User user) throws Exception {
        return userRepository.save(user);
    }

    @Override
    public Iterable<User> findAll() throws Exception {
        return userRepository.findAll();
    }

    @Override
    public List<User> findByUpdatedAtAfterOrderByUpdatedAt(Date date) throws Exception {
        return userRepository.findByUpdatedAtAfter(date);
    }

    @Override
    public List<User> findByCreatedAtAfterOrderById(Date date) throws Exception {
        return userRepository.findByCreatedAtAfter(date);
    }
}
