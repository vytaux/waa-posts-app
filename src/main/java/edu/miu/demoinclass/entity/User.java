package edu.miu.demoinclass.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "\"user\"") // Use double quotes to handle reserved keyword
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Post> posts;
}