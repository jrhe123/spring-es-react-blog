package com.example.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.entity.Blog;
import com.example.blog.repository.BlogRepository;

@Service
public class BlogService {
	
	@Autowired
	private BlogRepository blogRepository;
	
	public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public Blog getBlogById(int id) {
        return blogRepository.findById(id).orElse(null);
    }

    public Blog updateBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public List<Blog> getAllBlog() {
        return blogRepository.findAll();
    }

    public void deleteBlog(Blog blog) {
    	blogRepository.delete(blog);
    }

}
