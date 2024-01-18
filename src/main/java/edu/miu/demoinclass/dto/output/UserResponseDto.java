package edu.miu.demoinclass.dto.output;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {
    long id;
    String name;
    List<PostResponseDto> posts;
}