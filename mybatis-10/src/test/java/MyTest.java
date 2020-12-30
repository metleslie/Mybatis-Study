import com.wang.entity.User;
import com.wang.mapper.UserMapper;
import com.wang.utils.IDutils;
import com.wang.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * @Author 19225
 * @create 2020/12/28 18:01
 */
public class MyTest {
    @Test
    public void getUser()
    {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.getUser();
        for (User user : userList) {
            System.out.println(user);
        }
        sqlSession.close();
    }
    @Test
    public void getLoginUser()
    {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User admin = mapper.getLoginUser("admin");
        System.out.println(admin);
        sqlSession.close();
    }

    @Test
    public void addUser()
    {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        java.util.Date date = new java.util.Date();
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-DD");
        String format1 = simpleDateFormat1.format(date);
        String format = simpleDateFormat.format(date);
        long time = date.getTime();
        Time time1 = new Time(time);
        HashMap map = new HashMap();
        map.put("id", 50);
        map.put("userCode","476400");
        map.put("userName","润之");
        map.put("userPassword","123456");
        map.put("gender",1);
        map.put("birthday",format1);
        map.put("phone","1111111111");
        map.put("address","东方");
        map.put("userRole",1);
        map.put("createdBy",2);
        map.put("creationDate",format);
        map.put("modifyBy",1);
        map.put("modifyDate",format);
        mapper.addUser(map);
        sqlSession.close();
    }
    @Test
    public void getUserList()
    {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        HashMap map = new HashMap();
        map.put("userName","赵燕");
        map.put("userCode","zhaoyan");
        map.put("currentPageNo",0);
        map.put("pageSize",1);
        List<User> list = mapper.getUserList("赵燕", 3, 0, 1);
        System.out.println(list);
    }
    @Test
    public void getUserCount()
    {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int count = mapper.getUserCount("杨过", 3);
        System.out.println(count);
        sqlSession.close();
    }
    @Test
    public void deleteUserById()
    {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int i = mapper.deleteUserById(1);
        sqlSession.close();
    }
    @Test
    public void getUserById()
    {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserById(2);
        System.out.println(user);
        sqlSession.close();
    }
    @Test
    public void changeUser()
    {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(2);
        System.out.println(user);
        sqlSession.close();
    }
    @Test
    public void updatePwd()
    {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.updatePwd(2,"8008208802");
        sqlSession.close();
    }

}
