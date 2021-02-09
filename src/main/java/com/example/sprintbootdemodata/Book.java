package com.example.sprintbootdemodata;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue
    private Integer bookid;
    private String name;
    private String author;
}
