package com.revature.bam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BamApplication {

	public static void main(String[] args) {
		SpringApplication.run(BamApplication.class, args);	
	}
}
