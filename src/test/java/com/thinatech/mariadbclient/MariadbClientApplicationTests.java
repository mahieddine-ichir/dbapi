package com.thinatech.mariadbclient;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MariadbClientApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
@Slf4j
class MariadbClientApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void call_api_with_parameters_start_limit_sort() throws Exception {
		String response = "{\"next\": \"http://localhost/tables/mytable?order_by=mytable_id&start=2&limit=2&sort=ASC\", " +
				"\"data\": [" +
				"{\"MYTABLE_ID\": \"1\", \"PROPERTY1\": \"value1\"}, " +
				"{\"MYTABLE_ID\": \"2\", \"PROPERTY1\": \"value2\"}" +
				"]}";

		mockMvc.perform(MockMvcRequestBuilders.get("/tables/mytable?order_by=mytable_id&start=0&limit=2&sort=asc"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().json(response));
	}

	@Test
	void call_api_with_no_parameters() throws Exception {
		String response = "{\"next\": \"http://localhost/tables/mytable?order_by=mytable_id&start=100&limit=100&sort=ASC\", " +
		//String response = "{ " +
				"\"data\": [" +
				"{\"MYTABLE_ID\": \"1\", \"PROPERTY1\": \"value1\"}, " +
				"{\"MYTABLE_ID\": \"2\", \"PROPERTY1\": \"value2\"}," +
				"{\"MYTABLE_ID\": \"3\", \"PROPERTY1\": \"value3\"}," +
				"{\"MYTABLE_ID\": \"4\", \"PROPERTY1\": \"value4\"}," +
				"{\"MYTABLE_ID\": \"5\", \"PROPERTY1\": \"value5\"}," +
				"{\"MYTABLE_ID\": \"6\", \"PROPERTY1\": \"value6\"}," +
				"{\"MYTABLE_ID\": \"7\", \"PROPERTY1\": \"value7\"}," +
				"{\"MYTABLE_ID\": \"8\", \"PROPERTY1\": \"value8\"}" +
				"]}";

		mockMvc.perform(MockMvcRequestBuilders.get("/tables/mytable?order_by=mytable_id"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().json(response));
	}

	@Test
	void call_api_with_sort_desc() throws Exception {
		String response = "{ " +
				"\"data\": [" +
				"{\"MYTABLE_ID\": \"8\", \"PROPERTY1\": \"value8\"}," +
				"{\"MYTABLE_ID\": \"7\", \"PROPERTY1\": \"value7\"}," +
				"{\"MYTABLE_ID\": \"6\", \"PROPERTY1\": \"value6\"}," +
				"{\"MYTABLE_ID\": \"5\", \"PROPERTY1\": \"value5\"}," +
				"{\"MYTABLE_ID\": \"4\", \"PROPERTY1\": \"value4\"}," +
				"{\"MYTABLE_ID\": \"3\", \"PROPERTY1\": \"value3\"}," +
				"{\"MYTABLE_ID\": \"2\", \"PROPERTY1\": \"value2\"}," +
				"{\"MYTABLE_ID\": \"1\", \"PROPERTY1\": \"value1\"}" +
				"]}";

		mockMvc.perform(MockMvcRequestBuilders.get("/tables/mytable?order_by=mytable_id&sort=DESC"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().json(response));
	}

}
