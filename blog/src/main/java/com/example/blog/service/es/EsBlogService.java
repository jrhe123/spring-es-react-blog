package com.example.blog.service.es;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.entity.es.EsBlog;
import com.example.blog.repository.es.EsBlogRepository;

@Service
public class EsBlogService {

	@Autowired
	private EsBlogRepository esBlogRepository;
	
	public EsBlog createBlog(EsBlog blog) {
		blog.setCreateTime(new Date());
		blog.setUpdateTime(new Date());
        return esBlogRepository.save(blog);
    }

    public EsBlog getBlogById(int id) {
        return esBlogRepository.findById(id).orElse(null);
    }

    public EsBlog updateBlog(EsBlog blog) {
    	blog.setUpdateTime(new Date());
        return esBlogRepository.save(blog);
    }

    public List<EsBlog> getAllBlog() {
    	Iterable<EsBlog> blogs = esBlogRepository.findAll();
    	
    	List<EsBlog> result = new ArrayList<EsBlog>();
        for (EsBlog blog : blogs) {
            result.add(blog);
        }
        return result;
    }

    public void deleteBlog(EsBlog blog) {
    	esBlogRepository.delete(blog);
    }
}
