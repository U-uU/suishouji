package com.memourmoney;

import com.memourmoney.service.MainMenuServiceImpl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MemourmoneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemourmoneyApplication.class, args);
    }

//    CommandLineRunner run() {
//        return args -> {
//            MainMenuServiceImpl menu = new MainMenuServiceImpl();
//            menu.main();
//        };
//    }

}
