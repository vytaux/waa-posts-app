package edu.miu.demoinclass.entity;

import javax.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String title;
    String content;
    String author;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    List<Comment> comments;
}