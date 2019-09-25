package com.jinm.learning.spring.batch.learningspringbatch.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.jinm.learning.spring.batch.learningspringbatch.step.sequential")
public class LearningSpringBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningSpringBatchApplication.class, args);
	}

}
