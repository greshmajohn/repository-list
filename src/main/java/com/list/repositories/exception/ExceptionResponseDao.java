package com.list.repositories.exception;



import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * author=greshma.john
 * Exception Response dao class with attributes:
 * title: title of exception
 * exceptioMessage - Exception message thrown by JVM or custom exceptions
 * status: HttpStatus name
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseDao {
	
	String title;
	String exceptionMessages;
	String status;

}
