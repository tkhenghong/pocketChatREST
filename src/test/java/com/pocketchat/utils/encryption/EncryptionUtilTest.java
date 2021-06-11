package com.pocketchat.utils.encryption;

import com.pocketchat.models.enums.utils.encryption.DigitalSignatureAlgorithm;
import com.pocketchat.server.exceptions.encryption.EncryptionErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.MGF1ParameterSpec;
import java.util.Base64;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EncryptionUtilTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${encryption.rsa.public.key.directory}")
    private String rsaPublicKeyDirectory = "encryption/rsa/public_key.der";

    @Value("${encryption.rsa.private.key.directory}")
    private String rsaPrivateKeyDirectory = "encryption/rsa/private_key.der";

    @Value("${encryption.rsa.key.factory.algorithm}")
    private String rsaKeyFactoryAlgorithm = "RSA";

    @Value("${encryption.rsa.cipher.algorithm.with.padding}")
    private String rsaCipherAlgorithmWithPadding = "RSA/None/OAEPWITHSHA-256ANDMGF1PADDING";

    @Value("${encryption.rsa.cipher.algorithm.with.padding2.for.encryption}")
    private String rsaCipherAlgorithmWithPadding2ForEncryption = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";

    @Value("${encryption.rsa.cipher.algorithm.with.padding2.for.decryption}")
    private String rsaCipherAlgorithmWithPadding2ForDecryption = "RSA/ECB/OAEPPadding";

    @Value("${encryption.rsa.cipher.algorithm.provider}")
    private String rsaCipherAlgorithmProvider = "BC";

    @Value("${encryption.rsa.cipher.key.size}")
    private int rsaCipherKeySize = 1024; // Reduced size to allow faster testing.

    @Value("${encryption.aes.default.secret.key.spec.algorithm}")
    private String aesSecretKeySpecAlgorithm = "AES";

    @Value("${encryption.aes.cipher.algorithm.with.padding}")
    private String aesCipherAlgorithmWithPadding = "AES/GCM/NoPadding";

    @Value("${encryption.aes.cipher.algorithm.with.padding2}")
    private String aesCipherAlgorithmWithPadding2 = "AES/ECB/PKCS5Padding";

    @Value("${encryption.aes.private.key.directory}")
    private String aesPrivateKeyDirectory = "";

    @Value("${encryption.aes.default.secret}")
    private String aesDefaultSecret = "PocketChatAESTest";

    @Value("${encryption.aes.message.digest.algorithm}")
    private String aesMessageDigestAlgorithm = "SHA-1";

    private EncryptionUtil encryptionUtil;

    @BeforeEach
    void setup() {
        //if we don't call below, we will get NullPointerException
        MockitoAnnotations.openMocks(this);
        encryptionUtil = new EncryptionUtil(
                rsaPublicKeyDirectory,
                rsaPrivateKeyDirectory,
                rsaKeyFactoryAlgorithm,
                rsaCipherAlgorithmWithPadding,
                rsaCipherAlgorithmWithPadding2ForEncryption,
                rsaCipherAlgorithmWithPadding2ForDecryption,
                rsaCipherAlgorithmProvider,
                rsaCipherKeySize,
                aesSecretKeySpecAlgorithm,
                aesCipherAlgorithmWithPadding,
                aesCipherAlgorithmWithPadding2,
                aesPrivateKeyDirectory,
                aesDefaultSecret,
                aesMessageDigestAlgorithm
        );
    }

    /**
     * Test RSA encryption and decryption with Project default RSA algorithm.
     * Refer to encryption.rsa.cipher.algorithm.with.padding.
     */
    @Test
    void testDefaultRSAEncryption() {
        String testString = UUID.randomUUID().toString();

        String rsaEncryptedEncodedString = encryptionUtil.encryptStringWithDefaultRSAPublicKey(testString);

        logger.info("rsaEncryptedEncodedString: {}", rsaEncryptedEncodedString);

        String restoredText = encryptionUtil.decryptStringWithDefaultRSAPrivateKey(rsaEncryptedEncodedString);

        logger.info("restoredText: {}", restoredText);

        assertEquals(restoredText, testString);
    }

    /**
     * To test the RSA keys generated by the Java application can encrypt & decrypt the plain text successfully or not.
     */
    @Test
    void testRandomRSAKeysEncryption() {
        KeyPair keyPair = encryptionUtil.generateNewRSAKeyPair();

        String testString = UUID.randomUUID().toString();

        String rsaEncryptedEncodedString = encryptionUtil.encryptStringWithGivenRSAPublicKey(testString, keyPair.getPublic());

        String restoredText = encryptionUtil.decryptStringWithGivenRSAPrivateKey(rsaEncryptedEncodedString, keyPair.getPrivate());

        assertEquals(restoredText, testString);
    }

    /**
     * Hashing function: A function that transforms the string to randomized cipher text in one way. You're unable to
     * restore the text back to original self.
     * For example, SHA-1, MD5, SHA-256 and so forth.
     */

    /**
     * OEAP is a complicated security scheme implemented to use 2 hash functions. It is implemented to cover up possible
     * attacks that can be made to normal RSA encryption such as Broadcast Attack.
     *
     * OEAP with padding will improve the security of RSA keys, because each time the result value of the encryption
     * will be different.
     *
     * MGF1 as the mask generation function used in OEAP. Inside of MGF1 Spec you need to provide the algorithm of hash
     * function such as SHA-1, MD5, SHA-256 and so forth.
     *
     * ECB means Electronic Code Book, a mode of operation for a block cipher to encrypt every block of the plaintext
     * with predefined corresponding ciphertext value. Not suitable for plain text that is too small in length which
     * allows codebook attack.
     *
     * Bouncy Castle is one of the open source security provider for the OEAP solution.
     *
     * Read the documents below for understanding:
     * https://medium.com/blue-space/improving-the-security-of-rsa-with-oaep-e854a5084918
     * https://stackoverflow.com/questions/50298687/bouncy-castle-vs-java-default-rsa-with-oaep
     * https://stackoverflow.com/questions/27885726/encryption-mode-and-padding/27886397#27886397
     * https://searchsecurity.techtarget.com/definition/Electronic-Code-Book
     * https://stackoverflow.com/questions/49483281
     *
     * Java default RSA key algorithm is RSA/None/PKCS1Padding.
     * BouncyCastle default RSA key algorithm is RSA/None/NoPadding.
     */

    /**
     * Try use other RSA Java providers with OEAP, SHA 256 and MGF1 Padding.
     * NOTE: RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING means RSA encryption with ECB mode, OAEP mode, SHA-256 as message digest and MGF1 as padding
     * Referred from: https://stackoverflow.com/questions/32161720/breaking-down-rsa-ecb-oaepwithsha-256andmgf1padding
     * Encrypted string is encoded with Base64 from package java.util.
     * https://stackoverflow.com/questions/43325310/rsa-encyrption-converting-between-bytes-array-and-string/43325830#43325830
     */
    @Test
    void testSecureRSAAlgorithmEncryption() {
        String randomString = UUID.randomUUID().toString();
        logger.info("randomString: {}", randomString);

        // OAEPParameterSpec configuration. It is used for decryption of encrypted string using algorithm RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING.
        String mdName = "SHA-256"; // md means MessageDigest.
        String mgfName = "MGF1";
        String mgfParameterMdName = "SHA-1";

        KeyPair rsaKeyPair = encryptionUtil.generateNewRSAKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) rsaKeyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) rsaKeyPair.getPrivate();

        String encryptedText = encryptionUtil.encryptStringWithGivenPublicKeyAndAlgorithm(randomString, rsaPublicKey,
                rsaCipherAlgorithmWithPadding2ForEncryption);

        OAEPParameterSpec oaepParams = new OAEPParameterSpec(mdName, mgfName, new MGF1ParameterSpec(mgfParameterMdName), PSource.PSpecified.DEFAULT);

        String decryptedString = encryptionUtil.decryptStringWithGivenPrivateKeyAndAlgorithm(encryptedText, rsaPrivateKey,
                rsaCipherAlgorithmWithPadding2ForDecryption, oaepParams);

        logger.info("decryptedString: {}", decryptedString);

        assertEquals(decryptedString, randomString);
    }

    /**
     * To test RSA sign and verify process using default RSA algorithm provided by BouncyCastle.
     */
    @Test
    void testRSASignAndVerifyProvidedByBouncyCastle() {
        String randomString = UUID.randomUUID().toString();

        KeyPair rsaKeyPair = encryptionUtil.generateNewRSAKeyPairByBouncyCastle();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) rsaKeyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) rsaKeyPair.getPrivate();

        String signedMessage = encryptionUtil.signStringWithGivenPrivateKey(randomString, rsaPrivateKey);

        logger.info("signedMessage: {}", signedMessage);

        boolean signedMessageIsValid = encryptionUtil.verifyStringWithGivenPublicKey(randomString, signedMessage, rsaPublicKey);

        assertNotNull(signedMessage);
        assertNotEquals(signedMessage, randomString);
        assertTrue(signedMessageIsValid);
    }

    /**
     * To test RSA sign and verify process if use any plain message in verification process.
     */
    @Test
    void testRSASignAndVerifyProvidedByBouncyCastleButSuddenUsePlainMessageToVerify() {
        String randomString = UUID.randomUUID().toString();

        KeyPair rsaKeyPair = encryptionUtil.generateNewRSAKeyPairByBouncyCastle();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) rsaKeyPair.getPublic();

        String wrongSignedMessage = UUID.randomUUID().toString(); // Intended wrong message

        logger.info("wrongSignedMessage: {}", wrongSignedMessage);

        try {
            encryptionUtil.verifyStringWithGivenPublicKey(randomString, wrongSignedMessage, rsaPublicKey);
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(EncryptionErrorException.class);
        }

        assertNotNull(wrongSignedMessage);
        assertNotEquals(wrongSignedMessage, randomString);
    }

    /**
     * To test RSA sign and verify process if use any wrong Base64 encoded message in the verification process.
     */
    @Test
    void testRSASignAndVerifyProvidedByBouncyCastleButWrongSignedMessage() {
        String randomString = UUID.randomUUID().toString();

        KeyPair rsaKeyPair = encryptionUtil.generateNewRSAKeyPairByBouncyCastle();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) rsaKeyPair.getPublic();

        String randomWrongMessage = UUID.randomUUID().toString(); // Intended wrong message
        String wrongSignedMessage = Base64.getEncoder().encodeToString(randomWrongMessage.getBytes());

        logger.info("wrongSignedMessage: {}", wrongSignedMessage);

        boolean signedMessageIsValid = encryptionUtil.verifyStringWithGivenPublicKey(randomString, wrongSignedMessage, rsaPublicKey);

        assertNotNull(wrongSignedMessage);
        assertNotEquals(wrongSignedMessage, randomString);
        assertFalse(signedMessageIsValid);
    }

    /**
     * To test the sign and verify process if use Java default security provider with algorithm(RSA/None/PKCS1Padding)
     * to sign and verify messages.
     * NOTE: The test reveals even if you use default Java Security provider to generate Key Pair and use them in sign/verify
     * will still make the correct result.
     */
    @Test
    void testRSASignAndVerifyProvidedByBouncyCastleButUseDifferentSecurityProvider() {
        String randomString = UUID.randomUUID().toString();

        KeyPair differentRSAKeyPair = encryptionUtil.generateNewRSAKeyPair(); // Using default method to generate RSA Key Pair.
        RSAPublicKey rsaPublicKey = (RSAPublicKey) differentRSAKeyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) differentRSAKeyPair.getPrivate();

        String signedMessage = encryptionUtil.signStringWithGivenPrivateKey(randomString, rsaPrivateKey);

        logger.info("signedMessage: {}", signedMessage);

        boolean signedMessageIsValid = encryptionUtil.verifyStringWithGivenPublicKey(randomString, signedMessage, rsaPublicKey);

        assertNotNull(signedMessage);
        assertNotEquals(signedMessage, randomString);
        assertTrue(signedMessageIsValid);
    }

    @Test
    void testRSASignAndVerifyProvidedByBouncyCastleButUseWrongPublicKeyToVerifyMessage() {
        String randomString = UUID.randomUUID().toString();

        KeyPair rsaKeyPair = encryptionUtil.generateNewRSAKeyPairByBouncyCastle();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) rsaKeyPair.getPrivate();

        KeyPair anotherRSAKeyPair = encryptionUtil.generateNewRSAKeyPair();
        RSAPublicKey wrongRSAPublicKey = (RSAPublicKey) anotherRSAKeyPair.getPublic();

        String signedMessage = encryptionUtil.signStringWithGivenPrivateKey(randomString, rsaPrivateKey);

        logger.info("signedMessage: {}", signedMessage);

        boolean signedMessageIsValid = encryptionUtil.verifyStringWithGivenPublicKey(randomString, signedMessage, wrongRSAPublicKey);

        assertNotNull(signedMessage);
        assertNotEquals(signedMessage, randomString);
        assertFalse(signedMessageIsValid);
    }

    /**
     * To test RSA message signing and verification with other mentioned RSA algorithm by Baeldung.
     */
    @Test
    void testRSASignAndVerifyWithCustom() {
        String randomString = UUID.randomUUID().toString();

        KeyPair rsaKeyPair = encryptionUtil.generateNewRSAKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) rsaKeyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) rsaKeyPair.getPrivate();

        DigitalSignatureAlgorithm digitalSignatureAlgorithm = DigitalSignatureAlgorithm.SHA256WithRSA;

        String signedMessage = encryptionUtil.signStringWithGivenPrivateKeyAndAlgorithm(randomString, rsaPrivateKey, digitalSignatureAlgorithm);

        boolean signedMessageIsValid = encryptionUtil.verifyStringWithGivenPublicKeyAndAlgorithm(randomString, signedMessage, rsaPublicKey, digitalSignatureAlgorithm);

        assertNotNull(signedMessage);
        assertNotEquals(signedMessage, randomString);
        assertTrue(signedMessageIsValid);
    }

    /**
     * To test with AES Encryption with Project's default AES algorithm.
     * Refer to encryption.aes.cipher.algorithm.with.padding2.
     */
    @Test
    void testDefaultAESEncryption() {
        String randomString = UUID.randomUUID().toString();

        String rsaEncryptedEncodedString = encryptionUtil.encryptWithDefaultAESKey(randomString);

        String restoredText = encryptionUtil.decryptWithDefaultAESKey(rsaEncryptedEncodedString);

        logger.info("restoredText: {}", restoredText);

        assertEquals(restoredText, randomString);
    }

    /**
     * To test default AES encryption with Project default AES encryption algorithm.
     * Refer to encryption.aes.default.secret.key.spec.algorithm and encryption.aes.message.digest.algorithm
     *
     * @throws NoSuchAlgorithmException: is thrown when encryption.aes.default.secret.key.spec.algorithm is invalid.
     */
    @Test
    void testGivenAESEncryption() throws NoSuchAlgorithmException {
        String randomSecret = UUID.randomUUID().toString();
        String randomString = UUID.randomUUID().toString();

        SecretKeySpec aesSecretKeySpec = encryptionUtil.generateDefaultAESSecretKeySpec(randomSecret);

        String rsaEncryptedEncodedString = encryptionUtil.encryptWithGivenAESKey(randomString, aesSecretKeySpec);

        String restoredText = encryptionUtil.decryptWithGivenAESKey(rsaEncryptedEncodedString, aesSecretKeySpec);

        logger.info("restoredText: {}", restoredText);

        assertEquals(restoredText, randomString);
    }

    /**
     * TODO: Test Crypto AES Encryption and Decryption.
     *
     * @throws NoSuchPaddingException             is thrown when padding to be used in encryption is invalid.
     * @throws NoSuchAlgorithmException           is thrown when algorithm to be used in encryption is invalid.
     * @throws InvalidAlgorithmParameterException is thrown when algorithm parameter to be used in encryption is invalid.
     * @throws InvalidKeyException                is thrown when key to be used in encryption is invalid.
     * @throws BadPaddingException                is thrown when invalid padding in Cipher object is used in encryption.
     * @throws IllegalBlockSizeException          is thrown when invalid block size Cipher is used in encryption.
     */
    @Test
    void testCryptoAESDecryption() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
//        String secret = "René Über";
//        String cipherText = "U2FsdGVkX1+tsmZvCEFa/iGeSA0K7gvgs9KXeZKwbCDNCs2zPo+BXjvKYLrJutMK+hxTwl/hyaQLOaD7LLIRo2I5fyeRMPnroo6k8N9uwKk=";
//
//        byte[] cipherData = Base64.getDecoder().decode(cipherText);
//        byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);
//
//        MessageDigest md5 = MessageDigest.getInstance("MD5");
//        final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
//        SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
//        IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);
//
//        byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
//        Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
//        byte[] decryptedData = aesCBC.doFinal(encrypted);
//        String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);
//
//        System.out.println(decryptedText);
    }
}
