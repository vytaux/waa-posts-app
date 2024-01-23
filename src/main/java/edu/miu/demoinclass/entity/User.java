package edu.miu.demoinclass.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private List<Role> roles;
}