package com.example.demo;

import java.util.List;

public interface PostService {
	List<Post> getAllPosts();
	Post getPostById(Long postId)throws Exception;
	List<Post> getAllPostsByUserId(Long userId);
	Post createPost(Post post);
	Post updatePost(Long postId, Post post);
	void deletePost(Long postId);
}
