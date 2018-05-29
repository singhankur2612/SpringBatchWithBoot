package com.test.batchtestapp;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableBatchProcessing
public class FirstBatchWithDB {
	
	public static void main(String[] args) {
		System.out.println("*********************in FirstBatchWithDB.main*************************");
		System.exit(SpringApplication.exit(SpringApplication.run(FirstBatchWithDB.class, args)));
		
	}

}
