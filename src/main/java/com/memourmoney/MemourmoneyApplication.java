package com.memourmoney;

import com.memourmoney.service.MainMenu;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MemourmoneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemourmoneyApplication.class, args);
    }

    CommandLineRunner run() {
        return args -> {
            MainMenu.main();
        };
    }

}
