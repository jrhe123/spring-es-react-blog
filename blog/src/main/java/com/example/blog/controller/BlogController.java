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

import com.example.blog.entity.mysql.MysqlBlog;
import com.example.blog.service.mysql.MysqlBlogService;

@RestController
@RequestMapping("/blogs")
public class BlogController {
	
	@Autowired
	private MysqlBlogService blogService;

	
	@GetMapping("test")
    public void test(){
		System.out.println("app is running");
		System.out.println("pass here");
    }
	
	@GetMapping("")
    public List<MysqlBlog> getAllBlog(){
        return blogService.getAllBlog();
    }
	
	@PostMapping("")
    public MysqlBlog createBlog(@RequestBody MysqlBlog blog) {
		blog.setCreateTime(new Date());
        return blogService.createBlog(blog);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MysqlBlog> getBlogById(@PathVariable int id) {
    	MysqlBlog blog = blogService.getBlogById(id);
        if (blog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(blog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MysqlBlog> updateBlog(
    		@RequestBody MysqlBlog blog,@PathVariable int id){
    	MysqlBlog blogFromDb = blogService.getBlogById(id);
        if (blogFromDb == null){
            return ResponseEntity.notFound().build();
        }
        blogFromDb.setAuthor(blog.getAuthor());
        blogFromDb.setTitle(blog.getTitle());
        blogFromDb.setContent(blog.getContent());
        blogFromDb.setUpdateTime(new Date());
        MysqlBlog updatedBlog = blogService.updateBlog(blogFromDb);
        return ResponseEntity.ok(updatedBlog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MysqlBlog> deleteBlog(@PathVariable int id){
        MysqlBlog blog = blogService.getBlogById(id);
        if (blog == null)
            return ResponseEntity.notFound().build();
        blogService.deleteBlog(blog);
        return ResponseEntity.ok().build();
    }
}
