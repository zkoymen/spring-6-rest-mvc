package guru.springframework.spring6restmvc.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {


    // Enriching the content to get more explanations
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException exception) {



        List errorList = exception.getFieldErrors().stream()
                .map(fieldError ->  {
                    Map<String, String> errorMap = new HashMap<>();

                    // Just taking the necessary info not too much
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());

        return  ResponseEntity.badRequest().body(errorList);
    }
}
