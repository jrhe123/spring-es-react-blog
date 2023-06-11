package com.example.blog.entity.mysql;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 
 * @author jiaronghe
 * 
 *	Create table spring_es_react_blog.`t_blog` (
		`id` int(11) NOT NULL AUTO_INCREMENT,
	    `title` varchar(255) DEFAULT NULL,
	    `author` varchar(255) DEFAULT NULL,
	    `content` text,
	    `create_time` datetime(6) DEFAULT NULL,
	    `update_time` datetime(6) DEFAULT NULL,
	    PRIMARY KEY (`id`),
	    INDEX index_title_author (title, author),
	    FULLTEXT INDEX index_content (content)
	) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
 */

@Entity
@Data
@Table(name = "t_blog")
@EntityListeners(AuditingEntityListener.class)
public class MysqlBlog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String title;
	
	private String author;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@CreatedDate
	private Date createTime;
	
	@LastModifiedDate
	private Date updateTime;
}



