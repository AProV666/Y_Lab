package com.edu.ulab.app.dao.dto;

import lombok.Data;

@Data
public class BookDto {
    private String id;
    private String userId;
    private String title;
    private String author;
    private long pageCount;
}
