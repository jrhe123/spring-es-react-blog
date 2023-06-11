package com.example.blog.repository.es;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.blog.entity.es.EsBlog;
import com.example.blog.entity.mysql.MysqlBlog;

@Repository
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, Integer> {

	/**
	 * 
	 * {
		  "query": {
		    "match": {
		      "title": {
		        "query": "article xxx"
		      }
		    }
		  }
		}
	 */
	
	@Query("{\"match\": {\"title\": {\"query\": \"?0\"}}}")
    Page<EsBlog> findByTitle(String title, Pageable pageable);
	
	@Query("{\"match\": {\"author\": {\"query\": \"?0\"}}}")
    Page<EsBlog> findByAuthor(String author, Pageable pageable);
	
	@Query("{\"match\": {\"content\": {\"query\": \"?0\"}}}")
    Page<EsBlog> findByContent(String content, Pageable pageable);
		
}
