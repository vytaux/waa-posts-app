package edu.miu.demoinclass.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
    public UserNotFoundException(long userId) {
        super(HttpStatus.NOT_FOUND, "User with id " + userId + " not found");
    }
}
