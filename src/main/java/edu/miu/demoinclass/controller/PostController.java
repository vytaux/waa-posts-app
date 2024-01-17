package edu.miu.demoinclass.controller;

import edu.miu.demoinclass.dto.PostDto;
import edu.miu.demoinclass.dto.PostResponseDto;
import edu.miu.demoinclass.model.Post;
import edu.miu.demoinclass.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping({"/", ""})
    public ResponseEntity<List<PostResponseDto>> findAllPosts(
        @RequestParam(required = false) Map<String, String> filters
    ) {
        List<PostResponseDto> posts = postService.findAllPosts(filters);

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findPostById(@PathVariable long id) {
        PostResponseDto responseDto = postService.findPostById(id);
        return (responseDto != null)
                ? ResponseEntity.ok(responseDto)
                : ResponseEntity.notFound().build();
    }

    @PostMapping({"/", ""})
    public ResponseEntity<Long> createPost(@RequestBody PostDto postDto) {
        Long savedPostId = postService.createAndSavePost(postDto);
        return ResponseEntity.ok(savedPostId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id) {
        boolean success = postService.deletePostById(id);
        if (success) {
            return ResponseEntity.ok("Post deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePostById(@PathVariable long id, @RequestBody PostDto postDto) {
        boolean success = postService.updatePostById(id, postDto);
        if (success) {
            return ResponseEntity.ok("Post updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}