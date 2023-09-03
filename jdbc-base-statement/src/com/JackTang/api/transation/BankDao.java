package com.JackTang.api.transation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Jack Tang
 * @description bank表的数据库操作方法存储类
 * @create 2023-09-03 15:00
 */
public class BankDao {

    /**
     * 加钱的数据库操作方法
     * @param account 加钱的账号
     * @param money 金额
     */
    public void add(String account, int money, Connection conn) throws Exception {

        //3.编写SQL语句结果
        String sql = "update t_bank set money = money + ? where account = ?";

        //4.创建Statement
        PreparedStatement ps = conn.prepareStatement(sql);

        //5.封装
        ps.setObject(1, money);
        ps.setObject(2,account);

        //6.发送SQL语句
        ps.executeUpdate();

        //7.关闭资源
        ps.close();
        System.out.println("转账成功！");
    }

    /**
     * 减钱的数据库操作方法
     * @param account 减钱的账号
     * @param money 金额
     */
    public void sub(String account, int money, Connection conn) throws Exception{

        //3.编写SQL语句结果
        String sql = "update t_bank set money = money - ? where account = ?";

        //4.创建Statement
        PreparedStatement ps = conn.prepareStatement(sql);

        //5.封装
        ps.setObject(1, money);
        ps.setObject(2,account);

        //6.发送SQL语句
        ps.executeUpdate();

        //7.关闭资源
        ps.close();
        System.out.println("转账成功！");
    }

}