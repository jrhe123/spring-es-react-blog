package com.example.blog.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.entity.es.EsBlog;

@Repository
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, Integer> {

}
