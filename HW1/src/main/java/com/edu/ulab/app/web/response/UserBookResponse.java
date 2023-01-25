package com.edu.ulab.app.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBookResponse extends BaseWebResponse {
    private Long userId;
    private List<Long> booksIdList;
}
