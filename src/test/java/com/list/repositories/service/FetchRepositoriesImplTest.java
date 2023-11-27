package com.list.repositories.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.list.repositories.controller.ListRepositoriesController;

@SpringBootTest
public class FetchRepositoriesImplTest {

	public static final String REPOSITORY_JSON_CONST = "{\r\n"
			+ "    \"total_count\": 16093945,\r\n"
			+ "    \"incomplete_results\": true,\r\n"
			+ "    \"items\": [\r\n"
			+ "        {\r\n"
			+ "            \"id\": 60493101,\r\n"
			+ "            \"node_id\": \"MDEwOlJlcG9zaXRvcnk2MDQ5MzEwMQ==\",\r\n"
			+ "            \"name\": \"coding-interview-university\",\r\n"
			+ "            \"full_name\": \"jwasham/coding-interview-university\",\r\n"
			+ "            \"private\": false,\r\n"
			+ "            \"owner\": {\r\n"
			+ "                \"login\": \"jwasham\",\r\n"
			+ "                \"id\": 3771963,\r\n"
			+ "                \"node_id\": \"MDQ6VXNlcjM3NzE5NjM=\",\r\n"
			+ "                \"avatar_url\": \"https://avatars.githubusercontent.com/u/3771963?v=4\",\r\n"
			+ "                \"gravatar_id\": \"\",\r\n"
			+ "                \"url\": \"https://api.github.com/users/jwasham\",\r\n"
			+ "                \"html_url\": \"https://github.com/jwasham\",\r\n"
			+ "                \"followers_url\": \"https://api.github.com/users/jwasham/followers\",\r\n"
			+ "                \"following_url\": \"https://api.github.com/users/jwasham/following{/other_user}\",\r\n"
			+ "                \"gists_url\": \"https://api.github.com/users/jwasham/gists{/gist_id}\",\r\n"
			+ "                \"starred_url\": \"https://api.github.com/users/jwasham/starred{/owner}{/repo}\",\r\n"
			+ "                \"subscriptions_url\": \"https://api.github.com/users/jwasham/subscriptions\",\r\n"
			+ "                \"organizations_url\": \"https://api.github.com/users/jwasham/orgs\",\r\n"
			+ "                \"repos_url\": \"https://api.github.com/users/jwasham/repos\",\r\n"
			+ "                \"events_url\": \"https://api.github.com/users/jwasham/events{/privacy}\",\r\n"
			+ "                \"received_events_url\": \"https://api.github.com/users/jwasham/received_events\",\r\n"
			+ "                \"type\": \"User\",\r\n"
			+ "                \"site_admin\": false\r\n"
			+ "            },\r\n"
			+ "		 \"stargazers_count\": 164453,\r\n"
			+ "            \"watchers_count\": 164453,\r\n"
			+ "            \"language\": \"Shell\"\r\n"
			+ "		}]\r\n"
			+ "}";
	public static final String REPOSITORY_JSON_ERROR_CONST = "{\r\n"
			+ "  \"message\": \"Only the first 1000 search results are available\",\r\n"
			+ "  \"documentation_url\": \"https://docs.github.com/v3/search/\"\r\n" + "}";
	public static final String URL = "https://api.github.com/search/repositories?sort=stars&order=desc&q=stars:>0";
	public static final String CREATED_QUERY = "+created:>=";
	public static final String LANGUAGE = "+language:";

	@InjectMocks
	FetchRepositoriesImpl fetchRepoService;

	@MockBean
	private RestTemplate restTemplate;
	
	

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getRepositoriesDefault() {
		Mockito.when(restTemplate.getForObject(URL , String.class))
		.thenReturn(REPOSITORY_JSON_CONST);
		String repositoryJson = fetchRepoService.getRepositories(null, null, null, null);
		Assertions.assertNotNull(repositoryJson);
	}

	@Test
	void getRepositoriesWithDate() {
		Mockito.when(restTemplate.getForObject(URL + CREATED_QUERY + LocalDate.now(), String.class))
				.thenReturn(REPOSITORY_JSON_CONST);
		String repositoryJson = fetchRepoService.getRepositories(LocalDate.now(), null, null, null);
		Assertions.assertNotNull(repositoryJson);
	}

	@Test
	void getRepositoriesWithDateAndPage() {
		Mockito.when(restTemplate.getForObject(URL + CREATED_QUERY + LocalDate.now() + LANGUAGE + "Java", String.class))
				.thenReturn(REPOSITORY_JSON_CONST);
		String repositoryJson = fetchRepoService.getRepositories(LocalDate.now(), "Java", null, null);
		Assertions.assertNotNull(repositoryJson);
	}

	@Test
	void getRepositoriesWithPagesAndSize() {
		Mockito.when(restTemplate.getForObject(URL + "&per_page=20&page=2", String.class))
				.thenReturn(REPOSITORY_JSON_CONST);
		String repositoryJson = fetchRepoService.getRepositories(null, null, 2, 20);
		Assertions.assertNotNull(repositoryJson);
	}
	@Test
	void getRepositoriesWithNull() {
		Mockito.when(restTemplate.getForObject(URL , String.class))
				.thenReturn(REPOSITORY_JSON_ERROR_CONST);
		String repositoryJson = fetchRepoService.getRepositories(null, null, null, null);
		Assertions.assertNotNull(repositoryJson);
	}
	

}
