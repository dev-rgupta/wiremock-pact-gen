package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post extends CreatePostInput {
	Post(Long id, Long userId, String title, String body) {
		super(userId, title, body);
		this.id = id;
	}

	Long id;
}