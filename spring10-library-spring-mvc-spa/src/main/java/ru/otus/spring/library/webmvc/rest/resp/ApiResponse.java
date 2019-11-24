package ru.otus.spring.library.webmvc.rest.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse<T> {

    private int status;
    private Object result;
}
