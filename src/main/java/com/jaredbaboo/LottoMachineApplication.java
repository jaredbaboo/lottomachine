package com.jaredbaboo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = {"com.jaredbaboo.LottoMachine.controller",
		"com.jaredbaboo.LottoMachine.services",
		"com.jaredbaboo.LottoMachine.repos",
		"com.jaredbaboo.LottoMachine.models"})
public class LottoMachineApplication {


	public static void main(String[] args) {
		SpringApplication.run(LottoMachineApplication.class, args);
	}

}
