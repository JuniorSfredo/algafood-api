package com.juniorsfredo.algafood_api.api.exceptions;

import lombok.Getter;

@Getter
public enum TypeErrorResponse {

    BUSINESS_ERROR("/business-error", "Violation of trading rules"),
    INVALID_DATA("invalid-data", "Invalid data"),
    SYSTEM_ERROR("/server-error", "Internal Server Error"),
    INCOMPREHENSIBLE_MESSAGE("/type-mismatch", "Incomprehensible message"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
    RESOURCE_NOT_FOUND( "/entity-not-found", "Resource Not Found!"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use!");

    private String uri;
    private String title;

    TypeErrorResponse(String path, String title ) {
        this.uri = "www.mydomain" + path;
        this.title = title;
    }
}
