package com.JackTang.api.statement;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;

/**
 * @author Jack Tang
 * @description 使用 statement 查询 t_user 表下，全部的数据
 * @create 2023-08-30 23:26
 */
public class StatementQueryPart {
    /**
     *
     * TODO：
     *      DriverManager
     *      Connection
     *      Statement
     *      ResultSet
     *
     * @param args
     */
    public static void main(String[] args) throws SQLException {
        //1.注册驱动
        /**
         * TODO:
         *      注册驱动
         *      依赖：驱动版本 8+ com.mysql.cj.jdbc.Driver
         *           驱动版本 5+ com.mysql.jdbc.Driver
         *
         */
        DriverManager.registerDriver(new Driver());
        //2.获取连接
        /**
         * TODO:
         *      java程序要和数据库创建连接！
         *      JAVA程序，连接数据库，肯定是调用某个方法，方法也需要填入连接数据库的基本信息：
         *              数据库 ip 地址 127.0.0.1
         *              数据库端口号 3306
         *              账号：root
         *              密码：root
         *              连接数据库的名称：atguigu
         */

        /**
         * 参数1 ： url
         *         jdbc:数据库厂商名://ip地址:port/数据库名
         *         jdbc:mysql://127.0.0.1:3306/atguigu
         * 参数2 ： username 数据库软件的账号
         * 参数3 ： password 数据库软件的密码
         */
        // java.sql 接口 = 实现类
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/atguigu", "root", "hyx520");
        //3.创建statement
        Statement statement = connection.createStatement();
        //4.发送SQL语句，并获取返回结果
        String sql = "select * from t_user";
        ResultSet resultSet = statement.executeQuery(sql);
        //5.进行结果集分析
        //先看看有没有下一行数据，有，你就可以获取
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String account = resultSet.getString("account");
            String password = resultSet.getString("PASSWORD");
            String nickname = resultSet.getString("nickname");
            System.out.println(id + "---" + account + "---" + nickname);
        }
        //6.关闭资源
        resultSet.close();
        statement.close();
        connection.close();

    }
}