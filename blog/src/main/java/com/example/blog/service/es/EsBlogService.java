package com.example.blog.service.es;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.stereotype.Service;

import com.example.blog.entity.es.EsBlog;
import com.example.blog.repository.es.EsBlogRepository;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery.Builder;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhraseQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;


@Service
public class EsBlogService {

	@Autowired
	private EsBlogRepository esBlogRepository;
	
	
	public List<EsBlog> queryBlogs(String keyword){
		/**
		 * POST /blog/_search
			{
			  "query": {
			    "bool": {
			      "should": [
			        {
			          "match_phrase": {
			            "title": "title"
			          }
			        },
			        {
			          "match_phrase": {
			            "author": "title"
			          }
			        },
			        {
			          "match_phrase": {
			            "content": "title"
			          }
			        }
			      ]
			    }
			  }
			}
		 */
		
//		MatchPhraseQuery mp = QueryBuilders.matchPhrase(null)
//		QueryBuilders.bool(fs -> fs.should(mp -> mp.matchPhrase(null)))
		
		MatchPhraseQuery titleMp = QueryBuilders.matchPhrase()
				.queryName("title").query(keyword).build();
		MatchPhraseQuery authorMp = QueryBuilders.matchPhrase()
				.queryName("author").query(keyword).build();
		MatchPhraseQuery contentMp = QueryBuilders.matchPhrase()
				.queryName("content").query(keyword).build();
		
		List list = Arrays.asList(titleMp, authorMp, contentMp);
		BoolQuery bool = QueryBuilders.bool().should(
				list
				).build();
		String string = bool.toString();
		
		System.out.println("!!!!!!!!!");
		System.out.println("!!!!!!!!!");
		System.out.println("!!!!!!!!!");
		System.out.println("string: " + string);
		
//		esBlogRepository.searchSimilar(null, null, null)
//		
//		esBlogRepository.
		
		return null;
	}
	
	
	// ======================================
	
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
