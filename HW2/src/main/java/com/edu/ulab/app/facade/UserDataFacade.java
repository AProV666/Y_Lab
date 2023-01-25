package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;

import com.edu.ulab.app.service.impl.BookServiceImpl;
import com.edu.ulab.app.service.impl.BookServiceImplTemplate;
import com.edu.ulab.app.service.impl.UserServiceImpl;
import com.edu.ulab.app.service.impl.UserServiceImplTemplate;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import com.edu.ulab.app.web.response.UserDeleteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacade {
    private final UserServiceImpl userService;
    private final BookServiceImpl bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserServiceImpl userService,
                          BookServiceImpl bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse updateUserWithBooks(UserBookRequest userBookRequest, Long id) {
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        userDto.setId(id);
        log.info("Mapped user request: {}", userDto);

        UserDto newUser = userService.updateUser(userDto);
        log.info("New user: {}", userDto);

        List<BookDto> newBookList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(newUser.getId()))
                .map(bookService::createBook)
                .toList();

        List<Long> bookIdList = bookService.getBookListByUserId(newUser.getId())
                .stream()
                .map(BookDto::getId)
                .toList();
        log.info("Collected books id: {}", bookIdList);

        return UserBookResponse.builder().userId(id).booksIdList(bookIdList).build();
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        UserDto userDto = userService.getUserById(userId);
        log.info("Got user: {}", userDto);
        List<Long> bookIdList = bookService.getBookListByUserId(userId)
                .stream()
                .filter(Objects::nonNull)
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder().userId(userDto.getId()).booksIdList(bookIdList).build();
    }

    public UserDeleteResponse deleteUserWithBooks(Long userId) {
        log.info("Delete user and his books: user id {}", userId);
        List<Long> bookIdList = bookService.getBookListByUserId(userId)
                .stream()
                .filter(Objects::nonNull)
                .map(BookDto::getId)
                .peek(bookService::deleteBookById)
                .toList();
        log.info("Deleted books with user id: {}", userId);
        Boolean hasDeleted = userService.deleteUserById(userId);
        if (!hasDeleted) {
            return UserDeleteResponse.builder().errorMessage("Deletion error. User not found.").build();
        }
        log.info("Deleted user with id: {}", userId);
        return UserDeleteResponse.builder().message("User deleted!").build();
    }
}
