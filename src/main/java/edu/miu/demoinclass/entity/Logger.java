package edu.miu.demoinclass.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Logger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    Date datetime;
    String principle;
    String operation;
}