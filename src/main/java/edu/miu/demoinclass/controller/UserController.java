package edu.miu.demoinclass.controller;

import edu.miu.demoinclass.dto.input.UserDto;
import edu.miu.demoinclass.dto.output.PostResponseDto;
import edu.miu.demoinclass.dto.output.UserResponseDto;
import edu.miu.demoinclass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", ""})
    public ResponseEntity<List<UserResponseDto>> findAllUsers(
            @RequestParam(required = false) boolean havingMoreThan1Post
    ) {
        List<UserResponseDto> users = userService.findAllUsers(havingMoreThan1Post);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable long id) {
        UserResponseDto responseDto = userService.findUserById(id);
        return (responseDto != null)
                ? ResponseEntity.ok(responseDto)
                : ResponseEntity.notFound().build();
    }

    @PostMapping({"/", ""})
    public ResponseEntity<Long> createUser(@RequestBody UserDto userDto) {
        Long savedUserId = userService.createAndSaveUser(userDto);
        return ResponseEntity.ok(savedUserId);
    }

    @GetMapping({"/{id}/posts", "/{id}/posts/"})
    public ResponseEntity<List<PostResponseDto>> findAllUserPosts(@PathVariable long id) {
        List<PostResponseDto> posts = userService.findAllUserPosts(id);
        return ResponseEntity.ok(posts);
    }
}