package com.example.blog.repository.mysql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.blog.entity.mysql.MysqlBlog;

@Repository
public interface MysqlBlogRepository extends JpaRepository<MysqlBlog, Integer> {

	@Query("select e from MysqlBlog e order by e.createTime desc")
	List<MysqlBlog> queryAll();
	
	@Query(
		"select e from MysqlBlog e " 
		+ "where e.title like concat('%', :keyword, '%') or "
		+ "e.author like concat('%', :keyword, '%') or "
		+ "e.content like concat('%', :keyword, '%') "
		+ "order by e.createTime desc"
	)
	List<MysqlBlog> queryBlogs(@Param("keyword") String keyword);
}
