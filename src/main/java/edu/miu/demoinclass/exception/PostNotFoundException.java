package edu.miu.demoinclass.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PostNotFoundException extends ResponseStatusException {
    public PostNotFoundException(long postId) {
        super(HttpStatus.NOT_FOUND, "Post with id " + postId + " not found");
    }
}
