package com.example.blog.service.es;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.entity.es.EsBlog;
import com.example.blog.repository.es.EsBlogRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhraseQuery;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;


@Service
public class EsBlogService {

	@Autowired
	private EsBlogRepository esBlogRepository;
	
	@Autowired
    private ElasticsearchClient elasticsearchClient;
	
	
	private final String indexName = "blog";
	
	// https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/searching.html#_nested_search_queries
	public List<EsBlog> queryBlogs(String keyword) throws ElasticsearchException, IOException{
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
		
		// Combine title / author / content queries to search the blog index
		SearchResponse<EsBlog> response = elasticsearchClient.search(s -> s
		    .index(indexName)
		    .query(q -> q
		        .bool(b -> b 
		        	.should(MatchPhraseQuery.of(m -> m 
		        			.field("title")
		        			.query(keyword)
		        			)._toQuery())	
		        	.should(MatchPhraseQuery.of(m -> m 
		        			.field("author")
		        			.query(keyword)
		        			)._toQuery())
		        	.should(MatchPhraseQuery.of(m -> m 
		    			    .field("content")
		    			    .query(keyword)
		        			)._toQuery())
		            // .must(byName) 
		            // .must(byMaxPrice)
		        )
		    ),
		    EsBlog.class
		);

		List<EsBlog> result = new ArrayList<>();
		List<Hit<EsBlog>> hits = response.hits().hits();
		for (Hit<EsBlog> hit: hits) {
			EsBlog blog = hit.source();
			result.add(blog);
		}
		
		return result;
	}
	
	// ======================================
	// https://www.pixeltrice.com/spring-boot-elasticsearch-crud-example/
	
	public String createOrUpdateDocument(EsBlog blog) throws IOException {
        IndexResponse response = elasticsearchClient.index(i -> i
                .index(indexName)
                .id(blog.getId().toString())
                .document(blog)
        );
        
        if(response.result().name().equals("Created")){
            return new StringBuilder("Document has been successfully created.").toString();
        } else if(response.result().name().equals("Updated")){
            return new StringBuilder("Document has been successfully updated.").toString();
        }
        
        return new StringBuilder("Error while performing the operation.").toString();
    }

    public EsBlog getDocumentById(int blogId) throws IOException{
        EsBlog blog = null;
        GetResponse<EsBlog> response = elasticsearchClient.get(g -> g
                        .index(indexName)
                        .id(blogId + ""),
                        EsBlog.class
        );

        if (response.found()) {
        	blog = response.source();
            System.out.println("Blog title " + blog.getTitle());
        } else {
            System.out.println ("Blog not found");
        }

        return blog;
    }

    public String deleteDocumentById(int blogId) throws IOException {
        DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(blogId + ""));
        DeleteResponse deleteResponse = elasticsearchClient.delete(request);
        if (Objects.nonNull(deleteResponse.result()) &&
        		!deleteResponse.result().name().equals("NotFound")) {
            return new StringBuilder("Blog with id " + deleteResponse.id() +
            		" has been deleted.").toString();
        }
        
        System.out.println("Blog not found");
        return new StringBuilder("Blog with id " + deleteResponse.id() +
        		" does not exist.").toString();
    }

    public  List<EsBlog> searchAllDocuments() throws IOException {
        SearchRequest searchRequest =  SearchRequest.of(s -> s.index(indexName));
        SearchResponse searchResponse =  elasticsearchClient.search(
        		searchRequest, EsBlog.class);
        
        List<Hit> hits = searchResponse.hits().hits();
        List<EsBlog> blogs = new ArrayList<>();
        for(Hit object : hits){

            System.out.print(((EsBlog) object.source()));
            blogs.add((EsBlog) object.source());

        }
        return blogs;
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
