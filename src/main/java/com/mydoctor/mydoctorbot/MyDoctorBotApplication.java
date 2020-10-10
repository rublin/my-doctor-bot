package com.mydoctor.mydoctorbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MyDoctorBotApplication {


    public static void main(String[] args) {
        SpringApplication.run(MyDoctorBotApplication.class, args);
    }

}
