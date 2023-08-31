package com.JackTang.api.statement;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author Jack Tang
 * @description 模拟用户登录
 * @create 2023-08-31 17:28
 *
 * TODO：
 *      1.明确jdbc的使用流程，和详细讲解内部设计api方法
 *      2.发现问题，引出prepareStatement
 *
 * TODO：
 *      1.输入账户和密码
 *      2.进行数据库信息查询
 *      3.反馈登录结果
 * TODO：
 *      1.键盘输入事件，收集账号和密码信息
 *      2.注册驱动
 *      3.获取连接
 *      4.创建statement
 *      5.发送查询SQL语句，并获取返回结果
 *      6.结果判断，显示登录成功还是失败
 *      7.关闭资源
 *
 */
public class StatementUserLoginPart {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //1.键盘输入事件，收集账号和密码信息
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入账号：");
        String account = scan.nextLine();
        System.out.println("请输入密码：");
        String password = scan.nextLine();
        
        //2.注册驱动
        /**
         * 方案1：
         *      DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver())
         *      注意： 8+ com.mysql.cj.jdbc.Driver
         *            5+ com.mysql.jdbc.Driver
         *      问题：注册两次驱动
         *           1.DriverManager.registerDriver() 方法本身会注册一次
         *           2.Driver.static{ DriverManager.registerDriver() } 静态代码块也会注册一次
         *      解决：只想注册一次驱动
         *              只触发静态代码块即可！ Driver
         *      触发静态代码块：
         *           类加载机制：类加载的时刻，会触发静态代码块！
         *           加载[class文件 -> jvm虚拟机的class对象]
         *           连接[验证(检查文件类型) -> 准备(静态变量默认值) -> 解析(触发静态代码块)]
         *           初始化（静态属性赋真实值）
         *      触发类加载：
         *          1.new 关键字
         *          2.调用静态方法
         *          3.调用静态属性
         *          4.接口 1.8 default默认实现
         *          5.反射
         *          6.子类触发父类
         *          7.程序的入口 main 方法
         */
        //方案1：
        //DriverManager.registerDriver(new Driver());

        //方案2：注册驱动 固定的写法！ 存在的问题，需要考虑mysql的新版调用，同时还需要考虑当其替换成了Oracle时也会有问题，还需要修改代码
        //new Driver();

        //方案3：字符串 -> 提取到外部的配置文件 -> 可以在不改变代码的情况下完成数据库驱动的切换！
        Class.forName("com.mysql.cj.jdbc.Driver");//利用反射，触发类加载从而触发其中的静态代码块调用

        /**
         * 重写： 为了子类扩展父类的方法！父类也间接的规范了子类方法的参数和返回！
         * 重载： 重载一般应用在第三方的工具类上，为了方便用户多种方式传递参数形式！简化形式！
         */
        //3.获取连接
        /**
         * getConnection(1,2,3)方法，是一个重载方法！
         * 允许开发者，用不同的形式传入数据库连接的核心参数
         *
         * 核心属性：
         *      1.数据库软件所在主机的IP地址：localhost | 127.0.0.1
         *      2.数据库软件所在的主机的端口号：3306
         *      3.连接的具体库：atguigu
         *      4.连接的账号
         *      5.连接的密码
         *      6.可选的信息  没有
         *三个参数：
         *      String URL: 数据库软件所在的信息，连接的具体库，以及其他可选信息！
         *                  语法： jdbc：数据库管理软件名称[mysql，oracle]://ip地址|主机名:port端口号/数据库名?key=value
         *                        &key=value 可选信息！
         *                  具体：jdbc:mysql://127.0.0.1:3306/atguigu
         *                       jdbc:mysql://localhost:3306/atguigu
         *                  本机的省略写法：如果你的数据库软件安装到本机，可以使用省略的写法
         *                       jdbc:mysql://localhost:3306/day01 = jdbc:mysql:///day01
         *
         *      String user: 连接数据库用户名
         *      String password: 连接数据库用户对应的密码
         *
         *
         * 两个参数：
         *     String URL : 写法还是jdbc的路径写法！
         *     Properties : 就是一个参数封装容器！至少要包含 user / password key!存储连接账号信息！
         *
         * 一个参数：
         *    String URL: URl可以携带目标地址，可以通过?分割，在后面key=value&key=value形式传递参数
         *                jdbc:mysql:///day01?user=root&password=123456
         * 扩展路径参数(了解):
         *    serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=true
         */
        //Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/atguigu", account, password);
        //①.三个参数的
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", account, password);
        //②.两个参数的
//        Properties info = new Properties();
//        info.put("user","root");
//        info.put("password","123456");
//        Connection connection1 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/atguigu", info);
        //③.一个参数
//        Connection connection2 = DriverManager.getConnection("jdbc:mysql:///atguigu?user=root&password=123456");

        //4.创建statement
        Statement statement = connection.createStatement();

        //5.SQL语句
        String sql = "SELECT *\n" +
                "FROM t_user\n" +
                "WHERE account = '" + account + "' AND `PASSWORD` = '" + password + "';";

        //6.发送SQL语句并执行返回结果
        /**
         * SQL分类：DDL（容器创建，修改，删除） DML（插入，修改，删除） DQL（查询） DCL（权限控制） TPL（事务控制语句）
         *
         * 参数： sql 非DQL
         * 返回： int
         *          情况1：DML 返回影响的行数，例如：删除了三条数据 return 3；插入了两条数据 return 2；修改了0条 return 0；
         *          情况2：非DML return 0；
         * int row = executeUpdate(sql)
         *
         * 参数 SQL DQL
         * 返回: resultSet 结果封装对象
         * ResultSet resultSet = executeQuery(sql);
         */
        ResultSet resultSet = statement.executeQuery(sql);

        //ResultSet == 小海豚  你必须有面向对象的思维：Java是面向对象编程的语言 OOP！
        /**
         *
         * TODO:1.需要理解ResultSet的数据结构和小海豚查询出来的是一样，需要在脑子里构建结果表！
         * TODO:2.有一个光标指向的操作数据行，默认指向第一行的上边！我们需要移动光标，指向行，再获取列即可！
         *        boolean = next()
         *              false: 没有数据，也不移动了！
         *              true:  有更多行，并且移动到下一行！
         *       推荐：推荐使用if 或者 while循环，嵌套next方法，循环和判断体内获取数据！
         *       if(next()){获取列的数据！} ||  while(next()){获取列的数据！}
         *
         *TODO：3.获取当前行列的数据！
         *         get类型(int columnIndex | String columnLabel)
         *        列名获取  //lable 如果没有别名，等于列名， 有别名label就是别名，他就是查询结果的标识！
         *        列的角标  //从左到右 从1开始！ 数据库全是从1开始！
         */

        //7.进行结果集对象解析
        if (resultSet.next()){
            int id = resultSet.getInt(1);
            String account1 = resultSet.getString(2);
            String passWorld = resultSet.getString(3);
            String name = resultSet.getString(4);
            System.out.println(account1 + "--" + passWorld + "--" + name + "--");
            //只要向下移动，就是有数据，就是登陆成功！
            System.out.println("登陆成功！");
        }else {
            System.out.println("登陆失败！");
        }

        //8.关闭资源
        resultSet.close();
        statement.close();
        connection.close();
    }

}