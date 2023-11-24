package com.list.repositories.service;

import java.time.LocalDate;

import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

/*
 * author =greshma.john
 * fetch repository list from external api-https://api.github.com/search/repositories.
 */

@Service
@Slf4j
public class FetchRepositoriesImpl implements FetchRepositories {
	
	public static final String ITEM="items";

	@Override
	public String getRepositories(LocalDate date, String language
			,Integer page,Integer pageSize)  {
		/*
		 * external git.api url . sort star repositories order by number of stars
		 */
		String url="https://api.github.com/search/repositories?sort=stars&order=desc";
		/*
		 * Default filtering condition to search for repositories having star count
		 */
		String filterCondition="q=stars:>0";
		/*
		 * additional filter to filter by created date of repositories
		 */
		if(date!=null)
			filterCondition=filterCondition+"+created:>="+date;
		/*
		 * filter by language
		 */
		if(language!=null)
			filterCondition=filterCondition+"+language:"+language;
		/*
		 * specify the number of repository list per request
		 */
		if(pageSize!=null)
			filterCondition=filterCondition+"&per_page="+pageSize;
		/*
		 * specify page
		 */
		if(page!=null)
			filterCondition=filterCondition+"&page="+page;
		
		log.info("filterConditions",filterCondition);
	
		return fetchRepositoriesFromRest(url+"&"+filterCondition);
	}

	/*
	 * RestTemplate API call.
	 */
	
	private String fetchRepositoriesFromRest(String url)  {
		log.info("Final URL : ", url);
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, String.class);
		

	}

	


}
