package com.ssm.zxw.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @author : ZXW
 * @version : 1.0
 * @date : 2021-01-28 11:12
 * @Description :
 */
public class wanwan {
    //定义红色球的集合
    static ArrayList<Integer> redbox = new ArrayList<Integer>();
    //定义蓝色球
    static int bluebox;
    //定义箱子
    static ArrayList<Integer> box = new ArrayList<Integer>();
    //把红球都放到箱子里
    static ArrayList<Integer> useredbox = new ArrayList<Integer>();
    static int usebluebox;
    //定义 红球匹配的个数
    static int reddui = 0;
    static int bluedui = 0;
    public static void kai() {
        System.out.println("开奖了");
        for (int i = 1; i <= 33; i++) {
            box.add(i);
        }
        Random r = new Random();
        //红球
        System.out.print("红球");
        for (int i = 1; i <= 6; i++) {
            //根据索引找到球
            int x = r.nextInt(box.size());
            int redbox1 = box.get(x);
            //删除已经拿出来的球
            box.remove(x);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(redbox1 + " ");
            redbox.add(redbox1);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("蓝球");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bluebox = r.nextInt(16) + 1;
        System.out.println(bluebox);
    }
    public static void main(String[] args) {
        old();
    }



    public static void old(){
        System.out.println("欢迎来来到中国福利彩票双色球,请量力而行，理性购彩");
        Scanner sc = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            System.out.println("请输入您要购买第" + i + "红色球的号码，范围是1到33，且不可重复购买一个号码");
            int useredbox1 = sc.nextInt();
            if (useredbox1 < 1 || useredbox1 > 33) {
                System.out.println("您输入的数字有误，请重新输入");
                i=0;
            } else {
                boolean result = list.contains(useredbox1);
                useredbox.add(useredbox1);
                if(result){
                    System.out.println("您输入有误,请重新输入.请别输入重复的数字.");
                    i -- ;
                }else{
                    list.add(useredbox1);
                }
            }
        }
        System.out.println("请输入您要购买的蓝球的号码，范围在1到16");
        usebluebox = sc.nextInt();
        //判断是否有相同元素

        System.out.println("您现在选好的红球是" + useredbox + "蓝球是" + usebluebox);
        System.out.println("请耐心等待10秒,十秒之后将揭晓结果");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kai();
        //遍历用户  红色相同的个数 调用retainAll方法
        useredbox.retainAll(redbox);
        reddui = useredbox.size();
        if (usebluebox == bluebox) {
            bluedui++;
        }
        if (reddui == 6 && bluedui == 1) {
            System.out.println("恭喜你获得一等奖，奖金1000万元");
        } else if (reddui == 6 && bluedui == 0) {
            System.out.println("恭喜你获得二等奖，奖金500万元");
        } else if (reddui == 5 && bluedui == 1) {
            System.out.println("恭喜你获得三等奖，奖金1万元");
        } else if (reddui == 5 && bluedui == 0 || reddui == 4 && bluedui == 1) {
            System.out.println("恭喜你获得四等奖，奖金3000元");
        } else if (reddui == 4 && bluedui == 0 || reddui == 3 && bluedui == 1) {
            System.out.println("恭喜你获得五等奖，奖金100元");
        } else if (reddui == 2 && bluedui == 1 || reddui == 1 && bluedui == 1 || reddui == 0 && bluedui == 1) {
            System.out.println("恭喜你获得六等奖，奖金5元");
        } else {
            System.out.println("沙雕  你什么奖都没中");
        }
    }


}