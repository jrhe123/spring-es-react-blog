package com.example.blog.service.mysql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.entity.mysql.MysqlBlog;
import com.example.blog.repository.mysql.MysqlBlogRepository;

@Service
public class MysqlBlogService {
	
	@Autowired
	private MysqlBlogRepository blogRepository;
	
	public List<MysqlBlog> queryAll() {
        return blogRepository.queryAll();
    }
	
	// ==========================================
	
	public MysqlBlog createBlog(MysqlBlog blog) {
        return blogRepository.save(blog);
    }

    public MysqlBlog getBlogById(int id) {
        return blogRepository.findById(id).orElse(null);
    }

    public MysqlBlog updateBlog(MysqlBlog blog) {
        return blogRepository.save(blog);
    }

    public List<MysqlBlog> getAllBlog() {
        return blogRepository.findAll();
    }

    public void deleteBlog(MysqlBlog blog) {
    	blogRepository.delete(blog);
    }

}
