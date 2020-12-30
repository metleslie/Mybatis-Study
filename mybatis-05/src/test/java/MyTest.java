import com.wang.entity.User;
import com.wang.mapper.UserMapper;
import com.wang.utils.MybatisUntil;
import org.apache.ibatis.session.SqlSession;
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
}
