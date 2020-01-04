package com.spring.tx;

import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;

import java.sql.*;

/**
 * @Author: LZJ
 * @Date: 2020/1/4 14:00
 * @Version 1.0
 * 演示脏读的发生
 */
public class ReadUncommittedExample {

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
            //关闭自动事务，提交事务需要手动提交
            conn.setAutoCommit(false);
            PreparedStatement prepare = conn.prepareStatement("INSERT INTO account (accountName,user,money) VALUES (?,?,?)");
            prepare.setString(1, accountName);
            prepare.setString(2,name);
            prepare.setInt(3,money);
            prepare.executeUpdate();
            System.out.println("插入事务: 执行插入");
            //让线程休息3秒钟模拟事务还没提交，另外一个线程调查询
            Thread.sleep(3000);
            //最后事务并且没有commit所以这条insert最后是没有插入数据库的
            conn.close();
            System.out.println("插入事务: 插入完成，由于未提交事务被回滚");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void select(String name,Connection conn){
        try {
            PreparedStatement prepared = conn.prepareStatement("SELECT * FROM account where user=?");
            prepared.setString(1,name);
            ResultSet resultSet = prepared.executeQuery();
            System.out.println("查询事务: 执行查询");
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

    public static Thread run(Runnable runnable){
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;
    }

    public static void main(String[] args) {
        Thread insert = run(new Runnable() {
            @Override
            public void run() {
                insert("1","lzj",100);
            }
        });
        Thread select = run(new Runnable() {
            @Override
            public void run() {
                try {
                    //休息500毫秒确保让insert线程先跑
                    Thread.sleep(500);
                    Connection conn = openConnection();
                    // 当前的事务隔离级别是TRANSACTION_READ_UNCOMMITTED即最低级别会出现 可能脏读 情况
                    // 将参数升级成 Connection.TRANSACTION_READ_COMMITTED 即可解决脏读的问题
                    conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                    select("lzj",conn);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            //insert加入到主线程中，主线程会等待他完成
            insert.join();
            select.join();
            System.out.println("==============================");
            System.out.println("插入事务完成后我们再次执行查询");
            Connection conn = openConnection();
            select("lzj",conn);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
