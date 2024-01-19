package edu.miu.demoinclass.service;

import edu.miu.demoinclass.dto.input.CommentDto;
import edu.miu.demoinclass.dto.input.UserDto;
import edu.miu.demoinclass.dto.output.CommentResponseDto;
import edu.miu.demoinclass.dto.output.PostResponseDto;
import edu.miu.demoinclass.dto.output.UserResponseDto;
import edu.miu.demoinclass.exception.CommentNotFoundException;
import edu.miu.demoinclass.exception.PostNotFoundException;
import edu.miu.demoinclass.exception.UserNotFoundException;
import edu.miu.demoinclass.model.Comment;
import edu.miu.demoinclass.model.Post;
import edu.miu.demoinclass.model.User;
import edu.miu.demoinclass.repository.CommentRepo;
import edu.miu.demoinclass.repository.PostRepo;
import edu.miu.demoinclass.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final PostRepo postRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentService(PostRepo postRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    public long createAndSaveComment(long userId, long postId, CommentDto commentDto) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        Comment comment = modelMapper.map(commentDto, Comment.class);
        post.getComments().add(comment);
        postRepo.save(post);

        return comment.getId();
    }
}