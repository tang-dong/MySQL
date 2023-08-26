# MySQL
## 1.库的基本操作
### 1.1 创建库
```sql
create database 数据库名;

#创建atguigudb数据库，该名称不能与已经存在的数据库重名。
create database atguigudb;
```
### 1.2 使用库
```sql
use 数据库名;
#使用atguigudb数据库
use atguigudb;
```
>说明：如果没有使用use语句，后面针对数据库的操作也没有加“数据名”的限定，那么会报“ERROR 1046
(3D000): No database selected”（没有选择数据库）
> 
>使用完use语句之后，如果接下来的SQL都是针对一个数据库操作的，那就不用重复use了，如果要针对另
一个数据库操作，那么要重新use。
### 1.3 删除库
```sql
drop databases [if exists] 库名;
```
### 1.4 查看某个库的所有表格
```sql
show tables from 数据库名;
```

## 2.表管理
### 2.1 创建表
```sql
create table 表名称(
       字段名 数据类型,
       字段名 数据类型
);
```
> 说明：如果是最后一个字段，后面就用加逗号，因为逗号的作用是分割每个字段。
```sql
#创建学生表
CREATE TABLE student (
    `id` INT,
    `name` VARCHAR(20) -- 说名字最长不超过20个字符
);
```
### 2.2 查看一个表的数据
```sql
select * from 数据库表名称;

--查看学生表的数据
select * from student;
```
### 2.3 添加一条记录
```sql
insert into 表名称 values(值列表);

--添加两条记录到student表中
insert into student values(1,'张三');
insert into student values(2,'李四');
```
### 2.4 查看表的创建信息
```sql
show create table 表名称

--查看student表的详细创建信息
show create table student
```
```sql
CREATE TABLE `student` (
  `id` int DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
```

### 2.5 删除表格
```sql
drop table 表名称;

--删除学生表
drop table student;
```
## 3.基本的 SELECT 语句
### 3.1 SELECT ... FROM
```sql
SELECT 标识选择哪些列
FROM 标识从哪个表中选择
```
```sql
SELECT *
FROM departments;
```
> 一般情况下，除非需要使用表中所有的字段数据，最好不要使用通配符‘*’。使用通配符虽然可以节省输入查询语句的时间，但是获取不需要的列数据通常会降低查询和所使用的应用程序的效率。通配符的优势是，当不知道所需要的列的名称时，可以通过它获取它们。在生产环境下，不推荐你直接使用 SELECT * 进行查询。

### 3.2 选定特定的列
```sql
SELECT department_id, location_id
FROM departments;
```
![img.png](img.png)

### 3.3 列的别名
![img_1.png](img_1.png)
![img_2.png](img_2.png)

### 3.4 去除重复行
默认情况下，查询会返回全部行，包括重复行
![img_3.png](img_3.png)
![img_4.png](img_4.png)

### 3.5 空值参与运算
![img_5.png](img_5.png)

### 3.6 着重号
我们需要保证表中的字段、表名等没有和保留字、数据库系统或常用方法冲突。如果真的相同，请在
SQL语句中使用一对``（着重号）引起来。

## 4.显示表结构
使用DESCRIBE 或 DESC 命令，表示表结构。
```sql
DESCRIBE employees;
或
DESC employees;
```
![img_6.png](img_6.png)
![img_7.png](img_7.png)

![img_8.png](img_8.png)
![img_9.png](img_9.png)
![img_10.png](img_10.png)

## 5.算术运算符
![img_11.png](img_11.png)
### 5.1 比较运算符
比较运算符用来对表达式左边的操作数和右边的操作数进行比较，比较的结果为真则返回1，比较的结果
为假则返回0，其他情况则返回NULL。
比较运算符经常被用来作为SELECT查询语句的条件来使用，返回符合条件的结果记录。

![img_12.png](img_12.png)
![img_13.png](img_13.png)
![img_14.png](img_14.png)
![img_15.png](img_15.png)
![img_16.png](img_16.png)
![img_17.png](img_17.png)
![img_18.png](img_18.png)
![img_19.png](img_19.png)

## 6.逻辑运算符
![img_20.png](img_20.png)
![img_21.png](img_21.png)

## 7.排序与分页
### 1.排序
#### 1.1排序数据
![img_23.png](img_23.png)
#### 1.2单列排序
```sql
select last_name,job_id,department_id,hire_date
from employees
order by hire_date;
```
![img_22.png](img_22.png)
```sql
select last_name,job_id,department_id,hire_date
from employees
order by hire_date desc;
```
![img_24.png](img_24.png)
#### 1.3多列排序
```sql
SELECT last_name,department_id,salary
FROM employees
ORDER BY department_id,salary DESC;
```
![img_25.png](img_25.png)
![img_26.png](img_26.png)

### 2.分页
#### 2.1背景
![img_27.png](img_27.png)
#### 2.2 实现规则
![img_28.png](img_28.png)
```sql
-- 前10条记录
SELECT * FROM employees LIMIT 0,10;
-- 或者
SELECT * FROM employees LIMIT 10;
```
![img_29.png](img_29.png)
```sql
-- 第11至20条记录
SELECT * FROM employees LIMIT 10,10;
```
![img_30.png](img_30.png)
![img_30.png](img_30.png)

```sql
-- 第21至30条记录
SELECT * FROM employees LIMIT 20,10;
```
![img_31.png](img_31.png)
![img_32.png](img_32.png)
![img_33.png](img_33.png)

#### 2.3 扩展
![img_34.png](img_34.png)

![img_35.png](img_35.png)

## 8.多表查询
多表查询，也称为关联查询，指两个或更多个表一起完成查询操作。

前提条件：这些一起查询的表之间是有关系的（一对一、一对多），它们之间一定是有关联字段，这个关联字段可能建立了外键，也可能没有建立外键。比如：员工表和部门表，这两个表依靠“部门编号”进行关联。

### 1.一个案例引发的多表连接
#### 1.1 案例说明
![img_36.png](img_36.png)
从多个表中获取数据：
![img_37.png](img_37.png)
#### 1.2 笛卡尔积（交叉连接）的理解
![img_38.png](img_38.png)
![img_39.png](img_39.png)
![img_40.png](img_40.png)
```sql
-- 查询员工姓名和所在部门名称
SELECT last_name,department_name FROM employees,departments;
SELECT last_name,department_name FROM employees CROSS JOIN departments;
SELECT last_name,department_name FROM employees INNER JOIN departments;
SELECT last_name,department_name FROM employees JOIN departments;
```
#### 1.3 案例分析与问题解决
![img_41.png](img_41.png)
```sql
SELECT table1.column, table2.column
FROM table1, table2
WHERE table1.column1 = table2.column2; -- 连接条件
```
![img_42.png](img_42.png)
