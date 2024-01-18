package edu.miu.demoinclass.service;

import edu.miu.demoinclass.dto.input.UserDto;
import edu.miu.demoinclass.dto.output.PostResponseDto;
import edu.miu.demoinclass.dto.output.UserResponseDto;
import edu.miu.demoinclass.exception.UserNotFoundException;
import edu.miu.demoinclass.model.User;
import edu.miu.demoinclass.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepo userRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    public List<UserResponseDto> findAllUsers(boolean havingMoreThan1Post) {
        List<User> users;

        if (havingMoreThan1Post) {
            users = userRepo.findUsersHavingMoreThan1Post();
        } else {
            users = userRepo.findAll();
        }

        return convertToResponseDtoList(users);
    }

    public UserResponseDto findUserById(long id) {
        Optional<User> user = userRepo.findById(id);
        return user.map(this::convertToResponseDto).orElse(null);
    }

    public long createAndSaveUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepo.save(user);
        return savedUser.getId();
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
}