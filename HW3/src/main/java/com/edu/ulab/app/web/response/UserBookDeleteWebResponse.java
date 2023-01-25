package com.edu.ulab.app.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBookDeleteWebResponse extends BaseWebResponse {
    private String message;
}
