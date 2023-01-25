package com.edu.ulab.app.entity;

import lombok.Data;

@Data
public class Book {
    private String id;
    private String userId;
    private String title;
    private String author;
    private long pageCount;
}

