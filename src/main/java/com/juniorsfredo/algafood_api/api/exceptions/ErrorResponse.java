package com.juniorsfredo.algafood_api.api.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private LocalDateTime dateTime;
    private Integer status;

    @Nullable
    private String type;

    private String title;

    @Nullable
    private String detail;

    private String userMessage;
    private List<Field> fields;

    @Getter
    public static class Field {
        private String name;
        private String message;

        public Field(String name, String message) {
            this.name = name;
            this.message = message;
        }
    }

    public ErrorResponse(LocalDateTime dateTime, Integer statusCode, String title) {
        this.dateTime = dateTime;
        this.status = statusCode;
        this.title = title;
    }

    public ErrorResponse(LocalDateTime dateTime, Integer statusCode, String title, String details, String userMessage) {
        this.dateTime = dateTime;
        this.status = statusCode;
        this.title = title;
        this.detail = details;
        this.userMessage = userMessage;
    }

    public void addFields(Field field) {
        this.fields.add(field);
    }
}
