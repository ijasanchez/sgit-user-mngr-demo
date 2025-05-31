package com.sgit.user.mngr.service;

import com.sgit.user.mngr.dto.User;
import com.sgit.user.mngr.exception.ResourceNotFoundException;

public interface IUserService {

    User create(User user);

    void delete(User user) throws ResourceNotFoundException;
    
    void deleteById(int id) throws ResourceNotFoundException;

    Iterable<User> findAll();

    User findById(int id) throws ResourceNotFoundException;
    
    User update(User user) throws ResourceNotFoundException;
    
}
