package com.commerzbank.homework.services;

public interface IMessageService {

	/**
	 * Return message as parameter. In case that parameter is empty, return default value
	 * @param simple message param
	 * @return massage param or default message
	 */
	String getMessage(String message);
	
}
