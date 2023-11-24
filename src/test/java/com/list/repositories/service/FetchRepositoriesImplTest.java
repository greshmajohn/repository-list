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

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
public class FetchRepositoriesImplTest {

	public static final String REPOSITORY_JSON_CONST = "{\r\n" + "  \"total_count\": 42068,\r\n"
			+ "  \"incomplete_results\": false,\r\n" + "  \"items\": [\r\n" + "    {\r\n"
			+ "      \"id\": 165041732,\r\n" + "      \"node_id\": \"MDEwOlJlcG9zaXRvcnkxNjUwNDE3MzI=\",\r\n"
			+ "      \"name\": \"kratos\",\r\n" + "      \"full_name\": \"go-kratos/kratos\",\r\n"
			+ "      \"private\": false,\r\n" + "      \"owner\": {\r\n" + "        \"login\": \"go-kratos\",\r\n"
			+ "        \"id\": 62791634,\r\n" + "        \"node_id\": \"MDEyOk9yZ2FuaXphdGlvbjYyNzkxNjM0\",\r\n"
			+ "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/62791634?v=4\",\r\n"
			+ "        \"gravatar_id\": \"\"\r\n" + "	},\r\n" + "	\"size\": 9445,\r\n"
			+ "      \"stargazers_count\": 21709,\r\n" + "	 \"language\": \"Go\",\r\n" + "   ]\r\n" + "}";
	public static final String REPOSITORY_JSON_ERROR_CONST = "{\r\n"
			+ "  \"message\": \"Only the first 1000 search results are available\",\r\n"
			+ "  \"documentation_url\": \"https://docs.github.com/v3/search/\"\r\n" + "}";
	public static final String URL = "https://api.github.com/search/repositories?sort=stars&order=desc&q=stars:>0";
	public static final String CREATED_QUERY = "+created:>=";
	public static final String LANGUAGE = "+language:";

	@InjectMocks
	FetchRepositoriesImpl fetchRepoService;

	@Mock
	private RestTemplate restTemplate;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getRepositoriesDefault() {

		Mockito.when(restTemplate.getForObject(URL, String.class)).thenReturn(REPOSITORY_JSON_CONST);
		String repositoryJson = fetchRepoService.getRepositories(null, null, null, null);
		Assertions.assertNotNull(repositoryJson);
	}

	@Test
	void getRepositoriesWithDate() {
		Mockito.when(restTemplate.getForObject(URL + CREATED_QUERY + LocalDate.now(), String.class))
				.thenReturn(REPOSITORY_JSON_CONST);
		String repositoryJson = fetchRepoService.getRepositories(LocalDate.now(), null, null, null);
		System.out.println("repositoryJson" + repositoryJson);
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

}
