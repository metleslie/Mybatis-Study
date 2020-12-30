import com.wang.entity.User;
import com.wang.mapper.UserMapper;
import com.wang.utils.MybatisUntil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

/**
 * @Author 19225
 * @create 2020/12/27 18:01
 */
public class MyTest {
    @Test
    public void a()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> user = mapper.getUser();
        for (User user1 : user) {
            System.out.println(user1);
        }
        sqlSession.close();
    }
    @Test
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
}
