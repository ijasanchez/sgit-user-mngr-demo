package com.sgit.user.mngr.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerSuccessTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("classpath:findAllResult.json")
    private Resource findAllResult;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerSuccessTest.class);

    @Value("${local.server.port}")
    private int port;

    private static final String URL_PREFIX_TEMPLATE = "http://localhost:%s/user-api/";

    @Test
    public void testFindAll() {
        try {
            String url = String.format(URL_PREFIX_TEMPLATE, port) + "findAll";
            ResponseEntity<JsonNode> responseEntity = restTemplate.getForEntity(url, JsonNode.class);

            // Evaluate status
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

            // Compare response
            try (InputStream inputStream = findAllResult.getInputStream()) {
                JsonNode responseExpectedNode = objectMapper.readTree(inputStream);
                JsonNode responseExpectedBodyNode = responseExpectedNode.path("response");
                JsonNode responseActualBodyNode = responseEntity.getBody().path("response");

                String expectedHash = getMd5(responseExpectedBodyNode);
                String actualHash = getMd5(responseActualBodyNode);

                assertEquals(expectedHash, actualHash, "Los hashes MD5 no coinciden.");
            }

        } catch (Exception e) {
            LOGGER.error("Error en testFindAll", e);
            fail("Excepción durante la ejecución de testFindAll: " + e.getMessage());
        }
    }

    private static String getMd5(JsonNode source) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(source.toString().getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().withUpperCase().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo MD5 no disponible", e);
        }
    }
}