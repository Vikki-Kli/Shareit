package ru.shareit.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NoSuchUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> objectNotFoundHandler(Exception e) {
        return Map.of("ошибка: ", e.getMessage());
    }

    @ExceptionHandler(AccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> accessDeniedHandler(Exception e) {
        return Map.of("ошибка: ", e.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            ValidationException.class,
            MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> deserializeAndValidationExceptionHandler(Exception e) {
        return Map.of("ошибка: ", "неправильно введены данные. Попробуйте еще раз");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> someExceptionHandler(Exception e) {
        System.out.println(e.getClass());
        System.out.println(e.getMessage());
        for(StackTraceElement i : e.getStackTrace()) System.out.println(i);
        return Map.of("ошибка: ", e.getMessage());
    }
}
