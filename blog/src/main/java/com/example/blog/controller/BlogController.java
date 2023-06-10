package com.example.blog.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.entity.Blog;
import com.example.blog.service.BlogService;

@RestController
@RequestMapping("/blog")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@PostMapping("/blog")
    public Blog createBlog(@RequestBody Blog blog) {
		blog.setCreateTime(new Date());
        return blogService.createBlog(blog);
    }

    @GetMapping("/blog/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable int id) {
    	Blog blog = blogService.getBlogById(id);
        if (blog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(blog);
    }

    @PutMapping("/blog/{id}")
    public ResponseEntity<Blog> updateBlog(
    		@RequestBody Blog blog,@PathVariable int id){
    	Blog blogFromDb = blogService.getBlogById(id);
        if (blogFromDb == null){
            return ResponseEntity.notFound().build();
        }
        blogFromDb.setAuthor(blog.getAuthor());
        blogFromDb.setTitle(blog.getTitle());
        blogFromDb.setContent(blog.getContent());
        blogFromDb.setUpdateTime(new Date());
        Blog updatedBlog = blogService.updateBlog(blogFromDb);
        return ResponseEntity.ok(updatedBlog);
    }

    @GetMapping("/blogs")
    public List<Blog> getAllBlog(){
        return blogService.getAllBlog();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Blog> deleteBlog(@PathVariable int id){
        Blog blog = blogService.getBlogById(id);
        if (blog == null)
            return ResponseEntity.notFound().build();
        blogService.deleteBlog(blog);
        return ResponseEntity.ok().build();
    }
}
