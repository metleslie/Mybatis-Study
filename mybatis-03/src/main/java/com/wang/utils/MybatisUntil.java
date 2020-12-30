package com.wang.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author 19225
 * @create 2020/12/11 1:43
 */
public class MybatisUntil {
    private static SqlSessionFactory sqlSession;
    static {

            String resource = "mybatis-config.xml";
        try {
            InputStream resourceAsStream =Resources.getResourceAsStream(resource);
            sqlSession = new SqlSessionFactoryBuilder().build(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static SqlSession getUntil()
    {
        return sqlSession.openSession();
    }
}
