package com.edu.ulab.app.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBookResponse extends BaseWebResponse {
    private String userId;
    private List<String> booksIdList;
}
