package com.edu.ulab.app.dao.mapper;

import com.edu.ulab.app.dao.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.web.request.BookRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapperDto {
    Book bookDtoToBook(BookDto bookDto);

    BookDto bookToBookDto(Book book);
}
