package com.sgit.user.mngr.model;

import org.springframework.data.repository.CrudRepository;

import com.sgit.user.mngr.dto.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
