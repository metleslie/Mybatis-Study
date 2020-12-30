import com.wang.entity.User;
import com.wang.mapper.UserMapper;
import com.wang.utils.MybatisUntil;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 19225
 * @create 2020/12/10 20:50
 */
public class MyTest {
    static Logger logger = Logger.getLogger(MyTest.class);

    @Test
    public void test()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.getUserList();
        for (User user: userList) {
            System.out.println(user);
        }
        sqlSession.close();
    }
    @Test
    public void a()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        logger.info("进入a()方法成功！");
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User userById = mapper.getUserById(1);
        System.out.println(userById);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void b()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",10);
        map.put("name","小明");
        map.put("pwd","123456");
        mapper.addUser(map);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void testLog4j()
    {
       logger.info("info:进入testLog4j");
       logger.debug("debug:进入testLog4j");
       logger.error("error:进入testLog4j");
    }
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
}
