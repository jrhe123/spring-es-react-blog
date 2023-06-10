package com.example.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.entity.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {

}
