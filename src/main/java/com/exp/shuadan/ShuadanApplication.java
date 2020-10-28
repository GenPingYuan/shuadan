package com.exp.shuadan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.exp.shuadan.mapper") //扫描的mapper
@SpringBootApplication
public class ShuadanApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShuadanApplication.class, args);
	}

}
