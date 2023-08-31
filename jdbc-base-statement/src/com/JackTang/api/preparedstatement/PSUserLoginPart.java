package com.JackTang.api.preparedstatement;

import java.sql.*;
import java.util.Scanner;

/**
 * @author Jack Tang
 * @description 使用预编译的statement完成用户信息
 * @create 2023-09-01 0:52
 *
 * TODO： 防止注入攻击 | 演示 ps 的使用流程
 *
 */
public class PSUserLoginPart {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1.键盘输入事件，收集账号和密码信息
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入账号：");
        String account = scan.nextLine();
        System.out.println("请输入密码：");
        String password = scan.nextLine();

        //2.ps数据库的流程
        //1.ps数据库的注册
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", account, password);

        /**
         * statement
         *      1.创建statement
         *      2.拼接SQL语句
         *      3.发送SQL语句，并且获取返回结果
         *
         * preparedstatement
         *      1.编写SQL语句结构   不包含动态值部分的语句，动态值部分使用占位符 ? 替代  注意： ？ 只能替代动态值
         *      2.创建preparedstatement，并且传入动态值
         *      3.动态值  占位符  赋值  ? 单独赋值即可
         *      4.发送SQL语句即可，并获取返回结果
         */
        //3.编写SQL语句机构
        String sql = "select * from t_user where account = ? and `PASSWORD` = ? ;";

        //4.创建 ps
        PreparedStatement ps = connection.prepareStatement(sql);

        //5.占位符单独赋值
        /**
         * 参数1 ： int   占位符的下标从左到右，从1开始！
         * 参数2： object 占位符的值 可以设置任何数值
         */
        ps.setObject(2,password);
        ps.setObject(1,account);

        //其内部完成了字符串的拼接！
        //6.直接执行SQL语句即可，发送SQL语句并获取返回结果
        ResultSet resultSet = ps.executeQuery();

        //7.进行结果集对象解析
        if (resultSet.next()){
            //只要向下移动，就是有数据 就是登录成功！
            System.out.println("登录成功！");
        }else{
            System.out.println("登录失败！");
        }

        //8.关闭资源
        resultSet.close();
        ps.close();
        connection.close();

    }
}