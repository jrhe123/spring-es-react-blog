package com.example.blog.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "t_blog")
public class Blog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String title;
	
	private String author;
	
	private String content;
	
	private Date createTime;
	
	private Date updateTime;
}