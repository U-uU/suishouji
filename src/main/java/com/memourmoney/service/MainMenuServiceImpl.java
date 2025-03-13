package com.memourmoney.service;

import com.memourmoney.dto.BillsDTO;
import com.memourmoney.model.Bills;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class MainMenuServiceImpl implements MainMenuService{
    public MainMenuServiceImpl() {
    }

    static ArrayList<Bills> billsList = new ArrayList<>();
    public static void main(){
        ArrayList<Bills> billsListFrom = readFromCSV();
        billsList.addAll(billsListFrom);
        MainMenuServiceImpl menu = new MainMenuServiceImpl();
//        menu.run();
    }

    public static void showMenu(){
        System.out.println("-------------随手记------------");
        System.out.println("1.添加账务  2.删除账务  3.查询账务  4.退出系统");
        System.out.println("请输入功能序号【1-4】");
    }
//    public void run(){
//        showMenu();
//        Scanner scanner = new Scanner(System.in);
//        boolean flag = true;
//        while(flag) {
//            int opt = scanner.nextInt();
//            switch (opt) {
//                case 1 -> {
//                    System.out.println("已选择：添加账务");
//                    addBills();
//                }
//                case 2 -> {
//                    System.out.println("已选择：删除账务");
//                    deleteBills();
//                }
//                case 3 -> {
//                    System.out.println("已选择：查询账务");
//                    select();
//                }
//                case 4 -> flag = false;
//                default -> System.out.println("请重新输入：");
//            }
//        }
//        // 退出系统前保存数据
//        try {
//            saveMenuToCSV(billsList);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("退出系统");
//
//    }

    @Override
    public List<BillsDTO> deleteBills(Long opt) {
        billsList.remove(opt - 1);
        return billsList.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<BillsDTO> addBills(BillsDTO billsDTO){
        Bills bills = new Bills();
        BeanUtils.copyProperties(billsDTO, bills);
        billsList.add(bills);
        try {
            saveMenuToCSV(billsList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("添加账务成功！");
        return billsList.stream().map(this::convertToDTO).toList();
    }

    //查询账务
//    public void select() {
//        System.out.println("随手记>>账务查询");
//        System.out.println("请选择您要查询的类型:");
//        System.out.println("1.查询所有  2.按照时间区间查询  3.按照收入和支出的类型查询");
//        Scanner scanner = new Scanner(System.in);
//        int opt = scanner.nextInt();
//        switch (opt) {
//            case 1 -> selectAll();
//            case 2 -> selectByTime();
//            case 3 -> selectByType();
//            default -> System.out.println("请重新输入");
//        }
//    showMenu();
//
//    }

    // 随手记>>账务查询>>按照类型查询(收入/支出)
    @Override
    public List<BillsDTO> selectByType(String type) {
        List<Bills> billsListSelectedByType = billsList.stream()
                .filter(bills -> {
                    String type1 = bills.getType();
                    return type1.equals(type);
        }).toList();
        return billsListSelectedByType.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<BillsDTO> selectByTime(LocalDate start, LocalDate end) {
        List<Bills> billsListSelectedByTime = billsList.stream().filter(bills -> {
            LocalDate time = bills.getTime();
            return time.isBefore(end) && time.isAfter(start);
        }).toList();
        return billsListSelectedByTime.stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<BillsDTO> selectByTime(LocalDate selectTime) {
        List<Bills> billsListSelectedByTime = billsList.stream().filter(bills -> {
            LocalDate time = bills.getTime();
            return time.isEqual(selectTime);
        }).toList();
        return billsListSelectedByTime.stream().map(this::convertToDTO).toList();
    }

    public List<BillsDTO> selectAll() {
        if (billsList.isEmpty()) {
            ArrayList<Bills> billsListFrom = readFromCSV();
            billsList.addAll(billsListFrom);
        }
        System.out.println("已选择：查询所有");
        print(billsList);
        return billsList.stream().map(this::convertToDTO).toList();
    }
    public static void print(List<Bills> billsList){
        System.out.println("ID\t\t类别\t\t账户\t\t类型\t\t金额\t\t\t时间\t\t\t\t备注");
        for (int i = 0; i < billsList.size(); i++) {
            Bills bills = billsList.get(i);
            System.out.println(i+1+"\t\t"+bills.getName()+"\t\t"+bills.getAccount()+"\t\t"+bills.getType()+"\t\t"+bills.getTotal()+"\t\t"+bills.getTime()+"\t\t"+bills.getDesc());
        }
    }
    // 写入文件
    public static void saveMenuToCSV(List<Bills> accounts) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter("bills.csv")) {
            for (int i = 0; i < accounts.size(); i++) {
                writer.println(
                        i + "," +
                                accounts.get(i).getName() + "," +
                                accounts.get(i).getAccount() + "," +
                                accounts.get(i).getType() + "," +
                                accounts.get(i).getTotal() + "," +
                                accounts.get(i).getTime() + "," +
                                accounts.get(i).getDesc() + ","
                );
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
    }

    // 读取文件
    public static ArrayList<Bills> readFromCSV() {
        File file = new File("./bills.csv");
        Boolean bool;

        // 如果文件不存在则创建
        if (!file.exists()) {
            try {
                bool = file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        ArrayList<Bills> accounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("bills.csv"))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Bills account = new Bills(Long.parseLong(data[0]),
                        data[1],
                        data[2],
                        data[3],
                        Double.parseDouble(data[4]),
                        LocalDate.parse(data[5]),
                        data[6]);
                accounts.add(account);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    private BillsDTO convertToDTO(Bills bills) {
        BillsDTO billsDTO = new BillsDTO();
        BeanUtils.copyProperties(bills, billsDTO);
        return billsDTO;
    }
}
