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
	void on_select_with_asc() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/tables/x_table?order_by=id&sort=asc&start=0&limit=2"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		JSONAssert.assertEquals(loadJson("/on_select_asc.json"), mvcResult.getResponse().getContentAsString(), false);
	}

	@Test
	void on_select_with_desc() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/tables/x_table?order_by=id&sort=desc&start=0&limit=2"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		JSONAssert.assertEquals(loadJson("/on_select_desc.json"), mvcResult.getResponse().getContentAsString(), false);
	}

	@Test
	void call_api_with_no_parameters() throws Exception {

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/tables/x_table?order_by=id"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		JSONAssert.assertEquals(loadJson("/on_default_parameters.json"), mvcResult.getResponse().getContentAsString(), false);
	}

	@Test
	public void on_search() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/tables/x_table/_search?q=id:1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		JSONAssert.assertEquals(loadJson("/on_search.json"), mvcResult.getResponse().getContentAsString(), false);
	}

	private String loadJson(String resource) throws URISyntaxException, IOException {
		return Files.readString(Paths.get(this.getClass().getResource(resource).toURI()));
	}

}
