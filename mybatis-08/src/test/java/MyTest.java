import com.wang.entity.Blog;
import com.wang.mapper.BlogMapper;
import com.wang.utils.IDutils;
import com.wang.utils.MybatisUntil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.*;

/**
 * @Author 19225
 * @create 2020/12/23 17:52
 */
public class MyTest {


    @Test
    public void getBlog()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> blog = mapper.getBlog();
        for (Blog blog1 : blog) {
            System.out.println(blog1);
        }
        sqlSession.close();
    }
    @Test
    public void insertBlog()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = new Blog();
        blog.setAuthor("教员");
        blog.setTitle("沁园春.雪");
        blog.setCreateTime(new Date());
        blog.setId(IDutils.getId());
        blog.setViews(10);
        mapper.insertBlog(blog);
        sqlSession.close();
    }
    @Test
    public void queryBlog()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        HashMap map = new HashMap();
        map.put("title","沁园春.雪");
        map.put("author","教员");
        map.put("views",10);
        List<Blog> blogs = mapper.queryBlog(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }
    @Test
    public void queryBlogChoose()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        HashMap map = new HashMap();
        //map.put("title","沁园春.雪");
        //map.put("author","教员");
        //map.put("views",10);
        List<Blog> blogs = mapper.queryBlogChoose(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }
    @Test
    public void queryBlogChoose2()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        HashMap map = new HashMap();
        map.put("title","沁园春.雪");
        //map.put("author","教员");
        map.put("views",10);
        List<Blog> blogs = mapper.queryBlogChoose2(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }
    @Test
    public void updateBlog()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        HashMap map = new HashMap();
        map.put("title","沁园春.雪");
        map.put("author","教员");
        map.put("id","1");

        mapper.updateBlog(map);
        sqlSession.close();
    }
    @Test
    public void getBlogForeach()
    {
        SqlSession sqlSession = MybatisUntil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        HashMap map = new HashMap();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(2);
        map.put("ids",ids);
        List<Blog> blogs = mapper.getBlogForeach(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }

}
