package com.commerzbank.homework.services;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
@Service
public class ZonedDateTimeService implements IZonedDateTimeService {

	@Override
	public String getZonedDate() {
		ZoneOffset zoneOffset =ZoneOffset.ofHours(1);
		OffsetDateTime dateTime =  OffsetDateTime.of(LocalDateTime.now(), zoneOffset);
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return fmt.format(dateTime);
	}

}
