package com.spring.tx;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author: LZJ
 * @Date: 2020/1/4 17:37
 * @Version 1.0
 * 使用spring的事务
 */
public class SpringTransaction {
    private static String url = "jdbc:mysql://localhost:3306/study?characterEncoding=utf8&useSSL=true";
    private static String user = "root";
    private static String password = "123456";

    public static Connection openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/study?characterEncoding=utf8&useSSL=true", "root", "123456");
        return conn;
    }

    public static void main(String[] args) {
        //获取一个数据源
        final DriverManagerDataSource ds = new DriverManagerDataSource(url, user, password);
        //创建一个事务模板
        final TransactionTemplate template = new TransactionTemplate();
        //为该模板设置一个事务管理器
        template.setTransactionManager(new DataSourceTransactionManager(ds));

        template.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                Connection conn = DataSourceUtils.getConnection(ds);
                Object savePoint = null;
                try {
                    {
                        //插入
                        PreparedStatement prepare = conn.prepareStatement("insert INTO account (accountName,user,money) VALUES (?,?,?)");
                        prepare.setString(1, "111");
                        prepare.setString(2, "aaaa");
                        prepare.setInt(3, 10000);
                        prepare.executeUpdate();
                    }
                    // 设置保存点 如果后面有错误事务会 回滚到保存点 提交保存到中的内容
                    savePoint = transactionStatus.createSavepoint();
                    {
                        // 插入
                        PreparedStatement prepare = conn.prepareStatement("insert INTO account (accountName,user,money) VALUES (?,?,?)");
                        prepare.setString(1, "222");
                        prepare.setString(2, "bbb");
                        prepare.setInt(3, 10000);
                        prepare.executeUpdate();
                    }
                    {
                        // 更新
                        PreparedStatement prepare = conn.
                                prepareStatement("UPDATE account SET money= money+1 where user=?");
                        prepare.setString(1, "asdflkjaf");
                        //让程序执行到此处报错，演示事务回滚
                        int i=1/0;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }catch (Exception e) {
                    if (savePoint != null) {
                        System.out.println("更新失败,回滚到保存点提交事务");
                        transactionStatus.rollbackToSavepoint(savePoint);
                    } else {
                        System.out.println("更新失败");
                        transactionStatus.setRollbackOnly();
                    }
                }
                return null;
            }
        });
    }
}
