package com.list.repositories.controller;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.list.repositories.service.FetchRepositories;

import jakarta.validation.constraints.PastOrPresent;

/*
 * author=greshma.john
 * 
 * Controller provide one Rest api end point to fetch list of popular git hub repositories from git.api 
 * 
 * 
 */

@RestController
@RequestMapping("repo")
@Validated
public class ListRepositoriesController  {

	FetchRepositories fetchRepos;

	public ListRepositoriesController(FetchRepositories fetchRepos) {
		this.fetchRepos = fetchRepos;
	}

	/*
	 * Rest API endpoint which accept four optional input parameters:
	 * createdDate- Filter Repository list based on created_on
	 * language - Filter out specific programming language
	 * page-get page results. By default 30 results will be fetched per api requests
	 * pageize - change the size of result getting fetched. A maximum of 100 data can be fetched at once.
	 */
	@GetMapping("list-repositories")
	public ResponseEntity<String> getRepoList(
			@RequestParam(required = false) @PastOrPresent LocalDate createdDate,
			@RequestParam(required = false) String language, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) @Range(min = 1, max = 100, message = "range between 1 to 100") Integer pageSize) {
		
		String result = fetchRepos.getRepositories(createdDate, language, page, pageSize);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
