package com.challenge.order.receiver.config;

import com.challenge.order.receiver.bean.incoming.response.GenericErrorResponse;
import com.challenge.order.receiver.enums.Error;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ApiResponse(responseCode = "400",
            description = "Invalid request",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorResponse.class)))
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public GenericErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // Find out info of all invalid parameters
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        LOGGER.error("Invalid input parameters detected. Error message: {}", errorMessage);

        return new GenericErrorResponse(Error.INVALID_PARAMS, errorMessage);
    }

    @ApiResponse(responseCode = "500",
            description = "System Error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorResponse.class)))
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    public GenericErrorResponse handleDataAccessException(DataAccessException e) {
        LOGGER.error("Data access exception occurred.", e);
        return new GenericErrorResponse(Error.DB_CRUD_ERROR);
    }

    @ApiResponse(responseCode = "500",
            description = "System Error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorResponse.class)))
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public GenericErrorResponse handleUncaughtException(Exception e) {
        LOGGER.error("Uncaught exception occurred.", e);
        return new GenericErrorResponse(Error.UNCAUGHT_EXCEPTION);
    }

}
