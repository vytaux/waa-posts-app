package edu.miu.demoinclass.entity.dto.output;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {
    long id;
    String name;
    List<PostResponseDto> posts;
}