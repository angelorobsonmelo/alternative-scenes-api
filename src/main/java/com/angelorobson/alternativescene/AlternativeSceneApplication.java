package com.angelorobson.alternativescene;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AlternativeSceneApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlternativeSceneApplication.class, args);
	}
}
