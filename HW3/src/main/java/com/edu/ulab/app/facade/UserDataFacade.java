package com.edu.ulab.app.facade;

import com.edu.ulab.app.dao.dto.UserDto;
import com.edu.ulab.app.exception.NoUserFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserService userService, BookService bookService,
                          UserMapper userMapper, BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        String id = userService.create(userDto);
        log.info("Created user id: {}", id);

        List<String> bookListId = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(id))
                .map(bookService::create)
                .toList();

        return UserBookResponse.builder()
                .userId(id)
                .booksIdList(bookListId)
                .build();
    }

    public UserBookResponse updateUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book update request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);
        if (userService.get(userDto.getId()) == null) {
            throw new NoUserFoundException("No user with id found: " + userDto.getId());
        }
        userService.update(userDto);
        List<String> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .map(bookDto -> {
                    if (bookService.get(bookDto.getId()) != null) {
                        bookService.update(bookDto);
                        return bookDto.getId();
                    } else {
                        bookDto.setUserId(userDto.getId());
                        return bookService.create(bookDto);
                    }
                }).toList();
        return UserBookResponse.builder().userId(userDto.getId()).booksIdList(bookIdList).build();
    }

    public UserBookResponse getUserWithBooks(String userId) {
        log.info("Got user book get request by id: {}", userId);
        UserDto userDto = userService.get(userId);
        if (userDto == null) {
            throw new NoUserFoundException("No user with id found: " + userId);
        }
        log.info("Found user: {}", userDto);
        List<String> booksIdList = bookService.getListIdByUserId(userId);
        log.info("User books: {}", booksIdList);
        return UserBookResponse.builder().userId(userId).booksIdList(booksIdList).build();
    }

    public Boolean deleteUserWithBooks(String userId) {
        Boolean bookSuccess = bookService.deleteBooksByUserId(userId);
        Boolean userSuccess = userService.delete(userId);
        return bookSuccess && userSuccess;
    }
}