package com.commerzbank.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commerzbank.homework.domain.MessageDTO;
import com.commerzbank.homework.services.IMessageService;
import com.commerzbank.homework.services.IZonedDateTimeService;

@RestController
@RequestMapping("messages")
public class MessagesController {
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IZonedDateTimeService dateTimeService;

	@GetMapping(produces = "application/json")
	public MessageDTO getMessage(@RequestParam(name = "message", required = false) String message) {
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setDate(dateTimeService.getZonedDate());
		messageDTO.setMessage(messageService.getMessage(message));

		return messageDTO;
	}

}
