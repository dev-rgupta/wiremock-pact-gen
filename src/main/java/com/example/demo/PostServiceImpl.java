package com.example.demo;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Post> getAllPosts() {
		final String uri = "http://fakeapi.jsonparseronline.com/posts";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<Post> entity = new HttpEntity<Post>(headers);

		ResponseEntity<Post[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Post[].class);

		return Arrays.asList(response.getBody());
	}

	@Override
	public Post getPostById(Long postId) throws Exception {
		Post post = new Post();
		final String uri = "http://fakeapi.jsonparseronline.com/posts/" + postId;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Post> entity = new HttpEntity<Post>(headers);
		ResponseEntity<Post> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Post.class);
		JSONObject jsonObject = new JSONObject( response.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        post = objectMapper.readValue(jsonObject.toString(), Post.class);
  
		return post;
	}

	@Override
	public List<Post> getAllPostsByUserId(Long userId) {
		final String uri = "http://fakeapi.jsonparseronline.com/users/" + userId + "/posts";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Post> entity = new HttpEntity<Post>(headers);

		ResponseEntity<Post[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Post[].class);
		return Arrays.asList(response.getBody());
	}

	@Override
	public Post createPost(Post post) {
		final String uri = "http://fakeapi.jsonparseronline.com/posts";
		HttpEntity<Post> entity = new HttpEntity<Post>(post);

		ResponseEntity<Post> response = restTemplate.exchange(uri, HttpMethod.POST, entity, Post.class);

		return response.getBody();
	}

	@Override
	public Post updatePost(Long postId, Post post) {
		final String uri = "http://fakeapi.jsonparseronline.com/posts/" + postId;
		HttpEntity<Post> entity = new HttpEntity<Post>(post);

		ResponseEntity<Post> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Post.class);

		return response.getBody();
	}

	@Override
	public void deletePost(Long postId) {
		final String uri = "http://fakeapi.jsonparseronline.com/posts/" + postId;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Post> entity = new HttpEntity<Post>(headers);

		ResponseEntity<Post> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, Post.class);

	}

}
