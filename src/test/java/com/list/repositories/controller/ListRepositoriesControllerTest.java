package com.list.repositories.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestClientException;

import com.list.repositories.service.FetchRepositories;

@WebMvcTest(ListRepositoriesController.class)
class ListRepositoriesControllerTest {

	

	@Autowired
	MockMvc mvc;

	@MockBean
	FetchRepositories fetchRepoService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getRepoList() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/repo/list-repositories").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void getRepoListMethodArgumentTypeMismatchException() throws Exception {

		/*
		 * MethodArgumentTypeMismatchException
		 */

		mvc.perform(MockMvcRequestBuilders.get("/repo/list-repositories?createdDate=12.10.2020")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isBadRequest());
		/*
		 * ConstraintViolationException
		 */

		mvc.perform(
				MockMvcRequestBuilders.get("/repo/list-repositories?pageSize=200").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isBadRequest());

	}

	@Test
	void getRepoListRestClientException() throws Exception {
		Mockito.when(fetchRepoService.getRepositories(null, null, null, null))
				.thenThrow(new RestClientException("exception"));
		mvc.perform(MockMvcRequestBuilders.get("/repo/list-repositories").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isBadRequest());
	}

}
