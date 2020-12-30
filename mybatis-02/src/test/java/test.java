import com.wang.entity.User;
import com.wang.mapper.UserMapper;
import com.wang.utils.MybatisUntil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * @Author 19225
 * @create 2020/12/11 1:51
 */
public class test {
    @Test
    public void a()
    {
        SqlSession until = MybatisUntil.getUntil();
        UserMapper mapper = until.getMapper(UserMapper.class);
        List<User> userList = mapper.getUserList();
        for (User user : userList) {
            System.out.println(user);
        }
        until.close();
    }
}
