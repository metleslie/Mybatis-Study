

# Mybatis

## 1.简介

环境：

- JDK 1.8
- Mysql 5.7
- maven 3.6.1
- IDEA

回顾：

- JDBC
- Mysql
- Java基础
- Maven
- Junit

### 1.1什么是 MyBatis？

- MyBatis 是一款优秀的**持久层框架**
- 它支持定制化 SQL、存储过程以及高级映射。
- MyBatis **避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集**。
- MyBatis 可以**使用简单的 XML 或注解**来配置和映射原生类型、接口和 Java 的 POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

如何获得MyBatis？

```xml
Maven仓库

<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.3</version>
</dependency>

```

  github：https://github.com/mybatis/mybatis-3/releases

  中文文档：https://mybatis.org/mybatis-3/zh/index.html

### 1.2 持久化

数据持久化

- 持久化就是将程序的数据在持久状态和瞬时状态转化的过程
- 内存：**断电即失**
- **数据库(JDBC),io文件**持久化。这两种方式持久化
- 生活：冷藏、罐头。

为什么需要持久化？

- **有一些对象或者数据不能让他丢掉！！！**

- 内存太贵了

### 1.3 持久层

Dao层、Service层、Controller层…

- 完成持久化工作的代码块
- 层**界限十分明显**

### 1.4 为什么需要Mybatis

- 帮助程序员将数据存入到数据库中。

- 方便

- 传统的JDBC代码太复杂了。简化、框架、自动化。

- 不用Mybatis也可以。它更容易上手。**技术没有高低之分**

优点：

- 简单易学：本身就很小且简单。没有任何第三方依赖，最简单安装**只要两个jar文件+配置几个sql映射文件**易于学习，易于使用，通过文档和源代码，可以比较完全的掌握它的设计思路和实现。
- 灵活
- sql和代码的分离，提高了可维护性。
- 提供映射标签，支持对象与数据库的orm字段关系映射    提供对象关系映射标签，支持对象关系组建维护
- 提供xml标签，支持编写动态sql。

最重要的一点：使用的人多！

Spring SpringMVC SpringBoot



## 2.第一个Mybatis程序

思路：搭建环境–>导入Mybatis–>编写代码–>测试！

### 1.写数据库

```sql
CREATE DATABASE mybatis;
use mybatis;
CREATE TABLE user(
id INT(20) not null PRIMARY KEY,
name VARCHAR(30) DEFAULT NULL,
pwd VARCHAR(30) DEFAULT NULL
)ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO user (id,name,pwd) VALUES
(1,‘王五’,‘123456’),
(2,‘张三’,‘123456’),
(3,‘李四’,‘123890’)
```

### 2.新建项目

建一个maven项目

删除src

导入maven依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
<!--父工程-->
    <groupId>org.example</groupId>
    <artifactId>Mybatis-Study</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>
    
    <modules>
        <module>mybatis-01</module>
    </modules>

    <dependencies>
        <!--mysql驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.21</version>
        </dependency>
        <!--mybatis驱动-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.6</version>
        </dependency>
        <!--junit驱动-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>

        </dependency>
    </dependencies>

</project>
```



### 3.再创建一个模块

编写核心配置文件 在resoureces文件下创建mybatis-config.xml文件

```xml
编写mybatis的核心配置文件 相当于jdbc的连接，只不过实在xml下

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--configuration核心配置文件-->
<configuration>
    <!--environments配置环境组-->
    <!--default默认环境-->
    <environments default="development">
        <!--environment单个环境-->
        <environment id="development">
            <!--transactionManager配置事务管理器-->
            <transactionManager type="JDBC"/>
            <!--配置连接池-->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                //这个需要看mysql版本如果是5版本就用上述的
                //如果是8版本，就需要com.mysql.cj.jdbc.Driver
                //且配置信息里面加上时区serverTimezone=UTC
                <property name="url" value="jdbc:mysql://localhostF:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>
    </environments>

</configuration>
```

### 4.写utils获取连接

```java
package com.rui.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

//sqlSessionFactory—>SessionFactory
public class MyBatisUtils {
private static SqlSessionFactory sqlSessionFactory;
static {
try{
//使用mybatis第一步、获取sqlSessionFactory对象
String resource = “mybatis-config.xml”;
InputStream inputStream = Resources.getResourceAsStream(resource);
sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
}catch(IOException e) {
e.printStackTrace();
}
     public static SqlSession getSqlSession()
    {
     return  sqlSessionFactory.openSession();
    }
}
```

### 5.编写实体类，dao层

```java
package com.wang.pojo;

public class User {
    private int id;
    private String name;
    private String pwd;

    public User() {
    }

    public User(int id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}

```



接口

```java
package com.wang.dao;

import com.wang.pojo.User;

import java.util.List;

public interface UserDao {
    List<User>getUserList();
}

```

mapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace绑定一个对应的Mapper接口-->
<mapper namespace="com.wang.mapper.UserMapper">
    <!--select查询语句-->
    <select id="getUserList" resultType="com.wang.entity.User">
        select * from mybatis.user
    </select>

    <select id="getUserById" parameterType="int" resultType="com.wang.entity.User">
        select * from mybatis.user where id = #{id}
    </select>
</mapper>
```

在pom.xml里面加上build

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>Mybatis-Study</artifactId>
        <groupId>org.example</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>mybatis-01</artifactId>
    <!--<build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>-->
    /*因为maven约定大于配置，一定要加上build，否则会报错*/
    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>



</project>
```

### 6.测试

注意点：org.apache.ibatis.binding.BindingException: Type interface com.wang.dao.UserMapper is not known to the MapperRegistry.

![image-20200922201234752](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20200922201234752.png)

```java
package com.wang.dao;

import com.wang.pojo.User;
import com.wang.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {
    @Test
    public void test()
    {
        SqlSession sqlsession = MybatisUtils.getSqlSession();

        UserDao mapper = sqlsession.getMapper(UserDao.class);

        List<User> userList = mapper.getUserList();

        for (User user:userList)
        {
            System.out.println(user);
        }
        sqlsession.close();
    }

}

```

### 7.报错情况

```xml
<mappers>
    <mapper resource="com/wang/dao/UserMapper.xml"/>
</mappers>
```

不加这句话会报错



不在子项目里添加build会报错



如果mysql用的是8以上的版本

driver=com.mysql.cj.jdbc.Driver



还有种报错情况

```xml
serverTimezone=UTC
```

要添加mysql的时区！



org.apache.ibatis.exceptions.PersistenceException:  ### Error updating database.  Cause: org.apache.

这种错误是字段名与数据库里面的字段名不一致导致的错误



注意：

```xml
<property name="driver" value="com.mysql.cj.jdbc.Driver"/>
//这个要加cj
<property name="url" value="jdbc:mysql://localhost/mybatis?userSSL=false&amp;
userUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=UTC"/>
//且时区要配置好
```

注意：

配置文件里面不要加注释，会报错

## 3.CRUD

**namespace中的包名要和Dao/mapper接口名一致**

2.select

选择，查询语句

- id：就是duiyingnamespace中的方法名
- resultType：SQL语句的返回值！paramatertype:参数分类型 



### 1.mapper



```xml
	<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.wang.dao.UserDao">


