import com.wang.entity.Student;
import com.wang.entity.Teacher;
import com.wang.mapper.StudentMapper;
import com.wang.mapper.TeacherMapper;
import com.wang.utils.MybatisUntil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * @Author 19225
 * @create 2020/12/23 17:52
 */
public class MyTest {
    @Test
    public void getTeacher()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher(1);
        System.out.println(teacher);
        sqlSession.close();
    }
    @Test
    public void getTeacher2()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher2(1);
        System.out.println(teacher);
        sqlSession.close();
    }
}
