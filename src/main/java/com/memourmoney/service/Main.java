package com.memourmoney.service;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public class Main {
    public static void main(String[] args) {
        try {
            MainMenu.main();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}