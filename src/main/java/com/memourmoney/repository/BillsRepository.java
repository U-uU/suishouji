package com.memourmoney.repository;

import com.memourmoney.model.Bills;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BillsRepository {
    private static final String CSV_FILE_PATH = "bills.csv";

    // 读取CSV文件
    public List<Bills> findAll() {
        List<Bills> billsList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            // 跳过标题行
            reader.skip(1);
            List<String[]> records = reader.readAll();

            billsList = records.stream()
                    .map(this::convertToBills)
                    .collect(Collectors.toList());
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return billsList;
    }

    // 保存到CSV文件
    public Bills save(Bills bills) {
        List<Bills> existingBills = findAll();

        // 如果是新增，设置ID
        if (bills.getId() == null) {
            bills.setId(generateNextId(existingBills));
        }

        // 如果是更新，先移除旧记录
        existingBills.removeIf(b -> b.getId().equals(bills.getId()));
        existingBills.add(bills);

        writeToCSV(existingBills);
        return bills;
    }

    // 按类型查询
    public List<Bills> findByType(String type) {
        return findAll().stream()
                .filter(bill -> bill.getType().equals(type))
                .collect(Collectors.toList());
    }

    // 按时间范围查询
    public List<Bills> findByTimeBetween(LocalDate startTime, LocalDate endTime) {
        return findAll().stream()
                .filter(bill ->
                        !bill.getTime().isBefore(startTime) &&
                                !bill.getTime().isAfter(endTime))
                .collect(Collectors.toList());
    }

    // 按ID删除
    public void deleteById(Long id) {
        List<Bills> existingBills = findAll();
        existingBills.removeIf(bill -> bill.getId().equals(id));
        writeToCSV(existingBills);
    }

    // 写入CSV
    private void writeToCSV(List<Bills> billsList) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE_PATH))) {
            // 写入标题
            writer.writeNext(new String[]{
                    "id", "name", "account", "type", "total", "time", "desc"
            });

            // 写入数据
            billsList.forEach(bill -> writer.writeNext(new String[]{
                    String.valueOf(bill.getId()),
                    bill.getName(),
                    bill.getAccount(),
                    bill.getType(),
                    String.valueOf(bill.getTotal()),
                    bill.getTime().toString(),
                    bill.getDesc()
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 生成下一个ID
    private Long generateNextId(List<Bills> existingBills) {
        return existingBills.stream()
                .mapToLong(Bills::getId)
                .max()
                .orElse(0L) + 1;
    }

    // 将CSV行转换为Bills对象
    private Bills convertToBills(String[] record) {
        Bills bills = new Bills();
        bills.setId(Long.parseLong(record[0]));
        bills.setName(record[1]);
        bills.setAccount(record[2]);
        bills.setType(record[3]);
        bills.setTotal(Double.parseDouble(record[4]));
        bills.setTime(LocalDate.parse(record[5]));
        bills.setDesc(record[6]);
        return bills;
    }
}
