package com.pocketchat.utils.password;

import com.pocketchat.server.exceptions.conversation_group.ConversationGroupAdminNotInMemberIdListException;
import com.pocketchat.server.exceptions.password.PasswordPolicyNotMeetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.passay.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PasswordUtilTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${password.lower.case.character.length}")
    int lowerCaseCharacterLength = 1;

    @Value("${password.upper.case.character.length}")
    int upperCaseCharacterLength = 1;

    @Value("${password.digit.number.character.length}")
    int digitNumberCharacterLength = 1;

    @Value("${password.special.character.list}")
    String specialCharactersList = "!@#$%^&*()_+";

    @Value("${password.special.character.length}")
    int specialCharactersLength = 1;

    @Value("${password.minimum.length}")
    int minimumPasswordLength = 8;

    @Value("${password.maximum.length}")
    int maximumPasswordLength = 256;

    PasswordUtil passwordUtil;

    @Mock
    PasswordValidator passwordValidator;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setup() {
        passwordUtil = new PasswordUtil(
                passwordEncoder,
                lowerCaseCharacterLength,
                upperCaseCharacterLength,
                digitNumberCharacterLength,
                specialCharactersList,
                specialCharactersLength,
                minimumPasswordLength,
                maximumPasswordLength
        );
    }

    /**
     * To test a random generated password fulfills all minimum requirements setup by the server configuration.
     */
    @Test
    public void testGenerateRandomSecurePassword() {
        String password = passwordUtil.generateRandomSecurePassword();

        logger.info("password: {}", password);

        assertNotNull(password);
        assertEquals(password.length(), minimumPasswordLength);
        assertTrue(countUpperCase(password) > 0);
        assertTrue(countLowerCase(password) > 0);
        assertTrue(countSpecialCharacters(password) > 0);
    }

    /**
     * To test PasswordEncoder works properly or not.
     * Note: PasswordEncoder bean is configured within SecurityConfiguration.java file, with BCryptPasswordEncoder.
     */
    @Test
    public void testEncodeAndVerifyWithPasswordEncoder() {
        String randomString = UUID.randomUUID().toString();

        String encodedString = passwordUtil.encodePassword(randomString);

        boolean passwordMatches = passwordUtil.matchPassword(randomString, encodedString);

        assertNotNull(encodedString);
        assertTrue(passwordMatches);
    }

    /**
     * To test password validation when:
     * 1. Password meet with the password Policy correctly.
     * 2. There's no username within the password.
     */
    @Test
    public void testPasswordStrengthWhenPasswordFulfilledTheRequirements() {
        String username = UUID.randomUUID().toString();

        String password = passwordUtil.generateRandomSecurePassword();

        logger.info("password: {}", password);

        boolean passwordHasCorrectRequirements = passwordUtil.validatePasswordStrength(password, username);

        assertNotNull(password);
        assertTrue(passwordHasCorrectRequirements);
    }

    /**
     * To test password validation when:
     * 1. Password meet with the password Policy correctly.
     * 2. But there's username within the password.
     */
    @Test
    public void testPasswordStrengthButPasswordHasUsernameWithinIt() {
        String username = UUID.randomUUID().toString();

        String passwordWithCorrectRequirements = passwordUtil.generateRandomSecurePassword();

        String password = passwordWithCorrectRequirements + username;

        logger.info("password: {}", password);

        try {
            passwordUtil.validatePasswordStrength(password, username);
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(PasswordPolicyNotMeetException.class);
        }

        assertNotNull(password);
    }

    /**
     * To test password validation when:
     * 1. Password does not meet with the password Policy correctly.
     * 2. No username within the password.
     */
    @Test
    public void testPasswordStrengthButPasswordDoesNotMeetRequirements() {
        String username = UUID.randomUUID().toString();
        String password = "1234abcd";

        logger.info("password: {}", password);

        try {
            passwordUtil.validatePasswordStrength(password, username);
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(PasswordPolicyNotMeetException.class);
        }

        assertNotNull(password);
    }

    /**
     * Counts number of upper case characters in a string.
     * https://stackoverflow.com/questions/25224954
     *
     * @param string: A string.
     * @return number of upper case characters.
     */
    private static long countUpperCase(String string) {
        return string.codePoints().filter(c -> c >= 'A' && c <= 'Z').count();
    }

    /**
     * Counts number of lower case characters in a string.
     * https://stackoverflow.com/questions/25224954
     *
     * @param string: A string.
     * @return number of lower case characters.
     */
    private static long countLowerCase(String string) {
        return string.codePoints().filter(c -> c >= 'a' && c <= 'z').count();
    }

    /**
     * Counts number of digits in a string.
     * https://stackoverflow.com/questions/5564339
     *
     * @param string: A string.
     * @return number of digits.
     */
    private static long countDigits(String string) {
        return string.replaceAll("\\D", "").length();
    }

    /**
     * Counts number of special case characters in a string.
     * https://stackoverflow.com/questions/11573939
     *
     * @param string: A string.
     * @return number of special case characters.
     */
    private static int countSpecialCharacters(String string) {
        return string.length() - Math.toIntExact(countUpperCase(string))
                - Math.toIntExact(countLowerCase(string))
                - Math.toIntExact(countDigits(string));
    }
}
