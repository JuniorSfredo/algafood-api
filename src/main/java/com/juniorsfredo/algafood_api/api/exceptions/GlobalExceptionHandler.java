package com.juniorsfredo.algafood_api.api.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.juniorsfredo.algafood_api.domain.exceptions.*;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String GENERIC_MESSAGE_ERROR_USER = "Internal server error has occurred! Try again, if the problem persists, please contact your administrator!";
    private final LocaleResolver localeResolver;

    @Autowired
    MessageSource messageSource;

    public GlobalExceptionHandler(@Qualifier("localeResolver") LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = newResponseError(status.value(),
                                                       TypeErrorResponse.SYSTEM_ERROR.getTitle(),
                                                       TypeErrorResponse.SYSTEM_ERROR.getUri(),
                                                       GENERIC_MESSAGE_ERROR_USER,
                                                       GENERIC_MESSAGE_ERROR_USER
                                                      );

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest webRequest) {

        HttpStatus status = HttpStatus.CONFLICT;
        String message = ex.getMessage();
        ErrorResponse errorResponse = newResponseError(status.value(),
                                                       TypeErrorResponse.ENTITY_IN_USE.getTitle(),
                                                       TypeErrorResponse.ENTITY_IN_USE.getUri(),
                                                       message,
                                                       message
                                                      );

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, webRequest);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException (EntityNotFoundException ex, WebRequest webRequest) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = newResponseError(status.value(),
                                                       TypeErrorResponse.RESOURCE_NOT_FOUND.getTitle(),
                                                       TypeErrorResponse.RESOURCE_NOT_FOUND.getUri(),
                                                       ex.getMessage(),
                                                       GENERIC_MESSAGE_ERROR_USER
                                                      );

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, webRequest);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest webRequest) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = newResponseError(status.value(),
                                                       TypeErrorResponse.BUSINESS_ERROR.getTitle(),
                                                       TypeErrorResponse.BUSINESS_ERROR.getUri(),
                                                       ex.getMessage(),
                                                       GENERIC_MESSAGE_ERROR_USER);
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), status, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Throwable rootCause = ex.getRootCause();

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        }

        // UnrecognizedPropertyException / IgnoredPropertyException - throws
        else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }

        String details = "Invalid request body. Verify syntax error!";
        ErrorResponse errorResponse = newResponseError(status.value(),
                                                       TypeErrorResponse.INCOMPREHENSIBLE_MESSAGE.getTitle(),
                                                       TypeErrorResponse.INCOMPREHENSIBLE_MESSAGE.getUri(),
                                                       details,
                                                       GENERIC_MESSAGE_ERROR_USER
                                                      );
        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String message = String.format("Resource %s not found!", ex.getRequestURL());
        ErrorResponse errorResponse = newResponseError(status.value(),
                TypeErrorResponse.RESOURCE_NOT_FOUND.getTitle(),
                TypeErrorResponse.RESOURCE_NOT_FOUND.getUri(),
                message,
                message
        );

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatchException(ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        System.out.println(ex);

        if (body instanceof String) {
            body = newResponseError(statusCode.value(),
                                    (String) body,
                                    null,
                                   null,
                                    GENERIC_MESSAGE_ERROR_USER
                                   );
        } else if (body == null) {
            body = newResponseError(statusCode.value(),
                                    HttpStatus.valueOf(statusCode.value()).getReasonPhrase(),
                                    null,
                                    null,
                                    GENERIC_MESSAGE_ERROR_USER
                                   );
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();
        List<ErrorResponse.Field> fieldErrors = bindingResult.getFieldErrors()
                                                                .stream()
                                                                .map(fieldError -> {
                                                                    String messageError = messageSource.getMessage(fieldError, Locale.US);
                                                                    return new ErrorResponse.Field(fieldError.getField(), messageError);
                                                                } ).toList();
        String message = "Invalid request, there are null fields! Check errors and try again.";

        ErrorResponse errorResponse = newResponseError(status.value(),
                                                       TypeErrorResponse.INVALID_DATA.getTitle(),
                                                       TypeErrorResponse.INVALID_DATA.getUri(),
                                                       message,
                                                       message
                                                      );

        errorResponse.setFields(fieldErrors);

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String details = String.format("Invalid Request! Required type: '%s' for input '%s'.", ex.getRequiredType().getSimpleName(), ex.getValue());
        ErrorResponse errorResponse = newResponseError(status.value(),
                TypeErrorResponse.INVALID_PARAMETER.getTitle(),
                TypeErrorResponse.INVALID_PARAMETER.getUri(),
                details,
                GENERIC_MESSAGE_ERROR_USER
        );

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String details = String.format("The attribute '%s' not exist. Verify request and try again.", ex.getPropertyName());
        ErrorResponse errorResponse = newResponseError(status.value(),
                TypeErrorResponse.INCOMPREHENSIBLE_MESSAGE.getTitle(),
                TypeErrorResponse.INCOMPREHENSIBLE_MESSAGE.getUri(),
                details,
                GENERIC_MESSAGE_ERROR_USER
        );

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String path = ex.getPath().stream().map(JsonMappingException.Reference::getFieldName).collect(Collectors.joining("."));
        String details = String.format("Attribute '%s' contains invalid value '%s'. The type must be '%s'.", path, ex.getValue(), ex.getTargetType());
        ErrorResponse errorResponse = newResponseError(status.value(),
                                                       TypeErrorResponse.INCOMPREHENSIBLE_MESSAGE.getTitle(),
                                                       TypeErrorResponse.INCOMPREHENSIBLE_MESSAGE.getUri(),
                                                       details,
                                                       GENERIC_MESSAGE_ERROR_USER
                                                      );

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    private ErrorResponse newResponseError (Integer statusCode, String title, @Nullable  String type, @Nullable String details, String errorMessageUser) {
        LocalDateTime now = LocalDateTime.now();
        if (details == null) return new ErrorResponse(now, statusCode, title);
        return new ErrorResponse(now, statusCode, title, details, errorMessageUser);
    }

}
