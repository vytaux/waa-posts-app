package edu.miu.demoinclass.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDto {
    String title;
    String content;
    String author;
}
