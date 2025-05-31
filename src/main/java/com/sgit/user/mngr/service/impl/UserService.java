package com.sgit.user.mngr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgit.user.mngr.dto.User;
import com.sgit.user.mngr.exception.ResourceNotFoundException;
import com.sgit.user.mngr.model.UserRepository;
import com.sgit.user.mngr.service.IUserService;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public void deleteById(int id) throws ResourceNotFoundException {
    		if (repository.findById(id).isPresent()) 
		{	
			repository.deleteById(id);
		}
    		else
    		{
    			throw new ResourceNotFoundException("User", "id", Integer.toString(id));
    		}
    }

    @Override
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(int id) throws ResourceNotFoundException {
    		if (repository.findById(id).isPresent()) 
    		{
    			return repository.findById(id).get();
    		}
    		else 
    		{
    			throw new ResourceNotFoundException("User", "id",  Integer.toString(id));
    		}
    }

    @Override
    public User update(User user) throws ResourceNotFoundException {
    		if (repository.findById(user.getId()).isPresent()) 
		{	
			return repository.save(user);
		}
    		throw new ResourceNotFoundException("User", "id", Integer.toString(user.getId()));	        
    }
    
    @Override
    public void delete(User user) throws ResourceNotFoundException {
    		if (repository.findById(user.getId()).isPresent()) 
		{	
			repository.delete(user);
		}
    		throw new ResourceNotFoundException("User", "id",  Integer.toString(user.getId()));	        
    }
}
