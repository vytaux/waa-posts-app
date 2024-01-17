package edu.miu.demoinclass.repository;

import edu.miu.demoinclass.dto.PostDto;
import edu.miu.demoinclass.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PostRepository {

    private final List<Post> posts = new ArrayList<>();
    private long nextId = 1;

    public List<Post> findPostsByFilters(Map<String, String> filters) {
        return posts.stream()
                .filter(post -> matchesFilters(post, filters))
                .collect(Collectors.toList());
    }

    private boolean matchesFilters(Post post, Map<String, String> filters) {
        return filters.entrySet().stream()
                .allMatch(entry -> matchesFilter(post, entry.getKey(), entry.getValue()));
    }

    private boolean matchesFilter(Post post, String property, String value) {
        switch (property) {
            case "author":
                return post.getAuthor().contains(value);
            // Add more cases for other properties if needed
            default:
                return false;
        }
    }

    public List<Post> findAllPosts() {
        return posts;
    }

    public Post findPostById(long id) {
        return posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Long savePost(Post post) {
        post.setId(nextId++);
        posts.add(post);
        return post.getId();
    }

    public boolean deletePostById(long id) {
        Iterator<Post> iterator = posts.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean updatePostById(long id, Post updatedPost) {
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId() == id) {
                updatePostFromEntity(posts.get(i), updatedPost);
                return true;
            }
        }
        return false;
    }

    private void updatePostFromEntity(Post post, Post updatedPost) {
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        post.setAuthor(updatedPost.getAuthor());
    }
}