package com.example.blog.entity.mysql;

import java.util.Date;

import jakarta.persistence.*;
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
public class MysqlBlog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String title;
	
	private String author;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private Date createTime;
	
	private Date updateTime;
}



