package edu.miu.demoinclass.service;

import edu.miu.demoinclass.entity.User;
import edu.miu.demoinclass.entity.dto.input.PostDto;
import edu.miu.demoinclass.entity.dto.output.PostResponseDto;
import edu.miu.demoinclass.exception.PostNotFoundException;
import edu.miu.demoinclass.entity.Post;
import edu.miu.demoinclass.repository.PostRepo;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import edu.miu.demoinclass.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepo postRepository;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;
    private final UserRepo userRepo;

    @Autowired
    public PostService(
            PostRepo postRepository,
            ModelMapper modelMapper,
            EntityManager entityManager,
            UserRepo userRepo
    ) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
        this.userRepo = userRepo;
    }

    public List<PostResponseDto> findAllPosts(String author, String title) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
        Root<Post> root = criteriaQuery.from(Post.class);

        List<Predicate> predicates = new ArrayList<>();

        if (author != null && !author.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("author"), "%" + author + "%"));
        }

        if (title != null && !title.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("title"), title));
        }

        if (!predicates.isEmpty()) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }

        List<Post> posts = entityManager.createQuery(criteriaQuery).getResultList();

        return convertToResponseDtoList(posts);
    }

    public PostResponseDto findPostById(long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(this::convertToResponseDto)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public long createAndSavePost(UserDetails userDetails, PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);

        User user = userRepo.findByEmail(userDetails.getUsername());
        post.setAuthor(user);

        postRepository.save(post);
        return post.getId();
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
//        PostResponseDto dto = modelMapper.map(post, PostResponseDto.class);
        PostResponseDto dto = new PostResponseDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthor(post.getAuthor().getFirstname());

        return dto;
    }

    private List<PostResponseDto> convertToResponseDtoList(List<Post> posts) {
        return posts.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
}