package com.spring.tx;

import com.sun.org.apache.regexp.internal.RE;

import java.sql.*;

/**
 * @Author: LZJ
 * @Date: 2020/1/4 15:06
 * @Version 1.0
 * 演示不可重复读的发生
 */
public class ReadCommittedExample {

    static {
        try {
            openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Object lock = new Object();

    public static Connection openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/study?characterEncoding=utf8&useSSL=true", "root", "123456");
        return conn;
    }

    public static void insert(String accountName,String name,int money){
        try {
            Connection conn = openConnection();
            PreparedStatement prepare = conn.prepareStatement("insert INTO account (accountName,user,money) VALUES (?,?,?)");
            prepare.setString(1, accountName);
            prepare.setString(2, name);
            prepare.setInt(3, money);
            prepare.executeUpdate();
            System.out.println("执行插入成功");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void select(String name,Connection conn){
        try {
            PreparedStatement prepared = conn.prepareStatement("SELECT * FROM account where user=?");
            prepared.setString(1,name);
            ResultSet resultSet = prepared.executeQuery();
            System.out.println("执行查询");
            while (resultSet.next()){
                for (int i =1;i<=4;i++){
                    System.out.print(resultSet.getString(i)+",");
                }
                System.out.println();
            }
            if (!resultSet.first()) System.out.println("该表并没有数据");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Thread run(Runnable runnable) {
        Thread thread1 = new Thread(runnable);
        thread1.start();
        return thread1;
    }

    public static void main(String[] args) {
        Thread insert = run(new Runnable() {
            @Override
            public void run() {
                try {
                    //加锁 等待锁释放才会继续执行
                    synchronized (lock){
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                insert("1", "lzj", 100);
            }
        });

        Thread select = run(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection conn = openConnection();
                    conn.setAutoCommit(false);
                    // 当前事务隔离级别是TRANSACTION_READ_COMMITTED即脏读不会出现 但是可能会出现不可重复，幻读情况
                    // 将参数升级成 Connection.TRANSACTION_REPEATABLE_READ 即可解决不可重复读问题
                    conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                    System.out.println("执行第一次查询");
                    //第一次查询读取不到数据
                    select("lzj",conn);
                    //释放锁 模拟正在执行当前事务时 有一个插入事务已经提交
                    synchronized (lock){
                        lock.notify();
                    }
                    System.out.println("==============");
                    System.out.println("执行第二次查询");
                    //休息一下模拟并发 在执行当前事务时 有另外一个事务提交了数据
                    Thread.sleep(500);
                    //由于并发问题导致 第二次查询结果和第一次查询结果 不一致
                    select("lzj",conn);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        try {
            insert.join();
            select.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
