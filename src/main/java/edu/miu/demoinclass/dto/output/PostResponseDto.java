package edu.miu.demoinclass.dto.output;

import lombok.Data;

@Data
public class PostResponseDto {
    long id;
    String title;
    String content;
    String author;
}