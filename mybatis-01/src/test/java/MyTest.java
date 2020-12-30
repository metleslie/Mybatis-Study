import com.wang.entity.User;
import com.wang.mapper.UserMapper;
import com.wang.utils.MybatisUntil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 19225
 * @create 2020/12/10 20:50
 */
public class MyTest {
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
    public void getUserById()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User userById = mapper.getUserById(1);
        System.out.println(userById);
        sqlSession.close();
    }
    @Test
    public void addUser()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int res = mapper.addUser(new User(4, "小狗", "123456"));
        if(res>0)
        {
            System.out.println("插入成功！");
        }
        //提交事务 增删改需要提交事务，否则的话数据库不会改变数据
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void addUser2()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userId",2);
        map.put("userName","小红");
        mapper.addUser2(map);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void change2()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",2);
        map.put("userName","威士忌");
        mapper.change2(map);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void change()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.change(new User(4,"小猫","456123"));
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void deleteUser()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.deleteUser(4);

        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void getLike()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> like = mapper.getLike("王");
        for(User user: like)
        {
            System.out.println(user);
        }
        sqlSession.close();
    }

}
