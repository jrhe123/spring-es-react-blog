package com.example.blog.entity.es;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
@Document(
		indexName = "blog",
		createIndex = false
		)
public class EsBlog {

	@Id
	private Integer id;
	
	@Field(type = FieldType.Text, analyzer = "standard", name = "title")
	private String title;
	
	@Field(type = FieldType.Text, analyzer = "standard", name = "author")
	private String author;
	
	@Field(type = FieldType.Text, analyzer = "standard", name = "content")
	private String content;
	
	@Field(type = FieldType.Date, format = DateFormat.date,
			pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
	private Date createTime;
	
	@Field(type = FieldType.Date, format = DateFormat.date,
			pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
	private Date updateTime;
}
