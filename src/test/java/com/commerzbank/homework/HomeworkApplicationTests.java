package com.commerzbank.homework;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.commerzbank.homework.controller.MessagesController;
import com.commerzbank.homework.domain.MessageDTO;
import com.commerzbank.homework.services.IMessageService;
import com.commerzbank.homework.services.IZonedDateTimeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class HomeworkApplicationTests {
	private static final String DEFAULT_MESSAGE = "Homework";

	private static final String CUSTOM_MESSAGE = "HelloWorld";
	
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IZonedDateTimeService dateTimeService;
	@Autowired
	private MessagesController messagesController;

	private MockMvc mockMvc;

	@Test
	void testDefaultMessage() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(messagesController).build();

		try {
			MvcResult mvcResult = this.mockMvc
					.perform(MockMvcRequestBuilders.get("/messages").contentType(MediaType.APPLICATION_JSON))
					.andReturn();
			assertMvcResult(mvcResult);
			
			MessageDTO messageDTO =  parseMessage(mvcResult.getResponse().getContentAsString());
	        assertEquals(DEFAULT_MESSAGE, messageDTO.getMessage());
	        assertEquals(getZonedDate() , messageDTO.getDate());
	        
		} catch (Exception ex) {
			fail();
		}

	}
	
	@Test
	void testCustomParamMessage() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(messagesController).build();
		
		try {
			MvcResult mvcResult = this.mockMvc
					.perform(MockMvcRequestBuilders.get("/messages" + "?message=" + CUSTOM_MESSAGE).contentType(MediaType.APPLICATION_JSON))
					.andReturn();
		
			assertMvcResult(mvcResult);
			MessageDTO messageDTO =  parseMessage(mvcResult.getResponse().getContentAsString());
	        assertEquals(CUSTOM_MESSAGE, messageDTO.getMessage());
	        assertEquals(getZonedDate() , messageDTO.getDate());
	        
		} catch (Exception ex) {
			fail();
		}

	}
	
	private void assertMvcResult(MvcResult mvcResult) throws UnsupportedEncodingException {
		assertNotNull(mvcResult);
		assertNotNull(mvcResult.getResponse());
		assertNotNull(mvcResult.getResponse().getContentAsString());	
	}

	private MessageDTO parseMessage(String message) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.readValue(message, MessageDTO.class);
		} catch (JsonProcessingException e) {
		    throw e;
		}
	}
	
	private String getZonedDate() {
		ZoneOffset zoneOffset =ZoneOffset.ofHours(1);
		OffsetDateTime dateTime =  OffsetDateTime.of(LocalDateTime.now(), zoneOffset);
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return fmt.format(dateTime);
	}


}
