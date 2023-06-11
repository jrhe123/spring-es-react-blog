package com.example.blog.service.es;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.repository.es.EsBlogRepository;

@Service
public class EsBlogService {

	@Autowired
	private EsBlogRepository esBlogRepository;
}
