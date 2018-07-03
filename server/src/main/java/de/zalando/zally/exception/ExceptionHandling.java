package de.zalando.zally.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.SpringAdviceTrait;


@ControllerAdvice
public class ExceptionHandling implements ProblemHandling, SpringAdviceTrait {

    @ExceptionHandler
    public ResponseEntity<Problem> handleMissingApiDefinitionException(MissingApiDefinitionException exception,
                                                                       NativeWebRequest request) {
        return create(HttpStatus.BAD_REQUEST, exception, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleUnaccessibleResourceUrlException(
            UnaccessibleResourceUrlException exception, NativeWebRequest request) {
        return create(exception.getHttpStatus(), exception, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleUnsufficientTimeIntervalParameterException(
            UnsufficientTimeIntervalParameterException exception, NativeWebRequest request) {
        return create(HttpStatus.BAD_REQUEST, exception, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleUnsufficientTimeIntervalParameterException(
            TimeParameterIsInTheFutureException exception, NativeWebRequest request) {
        return create(HttpStatus.BAD_REQUEST, exception, request);
    }
}
