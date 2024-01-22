package edu.miu.demoinclass.controller;

import edu.miu.demoinclass.dto.input.PostDto;
import edu.miu.demoinclass.dto.output.PostResponseDto;
import edu.miu.demoinclass.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findAllPosts(
        @RequestParam(required = false) String author,
        @RequestParam(required = false) String title
    ) {
        List<PostResponseDto> posts = postService.findAllPosts(author, title);

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findPostById(@PathVariable long id) {
        PostResponseDto responseDto = postService.findPostById(id);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<Long> createPost(@RequestBody PostDto postDto) {
        Long savedPostId = postService.createAndSavePost(postDto);

        return ResponseEntity.ok(savedPostId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id) {
        postService.deletePostById(id);

        return ResponseEntity.ok("Post " + id + " deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePostById(@PathVariable long id, @RequestBody PostDto postDto) {
        postService.updatePostById(id, postDto);

        return ResponseEntity.ok("Post " + id + " updated successfully");
    }
}