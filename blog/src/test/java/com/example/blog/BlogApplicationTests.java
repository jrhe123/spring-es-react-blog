package com.example.blog;

import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.blog.entity.es.EsBlog;
import com.example.blog.repository.es.EsBlogRepository;

@SpringBootTest
class BlogApplicationTests {
	
	@Autowired
	EsBlogRepository esBlogRepository;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void testEsBlogRepo() {
		System.out.println("hit here !!!!");
		Iterable<EsBlog> allBlogs = esBlogRepository.findAll();
		System.out.println("hit here 2!!!!");
		Iterator<EsBlog> iterator = allBlogs.iterator();
		System.out.println("hit here 3!!!!");
		EsBlog nextBlog = iterator.next();
		System.out.println("---------------" + nextBlog.getTitle());
	}

}
