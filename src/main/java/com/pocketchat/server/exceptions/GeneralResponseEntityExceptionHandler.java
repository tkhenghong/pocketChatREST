package com.pocketchat.server.exceptions;

import com.pocketchat.server.exceptions.chat_message.ChatMessageNotFoundException;
import com.pocketchat.server.exceptions.conversation_group.ConversationGroupNotFoundException;
import com.pocketchat.server.exceptions.country_code.CountryCodeNotFoundException;
import com.pocketchat.server.exceptions.general.StringEmptyException;
import com.pocketchat.server.exceptions.mobile_number.MobileNumberNotFoundException;
import com.pocketchat.server.exceptions.multimedia.MultimediaNotFoundException;
import com.pocketchat.server.exceptions.otp.MaximumOTPVerificationAttemptReachedException;
import com.pocketchat.server.exceptions.otp.OTPNotFoundException;
import com.pocketchat.server.exceptions.otp.WrongOTPException;
import com.pocketchat.server.exceptions.settings.SettingsNotFoundException;
import com.pocketchat.server.exceptions.unread_message.UnreadMessageNotFoundException;
import com.pocketchat.server.exceptions.user.UserGoogleAccountIsAlreadyRegisteredException;
import com.pocketchat.server.exceptions.user.UserNotFoundException;
import com.pocketchat.server.exceptions.user_authentication.PasswordIncorrectException;
import com.pocketchat.server.exceptions.user_authentication.UsernameExistException;
import com.pocketchat.server.exceptions.user_authentication.UsernameNotFoundException;
import com.pocketchat.server.exceptions.user_privilege.UserPrivilegeNotFoundException;
import com.pocketchat.server.exceptions.user_role.UserRoleNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

// https://www.toptal.com/java/spring-boot-rest-api-error-handling
@ControllerAdvice
public class GeneralResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions(Exception ex, WebRequest request) {
        return generateResponseEntity(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle User defined Not Found exceptions
    @ExceptionHandler({
            ChatMessageNotFoundException.class,
            ConversationGroupNotFoundException.class,
            CountryCodeNotFoundException.class,
            MobileNumberNotFoundException.class,
            MultimediaNotFoundException.class,
            OTPNotFoundException.class,
            SettingsNotFoundException.class,
            UnreadMessageNotFoundException.class,
            UserNotFoundException.class,
            UsernameNotFoundException.class,
            UserPrivilegeNotFoundException.class,
            UserRoleNotFoundException.class
    })
    public ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception ex, WebRequest request) {
        return generateResponseEntity(ex, request, HttpStatus.NOT_FOUND);
    }

    // Handle Not Acceptable exceptions
    @ExceptionHandler({
            StringEmptyException.class,
            UserGoogleAccountIsAlreadyRegisteredException.class,
            UsernameExistException.class,
    })
    public ResponseEntity<ExceptionResponse> handleNotAcceptableExceptions(Exception ex, WebRequest request) {
        return generateResponseEntity(ex, request, HttpStatus.NOT_ACCEPTABLE);
    }

    // Handle Unauthorized exceptions
    @ExceptionHandler({
            PasswordIncorrectException.class,
            WrongOTPException.class,
    })
    public ResponseEntity<ExceptionResponse> handleUnauthorizedExceptions(Exception ex, WebRequest request) {
        return generateResponseEntity(ex, request, HttpStatus.UNAUTHORIZED);
    }

    // Handle Too Many Requests (considered as Too many times) exceptions
    @ExceptionHandler({
            MaximumOTPVerificationAttemptReachedException.class
    })
    public ResponseEntity<ExceptionResponse> handleTooManyRequestsExceptions(Exception ex, WebRequest request) {
        return generateResponseEntity(ex, request, HttpStatus.TOO_MANY_REQUESTS);
    }

    // Handle any MethodArgumentNotValidException, objects that have been labelled @NotNull, @NotBlank, @Valid etc...
    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        return generateResponseEntity(ex, request, status);
    }

    private ResponseEntity<ExceptionResponse> generateResponseEntity(Exception ex, WebRequest request, HttpStatus httpStatus) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .timestamp(new Date()) // https://www.baeldung.com/java-stacktrace-to-string
                .trace(ExceptionUtils.getStackTrace(ex))
                .build();

        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }
}
