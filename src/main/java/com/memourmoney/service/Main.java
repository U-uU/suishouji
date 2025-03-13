package com.memourmoney.service;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public class Main {
    public static void main(String[] args) {
        MainMenuServiceImpl menu = new MainMenuServiceImpl();
        menu.main();

    }
}