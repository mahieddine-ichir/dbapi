package com.thinatech.dbapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DBApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
@Slf4j
class DBApiApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void on_select_0_with_asc() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/tables/mytable?order_by=mytable_id&start=0&limit=2&sort=asc"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		JSONAssert.assertEquals(loadJson("/on_select_asc.json"), mvcResult.getResponse().getContentAsString(), false);
	}

	@Test
	void on_select_10_with_desc() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/tables/mytable?order_by=mytable_id&start=10&limit=2&sort=desc"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		JSONAssert.assertEquals(loadJson("/on_select_desc.json"), mvcResult.getResponse().getContentAsString(), false);
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

	@Test
	public void on_query() throws Exception {
		String response = "[ " +
				"{\"MYTABLE_ID\": \"1\", \"PROPERTY1\": \"value1\"}" +
				"]";

		mockMvc.perform(MockMvcRequestBuilders.get("/tables/mytable/_search?q=mytable_id:1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().json(response));
	}

	private String loadJson(String resource) throws URISyntaxException, IOException {
		return Files.readString(Paths.get(this.getClass().getResource(resource).toURI()));
	}

}
