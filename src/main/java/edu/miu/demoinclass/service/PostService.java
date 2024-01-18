package edu.miu.demoinclass.service;

import edu.miu.demoinclass.dto.input.PostDto;
import edu.miu.demoinclass.dto.output.PostResponseDto;
import edu.miu.demoinclass.model.Post;
import edu.miu.demoinclass.repository.PostRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepo postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PostService(PostRepo postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    public List<PostResponseDto> findAllPosts(String author) {
        List<Post> posts;

        if (author != null && !author.isEmpty()) {
            posts = postRepository.findByAuthorContaining(author);
        } else {
            posts = postRepository.findAll();
        }

        return convertToResponseDtoList(posts);
    }

    public PostResponseDto findPostById(long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(this::convertToResponseDto).orElse(null);
    }

    public long createAndSavePost(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }

    public void updatePostById(long postId, PostDto updatedDto) {
        Post updatedPost = modelMapper.map(updatedDto, Post.class);
        if (postRepository.existsById(postId)) {
            updatedPost.setId(postId);
            postRepository.save(updatedPost);
        }
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