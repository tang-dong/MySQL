package com.JackTang.api.preparedstatement;

import org.junit.Test;

import java.sql.*;

/**
 * @author Jack Tang
 * @description 练习ps的特殊使用情况
 * @create 2023-09-03 13:05
 */
public class PSOtherPart {
    /**
     * TODO:
     *      t_user插入一条数据，并且获取数据库自增长的主键！
     *
     * TODO：使用总结
     *      1.创建preparedstatement的时候，告知，携带回数据自增长的主键 (sql, Statement.RETURN_GENERATED_KEYS)
     *      2.获取司机装主键值的结果集对象，一行一列，获取对应的数据即可 ResultSet resultSet = ps.getGeneratedKeys();
     */
    @Test
    public void returnPrimaryKey() throws Exception{
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql:///atguigu?user=root&PASSWORD=hyx520");
        //3.编写SQL语句
        String sql = "insert into t_user(account,PASSWORD,nickname) values(?,?,?)";
        //4.创建statement
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        //5.占位符赋值
        ps.setObject(1,"aaaa");
        ps.setObject(2,"123456");
        ps.setObject(3, "阿阳");
        //6.发送SQL语句，并且获取结果
        int rows = ps.executeUpdate();
        //7.结果解释
        if (rows > 0){
            System.out.println("数据插入成功！");

            //可以获取回显的主键
            //获取司机装主键的结果集对象， 一行  一列  id = 值
            ResultSet resultSet = ps.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);
            System.out.println("id = " + id);
        }else {
            System.out.println("插入失败！");
        }
        //8.关闭资源
        ps.close();
        conn.close();


    }


    /**
     *
     *  TODO：普通插入
     */
    @Test
    public void testInsert() throws Exception{
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql:///atguigu?user=root&PASSWORD=hyx520");
        //3.编写SQL语句
        String sql = "insert into t_user(account,PASSWORD,nickname) values(?,?,?)";
        //4.创建statement
        PreparedStatement ps = conn.prepareStatement(sql);
        //5.占位符赋值
        //开始时间
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            ps.setObject(1,"test"+i);
            ps.setObject(2,"123456");
            ps.setObject(3, "阿阳"+i);

            //6.发送SQL语句，并且获取结果
            int rows = ps.executeUpdate();
        }


        //结束时间
        long endTime = System.currentTimeMillis();

        System.out.println("消耗的时间:" + (endTime - startTime));

        //7.关闭资源
        ps.close();
        conn.close();


    }

    /**
     * TODO:
     *      + **addBatch(String)**： 将sql语句打包到一个Batch容器中,  添加需要批量处理的SQL语句或是参数；
     *      + **executeBatch()**：将Batch容器中的sql语句提交,  执行批量处理语句；
     *      + **clearBatch()**:清空Batch容器，为下一次打包做准备
     *
     *  TODO：批处理插入
     */
    @Test
    public void testInsert2() throws Exception{
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql:///atguigu?user=root&PASSWORD=hyx520");
        //3.编写SQL语句
        String sql = "insert into t_user(account,PASSWORD,nickname) values(?,?,?)";
        //4.创建statement
        PreparedStatement ps = conn.prepareStatement(sql);
        //5.占位符赋值
        //开始时间
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= 10000; i++) {
            ps.setObject(1,"test"+i);
            ps.setObject(2,"123456");
            ps.setObject(3, "阿阳"+i);

            //将SQL语句打包到一个容器中
            ps.addBatch();
            if (i % 500 == 0) {
                ps.executeBatch();//将容器中的SQL语句提交
                ps.clearBatch();//清空容器，为下一次打包做准备
            }
        }

        //为防止有sql语句漏提交【如i结束时%500！=0的情况】，需再次提交sql语句
        ps.executeBatch();
        ps.clearBatch();

        //结束时间
        long endTime = System.currentTimeMillis();

        System.out.println("消耗的时间:" + (endTime - startTime));

        //7.关闭资源
        ps.close();
        conn.close();


    }

    /**
     * TODO:
     *      批量插入优化：
     *              1.url? rewriteBatchedStatements=true
     *              2.insert 语句不能添加分号
     *              3.语句后面不能直接执行，每次需要装货 addBatch() 最后 executeBatch();
     *
     *  TODO：普通插入
     */
    @Test
    public void testInsert3() throws Exception{
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql:///atguigu?&rewriteBatchedStatements=true","root","hyx520");
        //3.编写SQL语句
        String sql = "insert into t_user(account,PASSWORD,nickname) values(?,?,?)";
        //4.创建statement
        PreparedStatement ps = conn.prepareStatement(sql);
        //5.占位符赋值
        //开始时间
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= 10000; i++) {
            ps.setObject(1,"test"+i);
            ps.setObject(2,"123456");
            ps.setObject(3, "阿阳"+i);

            ps.addBatch();
            if (i % 500 == 0) {
                ps.executeBatch();
                ps.clearBatch();
            }
        }
        ps.executeBatch();
        ps.clearBatch();

        //结束时间
        long endTime = System.currentTimeMillis();

        System.out.println("消耗的时间:" + (endTime - startTime));

        //7.关闭资源
        ps.close();
        conn.close();


    }
}