    <select id="getUserList" resultType="com.wang.pojo.User">
        select * from mybatis.user
    </select>

    <select id="getUserById" parameterType="int" resultType="com.wang.pojo.User">
        select * from mybatis.user where id=#{id};
    </select>

    <insert id="addUser" parameterType="com.wang.pojo.User">
        insert mybatis.user (id,name,pwd) values (#{id},#{name},#{pwd});
    </insert>
    <update id="changeUser" parameterType="com.wang.pojo.User">
        update mybatis.user set name=#{name},pwd=#{pwd} where id = #{id};
    </update>
    <delete id="deleteUser" parameterType="int">
        delete  from  mybatis.user where id = #{id};
    </delete>
</mapper>
```



### 2.接口



```java
package com.wang.dao;

import com.wang.pojo.User;

import java.util.List;

public interface UserDao {
    //查询全部用户
    List<User>getUserList();//List就是类型，就像平时的String、int那
                            // 而<User>是叫泛型，这里就代表定义一个集合存储User实体对象
    //根据id查询
    User getUserById(int id);
    //添加一个用户
    int addUser(User user);
    //修改用户
    int changeUser(User user);

    //删除用户
    int deleteUser(int id);
}


```

### 3.测试



```java

package com.wang.dao;

import com.wang.pojo.User;
import com.wang.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {
    @Test
    public void test() //该方法可以不用main方法调用就可以测试出运行结果，是一种测试方法
                        //一般函数都需要有main方法调用才能执行，注意被测试的方法必须是public修饰的
    {
        SqlSession sqlsession = MybatisUtils.getSqlSession();

        UserDao mapper = sqlsession.getMapper(UserDao.class);

        List<User> users = mapper.getUserList();

        for (User user:users)
        {
            System.out.println(user);
        }
        sqlsession.close();
    }
    @Test
    public void getUserById()
    {
        SqlSession sqlsession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlsession.getMapper(UserDao.class);
        User userById = mapper.getUserById(1);
        System.out.println(userById);
        sqlsession.close();

    }
    @Test
    public void addUser()
    {
        SqlSession sqlsession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlsession.getMapper(UserDao.class);
        int re=mapper.addUser(new User(5,"欢听","800820"));
        if(re>0)
        {
            System.out.println("插入成功！");
        }
        //提交事务
        sqlsession.commit();
        sqlsession.close();
    }
    @Test
    public void changeUser()
    {
        SqlSession sqlsession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlsession.getMapper(UserDao.class);
        mapper.changeUser(new User(1,"你好","123256"));

        sqlsession.commit();
        sqlsession.close();

    }
    @Test
    public void deleteUser()
    {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        mapper.deleteUser(5);
        sqlSession.commit();
        sqlSession.close();
    }

}

```



### 4.分析错误

- 标签不要匹配错误 
- resource绑定mapper，需要使用路径！ 
- 程序配置文件必须符合规范 
- NullPointerException，没有注册到资源 
- 输出的xml文件中存在中文乱码问题 
- maven资源没有导出问题

注意点：

**增删改需要提交事务！**

##  4.优化CRUD操作

### 1.万能Map

假设我们的实体类，或者数据库中的表，字段或者参数过多，我们应当考虑使用Map!

- ```java
     /**
       * 添加多个
       * @param map
       * @return
       */
      public int addUser2(Map<String,Object> map);
        
  ```

  

- ```java
      <insert id="addUser2" parameterType="map">
          insert into mybatis.user (id,name ,pwd) values (#{userid},#{username},#{userpwd})
      </insert>
  ```

- ```java
         map.put("userid","8");
          map.put("username","王老六");
          map.put("userpwd","888888");
       
          mapper.addUser2(map);
        
  ```

  **这样可以达到参数灵活使用**
  
  Map传递参数，直接在sql中取出key即可！【parameterType="map"】
  
  对象传递参数，直接在sql中取出对象的属性即可！【parameterType="com.zhang.pojo.User"】
  
  只有一个基本类型参数情况下，可以直接在sql中取到！【parameterType="int"】
  
  **多个参数用Map或者注解**

### 2 模糊查询

模糊查询怎么写？

1，java代码执行时，传递通配符% %

```java
List<User> list = mapper.getUserLike("%李%");

```

2.在sql拼接中使用通配符！

```java
select * from mybatis.user where name like "%"#{value}"%"
```

## 5.配置解析

### 1.核心配置文件

- mybatis-config.xml

- MyBatis配置包含对MyBatis的行为产生巨大影响的设置和属性。该文档的高级结构如下：

```xml
configuration（配置）
properties（属性）
settings（设置）
typeAliases（类型别名）
typeHandlers（类型处理器）
objectFactory（对象工厂）
plugins（插件）
environments（环境配置）
environment（环境变量）
transactionManager（事务管理器）
dataSource（数据源）
databaseIdProvider（数据库厂商标识）
mappers（映射器）
```



### 2.环境配置（environments）

mybatis可以配置成适应多种环境

**不过要记住，尽管可以配置多种环境，但每个sqlsessionFactory实例只能选择一种环境**

学会使用配置多套运行环境

**默认事务管理器jdbc，默认连接池：pooled**

### 3.属性（properties）



通过peoperties属性来实现引用配置文件

这些属性可以在外部进行配置，并可以进行动态替换。你既可以在典型的 Java 属性文件中配置这些属性，也可以在 properties 元素的子元素中设置。例如：

```xml
driver = com.mysql.cj.jdbc.Driver
url = jdbc:mysql://localhost:3306/mybatis?userSSL=false&userUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
username = root
password = 123456
```

在xml中，所有的标签都可以规定其顺序

![image-20200923125419507](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20200923125419507.png)

设置好的属性可以在整个配置文件中用来替换需要动态配置的属性值。比如:

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--引入外部配置文件-->
    <properties resource="db.properties"/>
    <!--default 更改默认环境-->
    <environments default="development">
        <environment id="development">
            <!--事务管理器-->
            <transactionManager type="JDBC"/>
            <!--数据源-->
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>

        <environment id="test">
            <!--默认事务管理器jdbc-->
            <transactionManager type="JDBC"/>
            <!--默认的pooled，可以让连接更具哎-->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost/mybatis?userSSL=false&amp;
                userUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=UTC"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <mapper resource="com/wang/dao/UserMapper.xml"/>
    </mappers>

</configuration>
```

- 可以直接引入外部文件

- 可以增加一些属性

  - 配置文件写一半

    ```xml
    driver=com.mysql.jdbc.Driver
    url=jdbc:mysql://localhost/mybatis?useSSL=false&userUnicode=true&characterEncoding=UTF-8
    ```

  - 配置里面写一半

    ```xml
    <properties resource="db.properties">
        <property name="username" value="root"/>
        <property name="password" value="123456"/>
    </properties>
    ```

    同样可以运行

- 如果两个文件有同一个引入文件**优先使用外部文件再使用内部配置的**

### 4.类型别名（Alias）

类型别名是为java类型**设置一个短的名字**

存在的意义仅在于用来减少类**完全限定名的冗余**

放在<properties>下

```xml
<!--可以给实体类改别名-->
<typeAliases>
    <typeAlias type="com.wang.pojo.User" alias="User"/>
</typeAliases>
```

也可以指定一个报名，**扫描实体类的包，它的默认别名就是这个类的类名，首字母小写**

就是用包取别名

实体类比较少使用第一种方式

如果实体类比较多，建议使用第二种

第一种可以DIY，第二种不可以,如果非要改，需要在实体类上增加注解

就是说用注解配置别名的话，一定要用第二种才可以用

![image-20200923132352150](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20200923132352150.png)

![image-20200923132413301](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20200923132413301.png)

![image-20200923132422444](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20200923132422444.png)

在实体类增加注解



### 5.设置（Setting）

这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为。 下表描述了设置中各项设置的含义、默认值等。

![image-20200923132834689](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20200923132834689.png)

![image-20200923132850661](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20200923132850661.png)



### 6.映射器（Mapper）

配置mapper

![image-20201211212533468](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201211212533468.png)

既然 MyBatis 的行为已经由上述元素配置完了，我们现在就要来定义 SQL 映射语句了。 但首先，我们需要告诉 MyBatis 到哪里去找到这些语句。 在自动查找资源方面，Java 并没有提供一个很好的解决方案，所以最好的办法是直接告诉 MyBatis 到哪里去找映射文件。 你可以使用相对于类路径的资源引用，或完全限定资源定位符（包括 `file:///` 形式的 URL），或类名和包名等。例如：

```xml
<!-- 使用相对于类路径的资源引用 -->
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>
<!-- 使用完全限定资源定位符（URL） -->
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
  <mapper url="file:///var/mappers/BlogMapper.xml"/>
  <mapper url="file:///var/mappers/PostMapper.xml"/>
</mappers>
<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>
<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```

这些配置会告诉 MyBatis 去哪里找映射文件，剩下的细节就应该是每个 SQL 映射文件了，也就是接下来我们要讨论的

方式一 ：相对路径配置【推荐使用】

```xml
 <mappers>
        <mapper resource="com/wang/mapper/mapper.xml"/>
    </mappers>
```

方式二：使用class文件绑定注册

```xml
<mappers>
        <mapper resource="com.wang.mapper.UserMapper"/>
    </mappers>
```

- 注意点：方法二**接口和配置文件同名且在一个包下**
- ![image-20201211213226210](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201211213226210.png)

方式三：使用扫描包进行注入绑定

```xml
<mappers>
        <package name="com.wang.mapper"/>
    </mappers>
```

- 注意点和方式二相同

### 7.生命周期和作用域

生命周期和作用域类别是至关重要的，因为错误的使用会导致非常严重的并发问题。

![image-20200923140037451](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20200923140037451.png)

#### SqlSessionFactoryBuilder

这个类可以被实例化、使用和丢弃。

- **一旦创建了 SqlSessionFactory，就不再需要它了**

- 局部变量

#### SqlSessionFactory

- 可以想象成一个数据库连接池

- SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，**没有任何理由丢弃它或重新创建另一个实例。**

- 多次重建会导致内存资源浪费

- 因此 SqlSessionFactory 的最佳作用域是应用作用域。 

- 最简单的就是使用**单例模式**或者静态单例模式。--保证全局只有一份变量

#### SqlSession

- 连接到连接池的一个请求！

- SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是**请求或方法作用域**。

- 用完之后需要关闭，否则资源被占用！

- 请求之后需要结束请求，否则资源被占用

**如果你现在正在使用一种 Web 框架，考虑将 SqlSession 放在一个和 HTTP 请求相似的作用域中。 换句话说，每次收到 HTTP 请求，就可以打开一个 SqlSession，返回一个响应后，就关闭它。** **这个关闭操作很重要**，**为了确保每次都能执行关闭操作，你应该把这个关闭操作放到 finally 块中**。

![-](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20200923140727471.png)

这里面的每一个mapper就代表一个具体的业务！



## 6.解决属性名和字段名不一致的问题

数据库中的字段

![image-20201212000627791](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201212000627791.png)

![image-20200923142910609](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20200923142910609.png)

```java
 		//select * from mybatis.user where id = #{id};
        //类型处理器
        //select id,name,pwd mybatis.user where id = #{id};
```

解决办法

- 起别名

  ```xml
  <select id="getUserById" resultType="Hello">
          select id,name,pwd as password from mybatis.user where id = #{id};
      </select>
  ```



### 2.结果集映射

```
id	name	pwd

id	name	password
```



![image-20201212003624467](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201212003624467.png)

- `resultMap` 元素是 MyBatis 中最重要最强大的元素。
- ResultMap 的设计思想是，对简单的语句做到零配置，对于复杂一点的语句，只需要描述语句之间的关系就行了。
- `ResultMap` 的优秀之处——你完全可以不用显式地配置它们。
- 如果这个世界总是这么简单就好了



## 7.日志

### 7.1 日志工厂

如果一个数据库中操作，出现了异常，我们需要排错。日志就是最好的助手

曾经：sout，debug

现在用日志工厂！



- SLF4J
- LOG4J 【掌握】
- LOG4J2
- JDK_LOGGING
- COMMONS_LOGGING
- STDOUT_LOGGING 【掌握】
- NO_LOGGING

在Mybatis中具体使用哪个日志实现，在设置中设定

```xml
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
```



![image-20201222192657515](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201222192657515.png)

### 7.2 log4j

什么是Log4j？

- Log4j是[Apache](https://baike.baidu.com/item/Apache/8512995)的一个开源项目，通过使用Log4j，我们可以控制日志信息输送的目的地是[控制台](https://baike.baidu.com/item/控制台/2438626)、文件、[GUI](https://baike.baidu.com/item/GUI)组件；
- 我们也可以控制每一条日志的输出格式；
- 通过定义每一条日志信息的级别，我们能够更加细致地控制日志的生成过程；
- 最令人感兴趣的就是，这些可以通过一个[配置文件](https://baike.baidu.com/item/配置文件/286550)来灵活地进行配置，而不需要修改应用的代码。



1.先导入log4j的包

```xml
 <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
```

2.log4j.properties

```properties
#将等级为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下面的代码
log4j.rootLogger=DEBUG,console,file
#控制台输出的相关设置
log4j.appender.console = org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.out
log4j.appender.console.Threshold=DEBUG
log4j.appender.console.layout = org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=[%c]-%m%n
#文件输出的相关设置
log4j.appender.file = org.apache.log4j.RollingFileAppender
log4j.appender.file.File=./log/rzp.log
log4j.appender.file.MaxFileSize=10mb
log4j.appender.file.Threshold=DEBUG
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=[%p][%d{yy-MM-dd}][%c]%m%n
#日志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG
log4j.logger.java.sq1.PreparedStatement=DEBUG
```

3.配置settings为log4j实现

```xml
<settings>
        <setting name="logImpl" value="LOG4J"/>
    </settings>
```

4.测试运行

![image-20201222205950164](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201222205950164.png)



### 简单实用

1，在要使用的类中，导入包import org.apache.log4j.Logger;

2.日志对象，参数为当前类的class

```java
static Logger logger = Logger.getLogger(MyTest.class);
```

3.日志级别

```java
logger.info("info:进入testLog4j");
logger.debug("debug:进入testLog4j");
logger.error("error:进入testLog4j");
		//常用的几个级别
```

   

## 8.分页

思考为什么要分页：减少数据的处理量

### 8.1 使用limit分页

sql语句

```sql
select * from limit startIndex,pageSize 
```

**startIndex是从哪里开始查询，0是第一个，pageSize是一页有几个，以前pageSize=-1时，是指查询到最后一个，但现在已经修复了。**

1.接口

```java
//分页查询
List<User> getUserLimit(Map<String,Integer> map);
```

2.mapper.xml

```xml
<select id="getUserLimit" parameterType="map" resultMap="UserMap">
    select * from user limit #{startIndex},#{pageSize}
</select>
```

3.测试

```java
@Test
public void Limit()
{
    SqlSession sqlSession = MybatisUntil.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    Map<String,Integer> map = new HashMap<String, Integer>();
    map.put("startIndex",2);
    map.put("pageSize",2);
    List<User> userList = mapper.getUserLimit(map);
    for (User user : userList) {
        System.out.println(user);
    }
    sqlSession.close();
}
```

### 8.2 RowBounds分页查询

不再使用sql实现分页

1.接口

```java
//分页查询2
List<User> getUserLimitByRowBounds();
```

2.xml

```xml
<select id="getUserLimitRowBounds" resultMap="UserMap">
    select * from user
</select>
```

3.代码实现

```java
@Test
public void RowBound()
{
    SqlSession sqlSession = MybatisUntil.getSqlSession();
    //RowBounds实现
    RowBounds rowBounds = new RowBounds(1, 2);
    //通过java代码层面实现分页
    List<User> userList = sqlSession.selectList("com.wang.mapper.UserMapper.getUserLimitRowBounds", null, rowBounds);
    for (User user : userList) {
        System.out.println(userList);
    }
    sqlSession.close();
}
```

### 8.3 分页插件



![在这里插入图片描述](https://img-blog.csdnimg.cn/20200623164958936.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0RERERlbmdf,size_16,color_FFFFFF,t_70)

了解即可，以防公司使用，里面有相关使用方法。



## 9.使用注解开发

### 9.1 面向接口编程





### 9.2 使用注解开发

1.接口编写，在接口上实现

```java
 @Select("select * from mybatis.user")
public List<User> getUserList();
```

2.在核心配置文件中绑定接口

```xml
<mappers>
    <mapper class="com.wang.mapper.UserMapper"/>
</mappers>
```

3.结果

![image-20201223155422391](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201223155422391.png)

本质：反射机制实现

底层：动态代理

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200623165014965.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0RERERlbmdf,size_16,color_FFFFFF,t_70)

Mybatis详细流程

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200623165030775.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0RERERlbmdf,size_16,color_FFFFFF,t_70#pic_center)

### 9.3 注解CRUD

```java
public static SqlSession getSqlSession()
{
        return sqlSession.openSession(true);
    //设置为true不用手动commit，事务提交！
}
```



1.编写接口增加注解。

```java
 //查询所有用户
    @Select("select * from mybatis.user")
    List<User> getUserList();

    //根据id查询用户
    //方法存在多个参数，所有的参数面前都需要加上@param注释
    @Select("select * from user where id = #{id}")
    User getUserById(@Param("id") int id);

    //增加用户
    @Insert("insert into user(id,name,pwd) values(#{id},#{name},#{pwd})")
    int addUser(User user);

    //修改用户
    @Update("update user set name = #{name},pwd = #{pwd} where id = #{id}")
    int update(User user);

    //删除用户
    @Delete("delete from user where id = #{iud}")
    int deleteUser(@Param("iud") int id);
```

测试类

```java
@Test
public void test1()
{
    SqlSession sqlSession = MybatisUntil.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    List<User> userList = mapper.getUserList();
    for (User user : userList) {
        System.out.println(userList);
    }
    sqlSession.close();
}
@Test
public void getUserById()
{
    SqlSession sqlSession = MybatisUntil.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    User userById = mapper.getUserById(2);
    System.out.println(userById);
    sqlSession.close();
}
@Test
public void addUser()
{
    SqlSession sqlSession = MybatisUntil.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    User user = new User(15, "伏特加", "12345600");
    mapper.addUser(user);
    sqlSession.close();
}
@Test
public void updateUser()
{
    SqlSession sqlSession = MybatisUntil.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    User user = new User(15,"威士忌","456789");
    mapper.update(user);
    sqlSession.close();
}
@Test
public void deleteUser()
{
    SqlSession sqlSession = MybatisUntil.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    mapper.deleteUser(1);
    sqlSession.close();
}
```

【注意：我们必须要将接口注册绑定到我们的核心配置文件中！】

**关于@Param（）注解**

- 基本类型的参数或者String类型，需要加上
- 引用类型不需要加
- 如果只有一个基本类型的话，可以忽略，但是建议大家都加上！
- 我们在sql中引用的就是我们@param中设定的属性名！

#{} ${}

#号可以防止sql注入；



## 10.lombok的使用

- java库
- 插件
- 构建工具
- 只需要在类上加注解就可以，不需要写getset方法等



使用步骤

​	1.在idea中导入lombok插件！

​	2.在项目中导入lombokjar包！

​	3.在实体类上加注解！

主要使用注解

@Data：get，set，无参构造，tostring，hashcode，equals；

```@AllArgsConstructor
@AllArgsConstructor
@Data：get，set，无参构造，tostring，hashcode，equals；
@NoArgsConstructor
```



## 11.多对一

测试环境搭建

- 导入lombok
- 新建实体类
- 建立mapper接口
- 建立xml文件
- 在核心配置文件中绑定注册我们的mapper接口或文件
- 测试查询是否成功

### 按照查询嵌套处理

```xml
<!--
    思路：
        1.查询所有的学生信息
        2.根据查询出来学生的tid，寻找对应的老师！  子查询

    -->
    <select id="getStudent" resultMap="StudentTeacher">
        select * from mybatis.student;
    </select>
    <resultMap id="StudentTeacher" type="Student">
        <result property="id" column="id"/>
        <result property="name" column="name"/>
        <!--复杂的属性我们需要单独处理，对象：association 集合:collection-->
        <association property="teacher" column="tid" javaType="teacher" select="getTeacher"/>
    </resultMap>
    <select id="getTeacher" resultType="teacher">
        select * from mybatis.teacher where id = #{id}
    </select><select id="getStudent" resultMap="StudentTeacher">
    select * from mybatis.student;
</select>
<resultMap id="StudentTeacher" type="Student">
    <result property="id" column="id"/>
    <result property="name" column="name"/>
    <!--复杂的属性我们需要单独处理，对象：association 集合:collection-->
    <association property="teacher" column="tid" javaType="teacher" select="getTeacher"/>
</resultMap>
<select id="getTeacher" resultType="teacher">
    select * from mybatis.teacher where id = #{id}
</select>
```

### 按照结果嵌套查询

```xml
<!--按照结果嵌套处理-->
<select id="getStudent2" resultMap="StudentTeacher2">
    select s.id sid,s.name sname,t.id tid
    from student s,teacher t
    where s.tid=t.id
</select>
<resultMap id="StudentTeacher2" type="Student">
    <result property="id" column="sid"/>
    <result property="name" column="sname"/>
    <association property="teacher" javaType="Teacher">
       <!-- <result property="name" column="tname"/>-->
        <result property="id" column="tid"/>
    </association>
</resultMap>
```

回顾mysql多对一查询方式：

- 子查询
- 联表查询



## 12 .一对多处理

比如：一个老师拥有多个学生

对于老师而言就是一对多的关系

### 按结果嵌套查询

```xml
<!--按结果嵌套查询-->
<select id="getTeacher" resultMap="StudentTeacher">
    select s.id sid, s.name sname,t.id tid,t.name tname
    from teacher t,student s
    where s.tid=t.id and t.id = #{tid}
</select>
<resultMap id="StudentTeacher" type="teacher">
    <result property="id" column="tid"/>
    <result property="name" column="tname"/>
    <!--javaType 指定属性的类型
       集合中的泛型信息，我们使用ofType获取-->
    <collection property="students" ofType="Student">
        <result property="id" column="sid"/>
        <result property="name" column="sname"/>
    </collection>
</resultMap>
```

### 按照查询嵌套处理（通过结果做映射）

```xml
<select id="getTeacher2" resultMap="StudentTeacher2">
    select id,name
    from teacher where id=#{tid}
</select>
<resultMap id="StudentTeacher2" type="Teacher">
    <result property="id" column="id"/>

    <collection property="students" javaType="ArrayList" ofType="Student" select="getStudentByTeacherId" column="id"/>
</resultMap>
<select id="getStudentByTeacherId" resultType="Student">
    select * from student where tid=#{id}
</select>
```



### 小结

1.关联 - association（多对一）

2.集合-collection（一对多）

3.javaType & ofType

- javaType用来指定实体类中属性的类型
- ofType用来指定映射到List或者集合中的entity类型，泛型中的约束类型！



注意点：

- 保证sql的可读性，尽量保证通俗易懂
- 注意一对多和多对一属性名和字段的问题
- 如果问题不好排查错误，可以使用日志，建议使用log4j



**慢SQL 	别人1s 	你1000s**

面试必问题

- mysql引擎
- InnoDB底层原理
- 索引
- 索引优化



## 13.动态SQL

什么是动态SQL

- **动态SQL就是指根据不同的条件生成不同的SQL语句**



如果你之前用过 JSTL 或任何基于类 XML 语言的文本处理器，你对动态 SQL 元素可能会感觉似曾相识。在 MyBatis 之前的版本中，需要花时间了解大量的元素。借助功能强大的基于 OGNL 的表达式，MyBatis 3 替换了之前的大部分元素，大大精简了元素种类，现在要学习的元素种类比原来的一半还要少。

- if
- choose (when, otherwise)
- trim (where, set)
- foreach

### 搭建环境

```sql
CREATE TABLE `blog`(
`id` VARCHAR(50) NOT NULL COMMENT '博客id',
`title` VARCHAR(100) NOT NULL COMMENT '博客标题',
`author` VARCHAR(30) NOT NULL COMMENT '博客作者',
`create_time` DATETIME NOT NULL COMMENT '创建时间',
`views` INT(30) NOT NULL COMMENT '浏览量'
)ENGINE=INNODB DEFAULT CHARSET=utf8
```

创建一个基础工程

1.导入包 （mysql，lombok，sql）

2.编写配置信息在xml里

3.在until里写获取连接方法

4.写实体类

5.编写实体类对应的接口和mapper.xml文件



![image-20201225160157702](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201225160157702.png)

java里属性名驼峰，Sql里是下划线自动匹配。

```xml
<setting name="mapUnderscoreToCamelCase " value="true"/>
```



插入数据

```xml
<insert id="insertBlog" parameterType="blog">
    insert into mybatis.blog (id,title,author,create_time,views)
    values (#{id},#{title},#{author},#{createTime},#{views});
</insert>
```

ps：select应该用resultType，结果值返回，parameterType这个是增删改的结果只返回



```java
@Test
public void insertBlog()
{
    SqlSession sqlSession = MybatisUntil.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    Blog blog = new Blog();
    blog.setAuthor("毛泽东");
    blog.setTitle("毛选");
    blog.setCreateTime(new Date());
    blog.setId(IDutils.getId());
    blog.setViews(1000000000);
    mapper.insertBlog(blog);
    sqlSession.close();
}
```



### 动态SQL 

#### if



```xml
<select id="queryBlog" parameterType="map" resultType="blog">
    select * from blog where 1=1
    <if test="title!=null">
        and title = #{title}
    </if>
    <if test="author!=null">
        and author = #{author}
    </if>
    <if test="views!=null">
        and views = #{views}
    </if>
</select>
```



```java
@Test
public void queryBlog()
{
    SqlSession sqlSession = MybatisUntil.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    HashMap map = new HashMap();
    map.put("title","沁园春.雪");
    map.put("author","教员");
    map.put("views",10);
    List<Blog> blogs = mapper.queryBlog(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }
    sqlSession.close();
}
```



![image-20201226143345013](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201226143345013.png)

#### where标签的作用

```xml
<select id="queryBlogChoose" parameterType="map" resultType="blog">
    select from blog where
    <if test="">
        title = #{title}
    </if>
    <if test="">
        and author = #{author}
    </if>
</select>
```

如上述mapper查找，如果不查找title，直接查找author，SQL语句就会变成 where后面跟上and

![image-20201226154059930](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201226154059930.png)

如果什么参数都不传入

![image-20201226154149453](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201226154149453.png)

所以出现where标签解决此问题。

**where元素只会在子元素返回任何内容的情况下才插入 “WHERE” 子句。而且，若子句的开头为 “AND” 或 “OR”，*where* 元素也会将它们去除。**

```xml
<select id="queryBlogChoose" parameterType="map" resultType="blog">
    select * from blog
    <where>
        <if test="title!=null">
            title = #{title}
        </if>
        <if test="author!=null">
            and author = #{author}
        </if>
    </where>
</select>
```

再次测试

![image-20201226154438112](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201226154438112.png)

**查询成功，如果传入if里面的标签会在sql语句后加上where，如果传入的参数为if的第二个，则会把and或者or给清除。**

#### choose

有时候，我们不想使用所有的条件，而只是想从多个条件中选择一个使用。针对这种情况，MyBatis 提供了 choose 元素，它有点像 Java 中的 switch 语句。

```xml
<select id="queryBlogChoose2" parameterType="map" resultType="blog">
    select * from blog
    <where>
       <choose>
           <when test="title!=null">
               title=#{title}
           </when>
           <when test="author!=null">
              and author=#{author}
           </when>
           <otherwise>
               and views = #{views}
           </otherwise>
       </choose>
    </where>
</select>
```

如果直接查询

![image-20201226155139866](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201226155139866.png)

所以说至少满足otherwise里面的条件



```java
@Test
public void queryBlogChoose2()
{
    SqlSession sqlSession = MybatisUntil.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    HashMap map = new HashMap();
    map.put("title","沁园春.雪");
    //map.put("author","教员");
    map.put("views",10);
    List<Blog> blogs = mapper.queryBlogChoose2(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }
    sqlSession.close();
}
```

不管是**满足里面的第一个when和otherwise亦或是第二个when和otherwise都只执行顺序第一个**，类似于java里面的switch，前面有一个满足的条件程序就会结束，如果都不满足，至少会输出最后一个的default

#### set

**set元素会动态地在行首插入 SET 关键字，并会删掉额外的逗号**

```xml
<update id="updateBlog" parameterType="map">
    update blog
    <set>
        <if test="title!=null" >
            title=#{title},
        </if>
        <if test="author!=null">
            author = #{author}
        </if>
    </set>
    where id = #{id}
</update>
```

如上所示，如果不加标签，会导致，我只更改一个title会导致 where前面多一个逗号，造成sql语句错误。

#### trim

手动设置前缀后缀

prefix：表示在trim标签内sql语句加上前缀xxx

suffix：表示在trim标签内sql语句加上后缀xxx

suffixOverrides：表示去除最后一个后缀xxx

xxx表示属性引号中的值;

prefixOverrides：表示去除第一个前缀and或者or



<font color='red'>所谓的动态sql，本质上还是SQL语句，只是我们在sql层面，去执行了一个逻辑代码。</font>



#### SQL片段

有的时候，我们可能会将一些功能的部分抽取出来，方便服用

1.使用SQL标签抽取公共的部分

```xml
<sql id="if-title-author">
    <if test="title!=null">
        title = #{title}
    </if>
    <if test="author!=null">
        and author = #{author}
    </if>
</sql>
```

2.在需要使用的地方使用include标签引用即可

```xml
<select id="queryBlogChoose" parameterType="map" resultType="blog">
    select * from blog
    <where>
        <include refid="if-title-author"></include>
    </where>

</select>
```

注意事项：

- <font color='red'>最好基于单表来定义SQL片段</font>
- 不要存在where标签！where一般根据条件子句来查询，<font color='blue'>where标签会自动优化，不宜放到SQL片段里</font>。





#### Foreach

![image-20201227155055271](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201227155055271.png)



```xml
<select id="getBlogForeach" parameterType="map" resultType="blog">
    select * from blog
    <where>
        <foreach collection="ids" item="id" open="and (" close=")" separator="or">
            id = #{id}
        </foreach>
    </where>
</select>
```

**foreach**  标签里 **collection是集合的名字 item=集合项 open= 前缀 close=后缀 separator=间隔号**



```java
public void getBlogForeach()
{
    SqlSession sqlSession = MybatisUntil.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    HashMap map = new HashMap();
    ArrayList<Integer> ids = new ArrayList<Integer>();
    ids.add(1);
    ids.add(2);
    map.put("ids",ids);
    List<Blog> blogs = mapper.getBlogForeach(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }
    sqlSession.close();
}
```

<font color='red'>动态sql就是在拼接语句，我们只要保证sql的正确性，按照sql的格式，去排列组合就可以了</font>

建议

- 现在Mysql中写出完整的Sql，在对应去修改我们的动态SQL实现通用即可！



## 14.缓存

查询：连接数据库，耗资源！

一次查询的结果，给他暂存在一个可以直接取到的地方！--->内存：缓存

再次查询数据库的时候，直接在缓存取东西，不用再去数据库里查数据了。



![image-20201227174028554](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201227174028554.png)

多个用户去访问服务器，然后增加服务器，服务器对数据库进行读写，多个服务器造成读写变慢，然后增加了一个缓存机制，在服务器与数据库之间，读利用缓存解决，读写分离，如果再增加用户，开始水平复制数据库，数据库复制，多个数据库保证数据相同，只有读做缓存，写是不做缓存的，数据库的一致性用主从复制解决，更高级的实现是，读是读的数据库，写是写的数据库，写的话就要用到主从复制，当数据写进去的时候，另外的数据库立刻响应刷新，这就是主从复制的概念，里面有个哨兵机制，用来检测数据库是否更改，更改后立即变化。

### 14.1 简介

1.什么是缓存[Cache]?

- 存在内存中的临时数据
- 将用户经常查询的数据放在缓存（内存）中，用户去查询数据就不用从磁盘上（关系型数据库文件）查询，从缓存中查询，从而提高查询效率，解决了高并发系统的性能问题。

2.为什么使用缓存？

- 减少和数据库的交互次数，减少系统开销，提高系统效率。

3.什么样的数据库能使用缓存？

- 经常查询且不经常改变，同理经常改变不经常查询的不适用缓存。

### 14.2 Mybatis缓存

- Mybatis包含一个非常强的的查询缓存特性，它可以非常方便的定制和配置缓存，缓存可以极大的提升查询效率。

- Mybatis系统中默认定义了两级缓存：一级缓存和二级缓存

  - 默认情况下只有一级缓存开启。（Sqlsession级别的缓存，也称本地缓存）

    ```java
    SqlSession sqlSession = MybatisUntil.getSqlSession();
    //
    //
    //
    sqlSession.close();
    ```

    从连接数据库，到关闭连接，这之间的缓存称为一级缓存。

  - 二级缓存需要手动开启配置，他是基于namespace级别的缓存

    ![image-20201227174812208](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201227174812208.png)

    意思是这个接口里面的全部查询的缓存。

  - 为了提高扩展性，Mybatis定义了缓存接口Cache，我们可以通过实现Cache接口来自定义二级缓存。

### 14.3 一级缓存

- 一级缓存也叫本地缓存：
  - 与数据库同一次会话期间查询到的数据会放在本地的缓存中。
  - 以后如果需要相同的数据，直接从缓存中拿，没必要再去查询数据库。

测试步骤：

- 开启日志！
- 测试在一个Session中查询两次相同记录
- 查看日志输出

![image-20201227182023346](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201227182023346.png)

- 映射语句文件中的所有 select 语句的结果将会被缓存。

  相同的select语句会被缓存，不相同的则不会

  ![image-20201227182451671](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201227182451671.png)

- 映射语句文件中的所有 insert、update 和 delete 语句会刷新缓存。

  两句相同的查询语句，中间加入一条插入语句，则执行了两次相同的查询，证明会刷新缓存。

  因为增删改操作可能会改变原来的数据，所以必定会刷新缓存

  ![image-20201227183654679](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201227183654679.png)

**了解即可**：

- 缓存会使用最近最少使用算法（LRU, Least Recently Used）算法来清除不需要的缓存。
- 缓存不会定时进行刷新（也就是说，没有刷新间隔）。
- 缓存会保存列表或对象（无论查询方法返回哪种）的 1024 个引用。
- 缓存会被视为读/写缓存，这意味着获取到的对象并不是共享的，可以安全地被调用者修改，而不干扰其他调用者或线程所做的潜在修改

缓存失效的情况：

1.映射语句文件中的所有 select 语句的结果将会被缓存。

相同的select语句会被缓存，不相同的则不会

![image-20201227182451671](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201227182451671.png)

2.映射语句文件中的所有 insert、update 和 delete 语句会刷新缓存。

两句相同的查询语句，中间加入一条插入语句，则执行了两次相同的查询，证明会刷新缓存。

因为增删改操作可能会改变原来的数据，所以必定会刷新缓存

![image-20201227183654679](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201227183654679.png)

3.查询不同的Mapper.xml

- 这种情况二级缓存都没开启，一级缓存不可能存在。

4.手动清理缓存

- ```java
  sqlSession.clearCache();
  ```

  添加清理缓存方法，手动清理缓存。

  ![image-20201227184512092](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201227184512092.png)

一级缓存默认开启，只在一次sqlSession中有效，也就是开启连接到关闭连接之间。

一级缓存相当于一个map，把查询语句，和对应的结果放进去，如果下次查询语句与里面的一个查询语句相同，那么就直接拿到结果就可以。

![image-20201227184848932](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201227184848932.png)

### 14.4 二级缓存

- 二级缓存也叫做全局缓存，一级缓存由于作用域太低，诞生了二级缓存。
- 基于namespace级别的缓存，一个名称空间，对应一个二级缓存。
- 工作机制
  - 一个会话查询一条数据，这个数据就会被放在当前会话的一级缓存中
  - 如果当前会话关闭了，这个会话对应的一级缓存就没了；但我们想要的是，会话关闭了，一级缓存中的数据会被保存到二级缓存中；
  - 新的会话查询信息，就可以从二级缓存中获取内容；
  - 不同的mapper查询的数据会放在自己对应的缓存(map)中。

默认情况下，只启用了本地的会话缓存，它仅仅对一个会话中的数据进行缓存。 要启用全局的二级缓存，只需要在你的 SQL 映射文件中添加一行：

![image-20201227185414233](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201227185414233.png)

步骤：

​	1.开启全局缓存

```xml
<settings>
   <-- 默认开启缓存，一般写为true，为了方便代码可读性-->
    <setting name="cacheEnabled" value="true"/>
</settings>
```

​	2.开启二级缓存

```xml
<mapper namespace="com.wang.mapper.UserMapper">
    <!--在当前mapper中使用二级缓存-->
    <cache
            eviction="FIFO" //清楚策略
            flushInterval="60000"//刷新间隔
            size="512"//最多储存结果对象
            readOnly="true"/>
```

**eviction** **：清除策略**

可用的清除策略有：

- `LRU` – 最近最少使用：移除最长时间不被使用的对象。
- `FIFO` – 先进先出：按对象进入缓存的顺序来移除它们。
- `SOFT` – 软引用：基于垃圾回收器状态和软引用规则移除对象。
- `WEAK` – 弱引用：更积极地基于垃圾收集器状态和弱引用规则移除对象

**flushInterval：刷新间隔**

**size：最多储存结果对象**

**readOnly：是否只读**

​	3.测试数据

```java
public void queryUser()
{
    SqlSession sqlSession = MybatisUntil.getSqlSession();
    SqlSession sqlSession2 = MybatisUntil.getSqlSession();//创建两个连接对象
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
    User user = mapper.queryUser(2);
    System.out.println(user);
    sqlSession.close();//二级缓存开启要在一级缓存结束之后，意思是说，要在第一个连接关闭之后，把数据放到缓存里，才能使二级缓存生效
    User user1 = mapper2.queryUser(2);
    System.out.println(user1);
    sqlSession2.close();
}
```

结果

![image-20201227221940158](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201227221940158.png)

两个sqlsession只查了一次

问题：我们需要将实体类序列化，否则会报错

```xml
Caused by: java.io.NotSerializableException: com.wang.entity.User
```

实体序列化操作

```java
public class User implements Serializable
```

小结：

- 只要开启了二级缓存，在同一个mapper之下就有效
- 所有的数据都会优先放在一级缓存中
- <font color='red'>只有当会话提交，或者关闭的时候，才会提交到二级缓存中！</font>



### 14.5 缓存原理

![image-20201227225234347](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201227225234347.png)

缓存原理以及先后顺序。

![image-20201227225536346](C:\Users\19225\AppData\Roaming\Typora\typora-user-images\image-20201227225536346.png)

可以手动设置，查询语句是否使用缓存，以及更新数据是否刷新缓存

### 14.6 自定义缓存-ehcache

Ehcache是一种广泛使用的开源Java分布式缓存。主要面向通用缓存,Java EE和轻量级容器。

了解如何使用自定义缓存

要在程序中使用ehcache，先要导包！

```xml
<dependency>
    <groupId>org.mybatis.caches</groupId>
    <artifactId>mybatis-ehcache</artifactId>
    <version>1.1.0</version>
</dependency>
```

在mapper中指定使用我们的ehcache缓存实现！

```xml
<cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
         updateCheck="false">

    <diskStore path="./tmpdir/Tmp_EhCache"/>

    <defaultCache
            eternal="false"
            maxElementsInMemory="10000"
            overflowToDisk="false"
            diskPersistent="false"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="259200"
            memoryStoreEvictionPolicy="LRU"/>

    <cache
            name="cloud_user"
            eternal="false"
            maxElementsInMemory="5000"
            overflowToDisk="false"
            diskPersistent="false"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="1800"
            memoryStoreEvictionPolicy="LRU"/>
</ehcache>
```



Redis数据库来做缓存，K-V！

