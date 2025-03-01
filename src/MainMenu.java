import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainMenu {
    static ArrayList<Bills> billsList = new ArrayList<>();
    // 类加载时第一时间执行，向billsList中添加初始化的数据
    static {
        billsList.add(
                new Bills("吃饭","现金","支出",234.0,"2023-09-18","聚会"));
        billsList.add(
                new Bills("工资","交行","收入",4330.0,"2023-10-18","开工资"));
        billsList.add(
                new Bills("吃饭","现金","支出",22.0,"2023-08-29","吃饭"));
        billsList.add(
                new Bills("衣服","现金","支出",431.0,"2023-09-11","约会"));
        billsList.add(
                new Bills("吃饭","现金","支出",24.0,"2023-08-11","聚会"));
        billsList.add(
                new Bills("家电","建行","支出",2000.0,"2024-01-01","买个电视"));
    }
    public static void main(String[] args) {
        run();
    }

    public static void showMenu(){
        System.out.println("-------------随手记------------");
        System.out.println("1.添加账务  2.删除账务  3.查询账务  4.退出系统");
        System.out.println("请输入功能序号【1-4】");
    }
    public static void run(){
        showMenu();
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while(flag) {
            int opt = scanner.nextInt();
            switch (opt) {
                case 1 -> {
                    System.out.println("已选择：添加账务");
                    addBills();
                }
                case 2 -> {
                    System.out.println("已选择：删除账务");
                    deleteBills();
                }
                case 3 -> {
                    System.out.println("已选择：查询账务");
                    select();
                }
                case 4 -> flag = false;
                default -> System.out.println("请重新输入：");
            }
        }
        System.out.println("退出系统");

    }

    private static void deleteBills() {
        System.out.println("随手记>>删除账务");
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要删除第几条账务");
        int opt = scanner.nextInt();
        billsList.remove(opt - 1);
        showMenu();
    }

    private static void addBills() {
        System.out.println("随手记>>添加账务");
        Scanner scanner = new Scanner(System.in);
        Bills bills = new Bills();
        System.out.println("请输入账务类别(用途)：");
        bills.setName(scanner.next());
        System.out.println("请输入账户：");
        bills.setAccount(scanner.next());
        System.out.println("请输入类型：");
        bills.setType(scanner.next());
        System.out.println("请输入金额：");
        bills.setTotal(scanner.nextDouble());
        System.out.println("请输入时间：");
        bills.setTime(scanner.next());
        System.out.println("请输入备注：");
        bills.setDesc(scanner.next());

        billsList.add(bills);
        System.out.println("添加账务成功！");
        showMenu();
    }

    //查询账务
    public static void select() {
        System.out.println("随手记>>账务查询");
        System.out.println("请选择您要查询的类型:");
        System.out.println("1.查询所有  2.按照时间区间查询  3.按照收入和支出的类型查询");
        Scanner scanner = new Scanner(System.in);
        int opt = scanner.nextInt();
        switch (opt) {
            case 1 -> selectAll();
            case 2 -> selectByTime();
            case 3 -> selectByType();
            default -> System.out.println("请重新输入");
        }
    showMenu();

    }

    private static void selectByType() {
        System.out.println("随手记>>账务查询>>按照类型查询");
        System.out.println("请输入查询类型：收入or支出");
        Scanner scanner = new Scanner(System.in);
        String type = scanner.next();
        List<Bills> billsList1 = billsList.stream()
                .filter(bills -> {
                    String type1 = bills.getType();
                    return type1.equals(type);
        }).toList();
        print(billsList1);
    }

    private static void selectByTime() {
        System.out.println("随手记>>账务查询>>按照时间查询");
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("请输入开始时间：");
        String start = scanner.next();
        System.out.println("请输入结束时间：");
        String end = scanner.next();
        List<Bills> billsList1 = billsList.stream().filter(bills -> {
            String time = bills.getTime();
            try {
                Date startDate = format.parse(start);
                Date endDate = format.parse(end);
                Date timeDate = format.parse(time);
                if (timeDate.before(endDate) && timeDate.after(startDate)) {
                    return true;
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return false;
        }).toList();
        print(billsList1);
    }

    private static void selectAll() {
        System.out.println("已选择：查询所有");
        print(billsList);
    }
    public static void print(List<Bills> billsList){
        System.out.println("ID\t\t类别\t\t账户\t\t类型\t\t金额\t\t\t时间\t\t\t\t备注");
        for (int i = 0; i < billsList.size(); i++) {
            Bills bills = billsList.get(i);
            System.out.println(i+1+"\t\t"+bills.getName()+"\t\t"+bills.getAccount()+"\t\t"+bills.getType()+"\t\t"+bills.getTotal()+"\t\t"+bills.getTime()+"\t\t"+bills.getDesc());
        }
    }
}
