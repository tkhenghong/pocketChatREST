package com.pocketchat.server.exceptions;

import com.pocketchat.server.exceptions.chat_message.ChatMessageNotFoundException;
import com.pocketchat.server.exceptions.conversation_group.*;
import com.pocketchat.server.exceptions.conversation_group_block.BlockConversationGroupException;
import com.pocketchat.server.exceptions.conversation_group_block.ConversationGroupBlockNotFoundException;
import com.pocketchat.server.exceptions.conversation_group_block.UnblockConversationGroupException;
import com.pocketchat.server.exceptions.conversation_group_mute_notification.ConversationGroupMuteNotificationNotFoundException;
import com.pocketchat.server.exceptions.conversation_group_mute_notification.UnmuteConversationGroupException;
import com.pocketchat.server.exceptions.country_code.CountryCodeNotFoundException;
import com.pocketchat.server.exceptions.encryption.EncryptionErrorException;
import com.pocketchat.server.exceptions.file.UploadFileException;
import com.pocketchat.server.exceptions.general.StringEmptyException;
import com.pocketchat.server.exceptions.general.StringNotEmptyException;
import com.pocketchat.server.exceptions.mobile_number.MobileNumberNotFoundException;
import com.pocketchat.server.exceptions.multimedia.MultimediaNotFoundException;
import com.pocketchat.server.exceptions.otp.MaximumOTPVerificationAttemptReachedException;
import com.pocketchat.server.exceptions.otp.OTPNotFoundException;
import com.pocketchat.server.exceptions.otp.WrongOTPException;
import com.pocketchat.server.exceptions.password.PasswordPolicyNotMeetException;
import com.pocketchat.server.exceptions.settings.EditSettingsException;
import com.pocketchat.server.exceptions.settings.SettingsNotFoundException;
import com.pocketchat.server.exceptions.sms.InvalidSendSMSRequestException;
import com.pocketchat.server.exceptions.unread_message.UnreadMessageNotFoundException;
import com.pocketchat.server.exceptions.user.UserGoogleAccountIsAlreadyRegisteredException;
import com.pocketchat.server.exceptions.user.UserNotFoundException;
import com.pocketchat.server.exceptions.user_authentication.PasswordIncorrectException;
import com.pocketchat.server.exceptions.user_authentication.UsernameExistException;
import com.pocketchat.server.exceptions.user_authentication.UsernameNotFoundException;
import com.pocketchat.server.exceptions.user_contact.UserContactNotFoundException;
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
            UserContactNotFoundException.class,
            UsernameNotFoundException.class,
            UserPrivilegeNotFoundException.class,
            UserRoleNotFoundException.class,
            ConversationGroupAdminNotInMemberIdListException.class,
            ConversationGroupBlockNotFoundException.class,
            ConversationGroupMuteNotificationNotFoundException.class
    })
    public ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception ex, WebRequest request) {
        return generateResponseEntity(ex, request, HttpStatus.NOT_FOUND);
    }

    // Handle Not Acceptable exceptions
    @ExceptionHandler({
            StringEmptyException.class,
            StringNotEmptyException.class,
            UserGoogleAccountIsAlreadyRegisteredException.class,
            UsernameExistException.class,
            InvalidConversationGroupTypeException.class,
            EditSettingsException.class,
            BlockConversationGroupException.class,
            UnblockConversationGroupException.class,
            UnmuteConversationGroupException.class
    })
    public ResponseEntity<ExceptionResponse> handleNotAcceptableExceptions(Exception ex, WebRequest request) {
        return generateResponseEntity(ex, request, HttpStatus.NOT_ACCEPTABLE);
    }

    // Handle Unauthorized exceptions
    @ExceptionHandler({
            PasswordIncorrectException.class,
            WrongOTPException.class,
            ConversationGroupMemberPermissionException.class
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

    // Handle Bad Request Exceptions
    @ExceptionHandler({
            PasswordPolicyNotMeetException.class
    })
    public ResponseEntity<ExceptionResponse> handleBadRequestsExceptions(Exception ex, WebRequest request) {
        return generateResponseEntity(ex, request, HttpStatus.BAD_REQUEST);
    }

    // Handle Server Internal Error Exceptions
    @ExceptionHandler({
            EncryptionErrorException.class,
            InvalidPersonalConversationGroupException.class,
            WebSocketObjectConversionFailedException.class,
            InvalidSendSMSRequestException.class,
            UploadFileException.class
    })
    public ResponseEntity<ExceptionResponse> handleServerInternalErrorExceptions(Exception ex, WebRequest request) {
        return generateResponseEntity(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
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
