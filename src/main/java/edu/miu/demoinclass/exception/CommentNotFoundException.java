package edu.miu.demoinclass.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CommentNotFoundException extends ResponseStatusException {
    public CommentNotFoundException(long commentId) {
        super(HttpStatus.NOT_FOUND, "Comment with id " + commentId + " not found");
    }
}
