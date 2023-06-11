package com.example.blog.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.entity.mysql.MysqlBlog;

@Repository
public interface MysqlBlogRepository extends JpaRepository<MysqlBlog, Integer> {

}
