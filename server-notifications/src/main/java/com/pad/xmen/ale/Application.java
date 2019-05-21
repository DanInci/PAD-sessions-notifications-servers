package com.pad.xmen.ale;

import com.pad.xmen.ale.persistence.RoomDAO;
import com.pad.xmen.ale.persistence.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application {

	public static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private RoomRepository roomRepository;

	@Scheduled(initialDelay = 60000, fixedDelay = 600000)
	public void cleanRooms() {
		LocalDateTime now = LocalDateTime.now();
		int cleanedRoomNo = 0;
		List<RoomDAO> rooms = roomRepository.findAllByFinishedAtBefore(now.minusMinutes(10));
		for(RoomDAO room : rooms) {
			cleanedRoomNo++;
			roomRepository.delete(room);
		}
		log.info("Cleaned " + cleanedRoomNo + " rooms");
	}

}
