package com.JackTang.api.transation;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Jack Tang
 * @description 银行卡业务方法，调用 dao 方法
 * @create 2023-09-03 15:05
 */
public class BankService {


    /**
     * TODO:
     *      事务添加是在业务方法中！
     *      利用 try catch 代码块，开启事务和提交事务，和事务回滚！
     *      将 connection 传入 dao 层即可！ dao 只负责使用，不要 close();
     * @param addAccount
     * @param subAccount
     * @param money
     * @throws Exception
     */
    public void transfer(String addAccount, String subAccount, int money) throws Exception {
        BankDao bankDao = new BankDao();

        //一个事务最基本的要求，必须是同一个连接对象：  connection
        //一个转账的方法属于事务（加钱  减钱）

        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2.获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql:///atguigu?user=root&password=hyx520");

        try {
            //开启事务
            //关闭事务自动提交
            conn.setAutoCommit(false);

            //执行数据库动作
            bankDao.add(addAccount,money,conn);
            System.out.println("------------------");
            bankDao.sub(subAccount,money,conn);

            //事务提交
            conn.commit();

        }catch (Exception e){
            //事务回滚
            conn.rollback();
            //抛出
            throw e;
        }finally {
            conn.close();
        }



    }

    @Test
    public void startTest() throws Exception {

        //ddd给ergouzi转500
        transfer("ergouzi","ddd",500);
    }

}