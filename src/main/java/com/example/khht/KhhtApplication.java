package com.example.khht;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.khht.mapper") //扫描的mapper
@SpringBootApplication
public class KhhtApplication {

	public static void main(String[] args) {
		SpringApplication.run(KhhtApplication.class, args);
	}

}
