package edu.miu.demoinclass.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;
}