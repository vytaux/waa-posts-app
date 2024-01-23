package edu.miu.demoinclass.service;

import edu.miu.demoinclass.entity.dto.input.CommentDto;
import edu.miu.demoinclass.exception.PostNotFoundException;
import edu.miu.demoinclass.entity.Comment;
import edu.miu.demoinclass.entity.Post;
import edu.miu.demoinclass.repository.PostRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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