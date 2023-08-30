package ru.shareit.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> sqlExceptionHandler(Exception e) {
        if (e.getClass() == DataIntegrityViolationException.class && e.getMessage().contains("повторяющееся значение ключа нарушает ограничение уникальности \"unique_name_for_user\""))
            return Map.of("ошибка: ", "У вас уже есть вещь с таким именем");
        else if (e.getClass() == DataIntegrityViolationException.class && e.getMessage().contains("повторяющееся значение ключа нарушает ограничение уникальности \"unique_email\""))
            return Map.of("ошибка: ", "Эта почта уже используется");
        else return Map.of("ошибка: ", e.getMessage());
    }

    @ExceptionHandler({NoSuchUserException.class, NoSuchItemException.class, EntityNotFoundException.class})
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
