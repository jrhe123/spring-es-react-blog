package com.example.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BlogSearchDTO {
	
	@NotEmpty(message = "type must be mysql or es")
	private String type;
	
	@NotEmpty
	@Size(min = 2, message = "keyword min length is 2")
	private String keyword;
}
