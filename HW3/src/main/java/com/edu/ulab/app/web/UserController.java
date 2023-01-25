package com.edu.ulab.app.web;

import com.edu.ulab.app.exception.NoUserFoundException;
import com.edu.ulab.app.facade.UserDataFacade;
import com.edu.ulab.app.web.constant.WebConstant;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookDeleteWebResponse;
import com.edu.ulab.app.web.response.UserBookResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;

import static com.edu.ulab.app.web.constant.WebConstant.REQUEST_ID_PATTERN;
import static com.edu.ulab.app.web.constant.WebConstant.RQID;

@Slf4j
@RestController
@RequestMapping(value = WebConstant.VERSION_URL + "/user",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserDataFacade userDataFacade;

    public UserController(UserDataFacade userDataFacade) {
        this.userDataFacade = userDataFacade;
    }

    @PostMapping(value = "/create")
    @Operation(summary = "Create user book row.",
            responses = {
                    @ApiResponse(description = "User book",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookResponse.class)))})
    public UserBookResponse createUserWithBooks(@RequestBody UserBookRequest request,
                                   @RequestHeader(RQID) @Pattern(regexp = REQUEST_ID_PATTERN) final String requestId) {
        UserBookResponse response = userDataFacade.createUserWithBooks(request);
        log.info("Response with created user and his books: {}", response);
        return response;
    }

    @PutMapping(value = "/update")
    @Operation(summary = "Update user book row.",
            responses = {
                    @ApiResponse(description = "User book",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookResponse.class)))})
    public UserBookResponse updateUserWithBooks(@RequestBody UserBookRequest request) {
        UserBookResponse response;
        try {
            response = userDataFacade.updateUserWithBooks(request);
        } catch (NoUserFoundException e) {
            response = new UserBookResponse(null, null);
            response.setErrorMessage("No such user!");
        }
        log.info("Response with updated user and his books: {}", response);
        return response;
    }

    @GetMapping(value = "/get/{userId}")
    @Operation(summary = "Get user book row.",
            responses = {
                    @ApiResponse(description = "User book",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookResponse.class)))})
    public UserBookResponse getUserWithBooks(@PathVariable String userId) {
        UserBookResponse response;
        try {
            response = userDataFacade.getUserWithBooks(userId);
        } catch (NoUserFoundException e) {
            response = new UserBookResponse(null, null);
            response.setErrorMessage("No such user!");
        }
        log.info("Response with user and his books: {}", response);
        return response;
    }

    @DeleteMapping(value = "/delete/{userId}")
    @Operation(summary = "Delete user book row.",
            responses = {
                    @ApiResponse(description = "Message",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookDeleteWebResponse.class)))})
    public UserBookDeleteWebResponse deleteUserWithBooks(@PathVariable String userId) {
        log.info("Delete user and his books: userId {}", userId);
        Boolean success = userDataFacade.deleteUserWithBooks(userId);
        UserBookDeleteWebResponse response;
        if (success) {
            response = UserBookDeleteWebResponse.builder().message("User deleted").build();
        } else {
            response = new UserBookDeleteWebResponse(null);
            response.setErrorMessage("Deletion error");
        }
        return response;
    }
}