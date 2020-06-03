package com.brillio.booking.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan
@EnableScheduling
public class BookingServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(BookingServiceMain.class, args);
    }
}
