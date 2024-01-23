package edu.miu.demoinclass.service;

import edu.miu.demoinclass.entity.dto.input.UserDto;
import edu.miu.demoinclass.entity.dto.output.CommentResponseDto;
import edu.miu.demoinclass.entity.dto.output.PostResponseDto;
import edu.miu.demoinclass.entity.dto.output.UserResponseDto;
import edu.miu.demoinclass.exception.CommentNotFoundException;
import edu.miu.demoinclass.exception.PostNotFoundException;
import edu.miu.demoinclass.exception.UserNotFoundException;
import edu.miu.demoinclass.entity.Post;
import edu.miu.demoinclass.entity.User;
import edu.miu.demoinclass.repository.PostRepo;
import edu.miu.demoinclass.repository.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    @Autowired
    public UserService(
            UserRepo userRepo,
            PostRepo postRepo,
            ModelMapper modelMapper,
            EntityManager entityManager
    ) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
    }

    public List<UserResponseDto> findAllUsers(Integer havingMoreThanNPosts, String title) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        if (havingMoreThanNPosts != null) {
            Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
            Root<User> subRootUser = subquery.from(User.class);
            Join<User, Post> joinUserPosts = subRootUser.join("posts");

            subquery.select(criteriaBuilder.count(joinUserPosts));
            subquery.where(criteriaBuilder.equal(subRootUser, root));
            subquery.groupBy(subRootUser.get("id"));
            subquery.having(criteriaBuilder.gt(criteriaBuilder.count(joinUserPosts), havingMoreThanNPosts));

            predicates.add(criteriaBuilder.exists(subquery));
        }

        if (title != null && !title.isEmpty()) {
            root.join("posts");
            predicates.add(criteriaBuilder.like(root.get("posts").get("title"), "%" + title + "%"));
        }

        if (!predicates.isEmpty()) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }

        List<User> users = entityManager.createQuery(criteriaQuery).getResultList();

        return convertToResponseDtoList(users);
    }

    public UserResponseDto findUserById(long id) {
        Optional<User> user = userRepo.findById(id);
        return user.map(this::convertToResponseDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public long createAndSaveUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        userRepo.save(user);
        return user.getId();
    }

    private UserResponseDto convertToResponseDto(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }

    private List<UserResponseDto> convertToResponseDtoList(List<User> users) {
        return users.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<PostResponseDto> findAllUserPosts(long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return user.getPosts().stream()
                .map(post -> modelMapper.map(post, PostResponseDto.class))
                .collect(Collectors.toList());
    }

    public CommentResponseDto findUserPostComment(
            long postId,
            long commentId
    ) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        // TODO inefficient, optimize
        return post.getComments().stream()
                .filter(comment -> comment.getId() == commentId)
                .findFirst()
                .map(comment -> modelMapper.map(comment, CommentResponseDto.class))
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    public void deleteUserById(long userId) {
        userRepo.deleteById(userId);
    }
}