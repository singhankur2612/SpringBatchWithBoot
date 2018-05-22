package com.test.batchtestapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.sql.Insert;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableBatchProcessing
public class FirstBatchWithDB {

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(FirstBatchWithDB.class, args)));
		
	}

}
