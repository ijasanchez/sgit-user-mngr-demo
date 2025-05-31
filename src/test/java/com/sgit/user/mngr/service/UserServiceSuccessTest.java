package com.sgit.user.mngr.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.sgit.user.mngr.dto.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceSuccessTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceSuccessTest.class);

    @Autowired
    private IUserService userService;

    @Test
    public void testGetUser() {
        Iterable<User> users = userService.findAll();
        assertNotNull(users, "The list of users should not be null.");
        LOGGER.info("Retrieved users successfully.");
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("juan@d.com");
        user.setFirstName("Alejandro");
        user.setLastName("Sánchez");
        User newUser = userService.create(user);
        assertNotNull(newUser, "The created user should not be null.");
        LOGGER.info("User created successfully with ID: {}", newUser.getId());
    }

    @Test
    public void testDeleteUser() {
    	
		try {
			userService.deleteById(2);
	        userService.findById(2);
			fail();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

    @Test
    public void testFindAllUser() {
    	try {
			Iterable<User> users = userService.findAll();
			assertNotNull(users);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			fail();
		}
    }
}