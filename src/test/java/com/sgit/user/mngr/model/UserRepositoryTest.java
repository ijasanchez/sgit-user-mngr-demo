package com.sgit.user.mngr.model;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sgit.user.mngr.dto.User;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testDatabaseConnection() {
        Query query = entityManager.createNativeQuery("SELECT 1");
        Object result = query.getSingleResult();
        assertEquals(1, ((Number) result).intValue());
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("john@test.com");
        user.setFirstName("Alejandro");
        user.setLastName("Sánchez");
        user = userRepository.save(user);

        assertTrue(userRepository.existsById(user.getId()));
        assertNotNull(userRepository.findById(user.getId()).orElse(null));
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setEmail("delete@test.com");
        user.setFirstName("Delete");
        user.setLastName("User");
        user = userRepository.save(user);

        userRepository.deleteById(user.getId());
        assertFalse(userRepository.existsById(user.getId()));
    }

    @Test
    public void testModifyUser() {
        User user = new User();
        user.setEmail("modify@test.com");
        user.setFirstName("Modify");
        user.setLastName("User");
        user = userRepository.save(user);

        user.setEmail("modified@test.com");
        user.setFirstName("Modified");
        user.setLastName("User");
        User updatedUser = userRepository.save(user);

        assertTrue(userRepository.existsById(updatedUser.getId()));
        User retrievedUser = userRepository.findById(updatedUser.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals("modified@test.com", retrievedUser.getEmail());
        assertEquals("Modified", retrievedUser.getFirstName());
        assertEquals("User", retrievedUser.getLastName());
    }
}