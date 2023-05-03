package com.hibit2.hibit2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;

//@SpringBootApplication(exclude ={ContextStackAutoConfiguration.class})
@EnableAutoConfiguration(exclude = {ContextStackAutoConfiguration.class, SecurityAutoConfiguration.class,})
public class Hibit2Application {

	public static void main(String[] args) {
		SpringApplication.run(Hibit2Application.class, args);
	}

}
