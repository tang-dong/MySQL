package com.JackTang.api.preparedstatement;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jack Tang
 * @description 使用preparedstatement进行t_user表的curd动作
 * @create 2023-09-01 1:22
 */
public class PSCURDPart {

    /**
     * 插入一条用户数据!
     * 账号: test
     * 密码: test
     * 昵称: 测试
     */
    @Test
    public void testInsert() throws ClassNotFoundException, SQLException {

        //1.注册
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2.获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql:///atguigu?user=root&PASSWORD=hyx520");

        //3.编写SQL语句  TODO: ? 只能代替 值！！！ 不能代替关键字  特殊符号  容器名
        String sql = "insert into t_user(account,password,nickname) value (?,?,?);";

        //4.创建ps
        PreparedStatement ps = conn.prepareStatement(sql);

        //5.封装数据
        ps.setString(1,"test");
        ps.setString(2, "test");
        ps.setString(3, "测试");

        //6.执行SQL语句
        int rows = ps.executeUpdate();
        //输出结果
        System.out.println(rows);
        
        //7.关闭资源
        ps.close();
        conn.close();

    }


    /**
     * 修改一条用户数据!
     * 修改账号: test的用户,将nickname改为tomcat
     */
    @Test
    public void testUpdate() throws ClassNotFoundException, SQLException {

        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2.获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql:///atguigu?user=root&PASSWORD=hyx520");

        //3.编写SQL语句
        String sql = "update t_user set nickname = ? where account = ? ;";

        //4.创建ps
        PreparedStatement ps = conn.prepareStatement(sql);

        //5.封装数据
        ps.setString(1, "tomcat");
        ps.setString(2, "test");

        //6.执行SQL语句
        int rows = ps.executeUpdate();
        System.out.println(rows);
        //7.关闭资源
        ps.close();
        conn.close();

    }


    /**
     * 删除一条用户数据!
     * 根据账号: test
     */
    @Test
    public void testDelete() throws ClassNotFoundException, SQLException {

        //注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection conn = DriverManager.getConnection("jdbc:mysql:///atguigu?user=root&PASSWORD=hyx520");

        String sql = "delete from t_user where account = ? ;";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, "test");

        int rows = ps.executeUpdate();

        System.out.println(rows);

        ps.close();
        conn.close();

    }


    /**
     * 查询全部数据!
     *   将数据存到List<Map>中
     *   map -> 对应一行数据
     *      map key -> 数据库列名或者别名
     *      map value -> 数据库列的值
     * TODO: 思路分析
     *    1.先创建一个List<Map>集合
     *    2.遍历resultSet对象的行数据
     *    3.将每一行数据存储到一个map对象中!
     *    4.将对象存到List<Map>中
     *    5.最终返回
     *
     * TODO:
     *    初体验,结果存储!
     *    学习获取结果表头信息(列名和数量等信息)
     */
    @Test
    public void testQueryMap() throws Exception {

        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2.获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql:///atguigu?user=root&PASSWORD=hyx520");

        //3.编写SQL
        String sql = "select id,account,PASSWORD,nickname from t_user;";

        //4.创建ps
        PreparedStatement ps = conn.prepareStatement(sql);

        //5.获取执行后返回的结果
        ResultSet resultSet = ps.executeQuery();

        //创建一个集合
        List<Map> mapList = new ArrayList<>();

        //获取列表信息，包括列名，列的类型等
        ResultSetMetaData metaData = resultSet.getMetaData();
        //获取了结果集 metaData 的列数
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()) {
            Map map =new HashMap();
            // 注意： 要从 1 开始，并且小于等于总列数
            for (int i = 1; i <= columnCount; i++) {
                // getColumnLabel()获取列的标签，getObject()获取列的值
                // 其中getColumnLabel()会获取别名，如果没有别名才会获取列的名称
                // 不要使用 getColumnName() ：只会获取列的名称
                map.put(metaData.getColumnLabel(i),resultSet.getObject(i));
            }
            mapList.add(map);
        }

        System.out.println(mapList);

        //关闭资源
        ps.close();
        conn.close();
        resultSet.close();

    }

}