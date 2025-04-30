package com.sgit.user.mngr.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sgit.user.mngr.exception.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceFailureTest {

    @Autowired
    private IUserService userService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceSuccessTest.class);


    @Test
    public void testDeleteById() {
    	try {
			userService.deleteById(1000);
			fail();
		} catch (ResourceNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		}
    }

    @Test
    public void testFindById() {
		try {
			userService.findById(1000);
			fail();
		} catch (ResourceNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		}
    }
}
