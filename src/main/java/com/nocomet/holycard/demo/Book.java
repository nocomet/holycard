package com.nocomet.holycard.demo;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long bookId;

    private String name;

    private String author;
}
