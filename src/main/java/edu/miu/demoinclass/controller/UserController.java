package edu.miu.demoinclass.controller;

import edu.miu.demoinclass.aspect.annotation.ExecutionTime;
import edu.miu.demoinclass.entity.dto.input.CommentDto;
import edu.miu.demoinclass.entity.dto.input.UserDto;
import edu.miu.demoinclass.entity.dto.output.CommentResponseDto;
import edu.miu.demoinclass.entity.dto.output.PostResponseDto;
import edu.miu.demoinclass.entity.dto.output.UserResponseDto;
import edu.miu.demoinclass.service.CommentService;
import edu.miu.demoinclass.service.ExceptionLogService;
import edu.miu.demoinclass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final CommentService commentService;
    private final ExceptionLogService exceptionLogService;

    @Autowired
    public UserController(
            CommentService commentService,
            UserService userService,
            ExceptionLogService exceptionLogService
    ) {
        this.commentService = commentService;
        this.userService = userService;
        this.exceptionLogService = exceptionLogService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAllUsers(
            @RequestParam(required = false) Integer havingMoreThanNPosts,
            @RequestParam(required = false) String title
    ) {
        List<UserResponseDto> users = userService
                .findAllUsers(havingMoreThanNPosts, title);

        return ResponseEntity.ok(users);
    }

    @ExecutionTime
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable long id) {
        UserResponseDto responseDto = userService.findUserById(id);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody UserDto userDto) {
        Long savedUserId = userService.createAndSaveUser(userDto);

        return ResponseEntity.ok(savedUserId);
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostResponseDto>> findAllUserPosts(@PathVariable long id) {
        List<PostResponseDto> posts = userService.findAllUserPosts(id);

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{userId}/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> findUserPostComment(
            @PathVariable long userId,
            @PathVariable long postId,
            @PathVariable long commentId
    ) {
        CommentResponseDto responseDto = userService.findUserPostComment(
            postId,
            commentId
        );

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{userId}/posts/{postId}/comments")
    public ResponseEntity<Long> createUserPostComment(
            @PathVariable long userId,
            @PathVariable long postId,
            @RequestBody CommentDto commentDto
    ) {
        Long savedCommentId = commentService.createAndSaveComment(userId, postId, commentDto);

        return ResponseEntity.ok(savedCommentId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id) {
        userService.deleteUserById(id);

        return ResponseEntity.ok("User " + id + " deleted successfully");
    }

    @GetMapping("/throwException")
    public String throwException() {
        throw new RuntimeException("This is a test exception");
    }
}