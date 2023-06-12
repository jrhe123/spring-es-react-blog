package com.example.blog.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.dto.BlogSearchDTO;
import com.example.blog.entity.es.EsBlog;
import com.example.blog.entity.mysql.MysqlBlog;
import com.example.blog.repository.es.EsBlogRepository;
import com.example.blog.service.es.EsBlogService;
import com.example.blog.service.mysql.MysqlBlogService;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;

@RestController
@RequestMapping("/blogs")
public class BlogController {
	
	@Autowired
	private MysqlBlogService blogService;
	
	@Autowired
	private EsBlogService esBlogService;

	
	@GetMapping("test")
    public void test(){
		System.out.println("app is running");
		
		System.out.println("pass here");
    }
	
//	@GetMapping("/populate")
//    public String populate() {
//    	for(int i = 0; i < 50; i++) {
//    		MysqlBlog blog = new MysqlBlog();
//    		blog.setTitle("this is title " + i);
//    		blog.setAuthor("this is author " + i);
//    		blog.setContent("this is content " + i);
//    		blogService.createBlog(blog);
//    	}
//    	return "done";
//    }
//	
	@GetMapping("")
    public List<MysqlBlog> getAllBlog(){
		// mysql
        return blogService.queryAll();
    }
	
	@PostMapping("search")
    public HashMap searchBlog(
    		@RequestBody BlogSearchDTO blogSearchDTO
    		) throws ElasticsearchException, IOException{
		StopWatch watch = new StopWatch();
		watch.start();
		
		HashMap<String, Object> resultHashMap = new HashMap<>();
		if (blogSearchDTO.getType().equalsIgnoreCase("es")) {
			// es
			List<EsBlog> blogs = esBlogService.queryBlogs(blogSearchDTO.getKeyword());
			resultHashMap.put("blogs", blogs);
		} else {
			// default mysql
			List<MysqlBlog> blogs = blogService.queryBlogs(blogSearchDTO.getKeyword());
			resultHashMap.put("blogs", blogs);
		}
		
		watch.stop();
		long totalTimeMillis = watch.getTotalTimeMillis();
		
		resultHashMap.put("duration", totalTimeMillis);
		
        return resultHashMap;
    }
	
	
	// ===========================================
	
	
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
