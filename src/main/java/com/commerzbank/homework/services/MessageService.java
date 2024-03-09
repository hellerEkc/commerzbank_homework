package com.commerzbank.homework.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MessageService implements IMessageService {

	@Value("${default.message}")
	private String defaultMessage;

	@Override
	public String getMessage(String message) {
		if(StringUtils.hasText(message)) {
			return message;
		}
		return defaultMessage;
    }
}
