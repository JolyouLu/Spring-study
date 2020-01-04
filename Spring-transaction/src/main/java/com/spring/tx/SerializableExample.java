package com.spring.tx;

import java.sql.*;

/**
 * @Author: LZJ
 * @Date: 2020/1/4 15:44
 * @Version 1.0
 * 演示幻读的发生
 */
public class SerializableExample {
    static {
        try {
            openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public static boolean select(String name,Connection conn){
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
                return false;
            }
            if (!resultSet.first()) {
                System.out.println("该表并没有数据");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
                    Connection conn = openConnection();
                    conn.setAutoCommit(false);
                    // 当前事务隔离级别是TRANSACTION_REPEATABLE_READ即脏读、不可重复读不会出现 但是可能幻读情况
                    // 设置TRANSACTION_SERIALIZABLE是序列化的每个select后会加锁 并发情况下 线程会因为 争夺锁导致死锁
                    // TRANSACTION_SERIALIZABLE(一般是不会使用到的)因为使用该事务隔离级别表示你的sql语句是不能并发执行的
                    conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                    //检测表中有没有lzj的数据 没有就插入
                    if (select("lzj", conn)){
                        insert("1", "lzj", 100);
                    }
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread insert2 = run(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection conn = openConnection();
                    conn.setAutoCommit(false);
                    // 当前事务隔离级别是TRANSACTION_REPEATABLE_READ即脏读、不可重复读不会出现 但是可能幻读情况
                    // 设置TRANSACTION_SERIALIZABLE是序列化的每个select后会加锁 并发情况下 线程会因为 争夺锁导致死锁
                    // TRANSACTION_SERIALIZABLE(一般是不会使用到的)因为使用该事务隔离级别表示你的sql语句是不能并发执行的
                    conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                    //检测表中有没有lzj的数据 没有就插入
                    if (select("lzj", conn)){
                        insert("1", "lzj", 100);
                    }
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            //最后我们会发现数据库 同时插入了2条数据
            //这个就是幻读 2个线程同时进行读取后都以为该表没数据了并且同时插入了一条数据
            insert.join();
            insert2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
