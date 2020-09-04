package com.pocketchat.utils.password;

import com.pocketchat.server.exceptions.password.PasswordPolicyNotMeetException;
import org.passay.CharacterData;
import org.passay.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.passay.IllegalCharacterRule.ERROR_CODE;

// Encryption used for password from backend app to DB: Bcrypt
@Service
public class PasswordUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PasswordEncoder passwordEncoder;

    private final PasswordGenerator passwordGenerator;

    private PasswordValidator passwordValidator;

    private final int lowerCaseCharacterLength;

    private final int upperCaseCharacterLength;

    private final int digitNumberCharacterLength;

    private final String specialCharactersList;

    private final int specialCharactersLength;

    private final int minimumPasswordLength;

    private final int maximumPasswordLength;

    private CharacterRule lowerCaseRule;

    private CharacterRule upperCaseRule;

    private CharacterRule digitRule;

    private CharacterRule specialCharacterRule;

    private LengthRule lengthRule;

    private UsernameRule usernameRule;

    @Autowired
    PasswordUtil(PasswordEncoder passwordEncoder,
                 @Value("${password.lower.case.character.length}") int lowerCaseCharacterLength,
                 @Value("${password.upper.case.character.length}") int upperCaseCharacterLength,
                 @Value("${password.digit.number.character.length}") int digitNumberCharacterLength,
                 @Value("${password.special.character.list}") String specialCharactersList,
                 @Value("${password.special.character.length}") int specialCharactersLength,
                 @Value("${password.minimum.length}") int minimumPasswordLength,
                 @Value("${password.maximum.length}") int maximumPasswordLength) {
        this.passwordEncoder = passwordEncoder;
        this.lowerCaseCharacterLength = lowerCaseCharacterLength;
        this.upperCaseCharacterLength = upperCaseCharacterLength;
        this.digitNumberCharacterLength = digitNumberCharacterLength;
        this.specialCharactersList = specialCharactersList;
        this.specialCharactersLength = specialCharactersLength;
        this.minimumPasswordLength = minimumPasswordLength;
        this.maximumPasswordLength = maximumPasswordLength;

        passwordGenerator = new PasswordGenerator();
        generatePasswordRules();
        generatePasswordValidator();
    }

    public String generateRandomSecurePassword() {
        return passwordGenerator.generatePassword(minimumPasswordLength, specialCharacterRule, lowerCaseRule, upperCaseRule, digitRule);
    }

    public String encodePassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    // NOTE: No decodePassword method, because PasswordEncoder is handled by Spring. You can only encode a password and
    // match a plain password with encodedPassword. Encryption and decryption algorithm is handled by Spring without
    // being exposed directly to the developers.

    public boolean matchPassword(String plainPassword, String encodedPasswordFromDB) {
        return passwordEncoder.matches(plainPassword, encodedPasswordFromDB);
    }

    public boolean validatePasswordStrength(String plainPassword, String username) {
        PasswordData passwordData = new PasswordData(plainPassword);
        passwordData.setUsername(username);
        RuleResult ruleResult = passwordValidator.validate(passwordData);

        if (!ruleResult.isValid()) {
            getPasswordValidationErrorDetails(ruleResult);
        }

        return ruleResult.isValid();
    }

    private void generatePasswordRules() {
        createLowerCaseCharacterRule();
        createUpperCaseCharacterRule();
        createDigitNumberRule();
        createSpecialCharacterRule();
        createLengthRule();
        createUsernameRule();
    }

    private void generatePasswordValidator() {
        passwordValidator = new PasswordValidator(lowerCaseRule, upperCaseRule, digitRule, specialCharacterRule, lengthRule, usernameRule);
    }

    private void createLowerCaseCharacterRule() {
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(lowerCaseCharacterLength);
    }

    private void createUpperCaseCharacterRule() {
        upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase);
        upperCaseRule.setNumberOfCharacters(upperCaseCharacterLength);
    }

    private void createDigitNumberRule() {
        digitRule = new CharacterRule(EnglishCharacterData.Digit);
        digitRule.setNumberOfCharacters(digitNumberCharacterLength);
    }

    private void createSpecialCharacterRule() {
        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE; // If added any special characters that doesn't accepted by the self defined characters. IllegalCharacterRule will be thrown
            }

            public String getCharacters() {
                return specialCharactersList;
            }
        };

        specialCharacterRule = new CharacterRule(specialChars);
        specialCharacterRule.setNumberOfCharacters(specialCharactersLength);
    }

    private void createLengthRule() {
        lengthRule = new LengthRule(minimumPasswordLength, maximumPasswordLength);
    }

    private void createUsernameRule() {
        usernameRule = new UsernameRule();
    }

    /**
     * Send error details back to frontend. Front end is able to deserialize the message and read it properly.
     * {
     * errorCode: string,
     * details: [
     * {key: value},
     * {key: value},
     * {key: value},
     * .....
     * ]
     * }
     */
    private void getPasswordValidationErrorDetails(RuleResult ruleResult) {
        List<RuleResultDetail> ruleResultDetails = ruleResult.getDetails();

        AtomicReference<String> passwordValidationErrorMessage = new AtomicReference<>("");

        ruleResultDetails.forEach(ruleResultDetail -> {
            String validationErrorMessage = "{ errorCode: \"" + ruleResultDetail.getErrorCode() + "\", " +
                    "details: " + printErrorDetails(ruleResultDetail.getParameters()) +
                    " }";

            passwordValidationErrorMessage.set(passwordValidationErrorMessage + validationErrorMessage);
        });

        throw new PasswordPolicyNotMeetException(passwordValidationErrorMessage.get());
    }

    private String printErrorDetails(Map<String, Object> stringObjectMap) {
        AtomicReference<String> errorDetails = new AtomicReference<>("[");
        stringObjectMap.forEach((String key, Object value) -> {
            errorDetails.set(errorDetails.get() + "{ \"" + key + "\": \"" + value.toString() + "\" },");
        });

        errorDetails.set(errorDetails.get().substring(0, errorDetails.get().length() - 1)); // Remove last ,
        errorDetails.set(errorDetails.get() + "]");

        return errorDetails.get();
    }
}
