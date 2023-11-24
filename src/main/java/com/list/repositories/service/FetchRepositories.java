package com.list.repositories.service;

import java.time.LocalDate;

/*
 * author=greshma.john
 * Interface provide one method to fetch repositories based on various optional attributes
 */

public interface FetchRepositories {
	/*
	 * get popular repositories based on createdDate, page size, page and language.
	 * 
	 */

	public String getRepositories(LocalDate attributes, String language, Integer page, Integer pageSize);
}
