package edu.miu.demoinclass.service;

import edu.miu.demoinclass.dto.PostDto;
import edu.miu.demoinclass.dto.PostResponseDto;
import edu.miu.demoinclass.model.Post;
import edu.miu.demoinclass.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PostService(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    public List<PostResponseDto> findAllPosts(Map<String, String> filters) {
        List<Post> posts;

        if (filters != null && !filters.isEmpty()) {
            // If filters are provided, customize the logic based on your requirements
            posts = postRepository.findPostsByFilters(filters);
        } else {
            // Otherwise, get all posts
            posts = postRepository.findAllPosts();
        }

        return convertToResponseDtoList(posts);
    }

    public PostResponseDto findPostById(long id) {
        Post post = postRepository.findPostById(id);
        return (post != null) ? convertToResponseDto(post) : null;
    }

    public long createAndSavePost(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        long savedPostId = postRepository.savePost(post);
        return savedPostId;
    }

    public boolean deletePostById(long id) {
        return postRepository.deletePostById(id);
    }

    public boolean updatePostById(long id, PostDto updatedDto) {
        Post updatedPost = modelMapper.map(updatedDto, Post.class);
        return postRepository.updatePostById(id, updatedPost);
    }

    private PostResponseDto convertToResponseDto(Post post) {
        return modelMapper.map(post, PostResponseDto.class);
    }

    private List<PostResponseDto> convertToResponseDtoList(List<Post> posts) {
        return posts.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
}