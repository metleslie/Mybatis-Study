package com.wang.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author 19225
 * @create 2020/12/28 17:48
 */
public class MybatisUtil {
    private static SqlSessionFactory sqlSession;
    static{
        String resource = "mybatis-config.xml";
        try {
            InputStream stream = Resources.getResourceAsStream(resource);
            sqlSession = new SqlSessionFactoryBuilder().build(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SqlSession getSqlSession()
    {
        return sqlSession.openSession(true);
    }
}
