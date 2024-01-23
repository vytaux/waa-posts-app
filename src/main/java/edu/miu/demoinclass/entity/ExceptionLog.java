package edu.miu.demoinclass.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class ExceptionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date datetime;
    private String principle; // Fake static user
    private String operation;
    private String exceptionType;
}