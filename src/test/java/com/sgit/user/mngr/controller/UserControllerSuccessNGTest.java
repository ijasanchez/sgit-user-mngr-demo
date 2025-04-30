package com.sgit.user.mngr.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sgit.user.mngr.UserApplication;

/*
 * https://www.guru99.com/install-testng-in-eclipse.html
 */

@SpringBootTest(classes = UserApplication.class)
public class UserControllerSuccessNGTest extends AbstractTestNGSpringContextTests  {
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	
	@Value(value = "classpath:findAllResult.json")
	private Resource findAllResult;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerSuccessTest.class);

	@BeforeClass
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test(invocationCount = 100, successPercentage = 100, threadPoolSize = 10)
	public void testHealth() throws Exception {
		LOGGER.info("UserControllerSuccessNGTest#testHealth");
		mockMvc.perform(get("/user-api/health")).andExpect(status().isOk())
				.andExpect(content().string("OK"));
	}
	
	@Test(invocationCount = 100, successPercentage = 100, threadPoolSize = 20)
	public void testFindAll() throws Exception {
		LOGGER.info("UserControllerSuccessNGTest#testFindAll");
		InputStream inputStream = findAllResult.getInputStream();
		String expected = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
		mockMvc.perform(get("/user-api/findAll")).andExpect(status().isOk())
        		.andExpect(content().contentType("application/json"))
				.andExpect(content().string(expected));
	}

}