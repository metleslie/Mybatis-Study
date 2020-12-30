package com.wang.mapper;

import com.wang.entity.Blog;

import java.util.HashMap;
import java.util.List;

/**
 * @Author 19225
 * @create 2020/12/25 21:46
 */
public interface BlogMapper {
    List<Blog> getBlog();
    int insertBlog(Blog blog);
    List<Blog> queryBlog(HashMap map);
    List<Blog> queryBlogChoose(HashMap map);
    List<Blog> queryBlogChoose2(HashMap map);
    int updateBlog(HashMap map);
    //查询id为123号记录的博客
    List<Blog> getBlogForeach(HashMap map);
}
