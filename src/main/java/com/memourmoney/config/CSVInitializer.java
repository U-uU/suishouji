package com.memourmoney.config;

import com.opencsv.CSVWriter;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class CSVInitializer {
    public static final String CSV_FILE_PATH = "/bills.csv";
    @PostConstruct
    public void initializeCSV() {
        File file = new File(CSV_FILE_PATH);
        if (!file.exists()) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(file))){
                writer.writeNext(new String[]{
                        "id", "name", "account", "type", "total", "time", "desc"
                });
            } catch (IOException e) {
                e.printStackTrace();
                // throw new RuntimeException(e);
            }
        }

    }
}
