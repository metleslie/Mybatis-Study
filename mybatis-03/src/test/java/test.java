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
    public void b()
    {
        SqlSession until = MybatisUntil.getUntil();
        UserMapper mapper = until.getMapper(UserMapper.class);
        User userById = mapper.getUserById(1);
        System.out.println(userById);
        until.close();
        //select * from mybatis.user where id = #{id};
        //类型处理器
        //select id,name,pwd mybatis.user where id = #{id};
    }
}
