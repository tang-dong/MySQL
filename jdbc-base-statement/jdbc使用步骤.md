# JDBC 使用的步骤
![img_1.png](img_1.png)
## 1.使用Statement
```java
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
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/atguigu", "root", "123456");
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
```

## 2.使用PreparedStatement
![img_2.png](img_2.png)
```java
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
```

