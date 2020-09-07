package com.pocketchat.utils.encryption;

import com.pocketchat.server.exceptions.encryption.EncryptionErrorException;
import com.pocketchat.models.enums.utils.encryption.DigitalSignatureAlgorithm;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.*;
import java.util.Arrays;
import java.util.Base64;

@Service
public class EncryptionUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String rsaPublicKeyDirectory;

    private String rsaPrivateKeyDirectory;

    private String rsaKeyFactoryAlgorithm;

    private String rsaCipherAlgorithmWithPadding;

    private String rsaCipherAlgorithmWithPadding2ForEncryption;

    private String rsaCipherAlgorithmWithPadding2ForDecryption;

    // Problem source: https://stackoverflow.com/questions/29922176/java-security-nosuchalgorithmexception-cannot-find-any-provider-supporting-rsa
    // NOTE: You need to specify provider for RSA/None/OAEPWITHSHA-256ANDMGF1PADDING.
    // https://www.baeldung.com/java-bouncy-castle
    private String rsaCipherAlgorithmProvider;

    private int rsaCipherKeySize;

    private String aesPrivateKeyDirectory;

    private String aesCipherAlgorithmWithPadding;

    private String aesCipherAlgorithmWithPadding2;

    private String aesDefaultSecret;

    private String aesSecretKeySpecAlgorithm;

    private String aesMessageDigestAlgorithm;

    private KeyFactory keyFactory;

    private PrivateKey rsaPrivateKey;

    private PublicKey rsaPublicKey;

    private SecretKeySpec aesSecretKeySpec;

    @Autowired
    public EncryptionUtil(
            @Value("${encryption.rsa.public.key.directory}") String rsaPublicKeyDirectory,
            @Value("${encryption.rsa.private.key.directory}") String rsaPrivateKeyDirectory,
            @Value("${encryption.rsa.key.factory.algorithm}") String rsaKeyFactoryAlgorithm,
            @Value("${encryption.rsa.cipher.algorithm.with.padding}") String rsaCipherAlgorithmWithPadding,
            @Value("${encryption.rsa.cipher.algorithm.with.padding2.for.encryption}") String rsaCipherAlgorithmWithPadding2ForEncryption,
            @Value("${encryption.rsa.cipher.algorithm.with.padding2.for.decryption}") String rsaCipherAlgorithmWithPadding2ForDecryption,
            @Value("${encryption.rsa.cipher.algorithm.provider}") String rsaCipherAlgorithmProvider,
            @Value("${encryption.rsa.cipher.key.size}") int rsaCipherKeySize,
            @Value("${encryption.aes.default.secret.key.spec.algorithm}") String aesSecretKeySpecAlgorithm,
            @Value("${encryption.aes.cipher.algorithm.with.padding}") String aesCipherAlgorithmWithPadding,
            @Value("${encryption.aes.cipher.algorithm.with.padding2}") String aesCipherAlgorithmWithPadding2,
            @Value("${encryption.aes.private.key.directory}") String aesPrivateKeyDirectory,
            @Value("${encryption.aes.default.secret}") String aesDefaultSecret,
            @Value("${encryption.aes.message.digest.algorithm}") String aesMessageDigestAlgorithm) {
        this.rsaPublicKeyDirectory = rsaPublicKeyDirectory;
        this.rsaPrivateKeyDirectory = rsaPrivateKeyDirectory;
        this.rsaKeyFactoryAlgorithm = rsaKeyFactoryAlgorithm;
        this.rsaCipherAlgorithmWithPadding = rsaCipherAlgorithmWithPadding;
        this.rsaCipherAlgorithmWithPadding2ForEncryption = rsaCipherAlgorithmWithPadding2ForEncryption;
        this.rsaCipherAlgorithmWithPadding2ForDecryption = rsaCipherAlgorithmWithPadding2ForDecryption;
        this.rsaCipherAlgorithmProvider = rsaCipherAlgorithmProvider;
        this.rsaCipherKeySize = rsaCipherKeySize;

        this.aesSecretKeySpecAlgorithm = aesSecretKeySpecAlgorithm;
        this.aesCipherAlgorithmWithPadding = aesCipherAlgorithmWithPadding;
        this.aesCipherAlgorithmWithPadding2 = aesCipherAlgorithmWithPadding2;
        this.aesPrivateKeyDirectory = aesPrivateKeyDirectory;
        this.aesDefaultSecret = aesDefaultSecret;
        this.aesMessageDigestAlgorithm = aesMessageDigestAlgorithm;

        try {
            // Why java.security.NoSuchProviderException No such provider: BC?
            // https://stackoverflow.com/questions/3711754/why-java-security-nosuchproviderexception-no-such-provider-bc
            Security.addProvider(new BouncyCastleProvider());

            setupRSAKeyFactory();
            setupRSAPrivateKey();
            setupRSAPublicKey();
            setupDefaultAESKey();
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
    }

    /************************************************RSA START************************************************/

    /**
     * Generate new RSA Key Pair.
     * https://stackoverflow.com/questions/21179959/rsa-signing-and-verifying-in-java
     * NOTE: This Key Pair algorithm is default algorithm provided by Java which is RSA/None/PKCS1Padding.
     * Built for dynamic RSA key pair between server and user. But end to end encryption it is not enough. Server are keeping the keys for the user, means the company itself, if know the method to reverse engineer it, they can break them.
     * Please store it somewhere safe in the database.
     */
    public KeyPair generateNewRSAKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(rsaCipherKeySize);

            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new EncryptionErrorException(noSuchAlgorithmException.getMessage());
        }
    }

    /**
     * Generate new RSA Key Pair provided by BouncyCastle.
     * NOTE: This Key Pair algorithm is default algorithm provided by BouncyCastle which is RSA/None/NoPadding.
     * Please store it somewhere safe in the database.
     */
    public KeyPair generateNewRSAKeyPairByBouncyCastle() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
            keyPairGenerator.initialize(rsaCipherKeySize, new SecureRandom());

            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
    }

    /**
     * Encrypt a plain text with PublicKey object loaded from a file of directory encryption.rsa.public.key.directory.
     *
     * @param plainText : Any normal text that you want to encrypt on.
     */
    public String encryptStringWithDefaultRSAPublicKey(String plainText) {
        String encoded;
        try {
            Cipher cipher = Cipher.getInstance(rsaCipherAlgorithmWithPadding, rsaCipherAlgorithmProvider);
            cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
            // Encrypt with RSA
            byte[] rsaEncrypted = cipher.doFinal(plainText.getBytes());
            // Encode with Base64
            encoded = Base64.getEncoder().encodeToString(rsaEncrypted);
        } catch (NoSuchPaddingException |
                NoSuchAlgorithmException |
                NoSuchProviderException |
                InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
        return encoded;
    }

    /**
     * Only use this if you have a PublicKey object with the correct encryption.aes.cipher.algorithm.with.padding.
     * Encrypt a plain text with PublicKey object loaded from a file, with directory from encryption.rsa.public.key.directory.
     *
     * @param plainText : Any normal text that you want to encrypt on.
     * @param publicKey : A PublicKey object get from this class itself, loaded from encryption.rsa.public.key.directory.
     */
    public String encryptStringWithDefaultRSAPublicKey(String plainText, PublicKey publicKey) {
        String encoded;
        try {
            Cipher cipher = Cipher.getInstance(rsaCipherAlgorithmWithPadding, rsaCipherAlgorithmProvider);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // Encrypt with RSA
            byte[] rsaEncrypted = cipher.doFinal(plainText.getBytes());

            // Encode with Base64
            encoded = Base64.getEncoder().encodeToString(rsaEncrypted);
        } catch (NoSuchPaddingException |
                NoSuchAlgorithmException |
                NoSuchProviderException |
                InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
        return encoded;
    }

    /**
     * Encrypt a plain text with given RSA PublicKey object made from the Java program itself.
     *
     * @param plainText : Any normal text that you want to encrypt on.
     * @param publicKey : A PublicKey object get from @return of the generateNewRSAKeyPair() itself.
     */
    public String encryptStringWithGivenRSAPublicKey(String plainText, PublicKey publicKey) {
        String encoded;
        try {
            Cipher cipher = Cipher.getInstance(rsaKeyFactoryAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // Encrypt with RSA
            byte[] rsaEncrypted = cipher.doFinal(plainText.getBytes());

            // Encode with Base64
            encoded = Base64.getEncoder().encodeToString(rsaEncrypted);
        } catch (NoSuchPaddingException |
                NoSuchAlgorithmException |
                InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
        return encoded;
    }

    /**
     * Decrypt a encoded Base64, RSA encrypted text with PrivateKey object from the file, with directory from encryption.rsa.private.key.directory
     *
     * @param base64EncodedEncryptedString: A base64 encoded, RSA encrypted String using file encryption.rsa.public.key.directory
     *                                      Note: privateKey variable is self loaded by using encryption.rsa.private.key.directory during startup.
     */
    public String decryptStringWithDefaultRSAPrivateKey(String base64EncodedEncryptedString) {
        String plainText;
        try {
            // SonarLint recommends*
            // https://rules.sonarsource.com/java/RSPEC-5542
            Cipher cipher = Cipher.getInstance(rsaCipherAlgorithmWithPadding, rsaCipherAlgorithmProvider);
            cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);

            // Decode Base64
            byte[] encryptedString = Base64
                    .getDecoder()
                    .decode(base64EncodedEncryptedString);

            // Decrypt string
            byte[] decrypted = cipher.doFinal(encryptedString);
            plainText = new String(decrypted);
        } catch (NoSuchPaddingException |
                InvalidKeyException |
                NoSuchAlgorithmException |
                NoSuchProviderException |
                BadPaddingException |
                IllegalBlockSizeException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }

        return plainText;
    }

    /**
     * Decrypt a base64 encoded, RSA encrypted string with given RSA Private Key.
     * NOTE: Your PrivateKey object MUST be made with the correct encryption.rsa.cipher.algorithm.with.padding.
     *
     * @param base64EncodedEncryptedString: A base64 encoded, RSA encrypted String using file encryption.rsa.public.key.directory.
     * @param privateKey                    : PrivateKey object, loaded by using encryption.rsa.private.key.directory during startup.
     */
    public String decryptStringWithGivenCustomRSAPrivateKey(String base64EncodedEncryptedString, PrivateKey privateKey) {
        String plainText;
        try {
            // SonarLint recommends*
            // https://rules.sonarsource.com/java/RSPEC-5542
            Cipher cipher = Cipher.getInstance(rsaCipherAlgorithmWithPadding, rsaCipherAlgorithmProvider);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // Decode Base64
            byte[] encryptedString = Base64
                    .getDecoder()
                    .decode(base64EncodedEncryptedString);

            // Decrypt string
            byte[] decrypted = cipher.doFinal(encryptedString);
            plainText = new String(decrypted);
        } catch (NoSuchPaddingException |
                InvalidKeyException |
                NoSuchAlgorithmException |
                NoSuchProviderException |
                BadPaddingException |
                IllegalBlockSizeException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }

        return plainText;
    }

    /**
     * Decrypt a encoded Base64, RSA encrypted text with PrivateKey object made from the Java program itself.
     *
     * @param base64EncodedEncryptedString : A base64 encoded, RSA encrypted String using @method generateNewRSAKeyPair().
     * @param privateKey                   : A PrivateKey object get from @return of the generateNewRSAKeyPair() itself.
     */
    public String decryptStringWithGivenRSAPrivateKey(String base64EncodedEncryptedString, PrivateKey privateKey) {
        String plainText;
        try {
            // SonarLint recommends*
            // https://rules.sonarsource.com/java/RSPEC-5542
            Cipher cipher = Cipher.getInstance(rsaKeyFactoryAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // Decode Base64
            byte[] encryptedString = Base64
                    .getDecoder()
                    .decode(base64EncodedEncryptedString);

            // Decrypt string
            byte[] decrypted = cipher.doFinal(encryptedString);
            plainText = new String(decrypted);
        } catch (NoSuchPaddingException |
                InvalidKeyException |
                NoSuchAlgorithmException |
                BadPaddingException |
                IllegalBlockSizeException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }

        return plainText;
    }

    /***
     * Encrypt text with given RSA Public Key with supported Java Cipher algorithm.
     * @param plainText: Text/String that you want to be encrypted with.
     * @param rsaPublicKey: Public Key created by KeyPair object. Remember generate KeyPair object using generateNewRSAKeyPair().
     * @param rsaCipherAlgorithmWithPadding: Supported Java Cipher algorithms.
     *                                     Refer to: https://docs.oracle.com/javase/7/docs/api/javax/crypto/Cipher.html
     * @return Base64 encoded, RSA encrypted String object.
     */
    public String encryptStringWithGivenPublicKeyAndAlgorithm(String plainText,
                                                              PublicKey rsaPublicKey,
                                                              String rsaCipherAlgorithmWithPadding) {
        Charset charSet = StandardCharsets.UTF_8;
        try {
            Cipher cipher = Cipher.getInstance(rsaCipherAlgorithmWithPadding);
            cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
            byte[] cipheredText = cipher.doFinal(plainText.getBytes(charSet));

            return Base64.getEncoder().encodeToString(cipheredText);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException |
                BadPaddingException |
                IllegalBlockSizeException |
                InvalidKeyException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
    }

    /***
     * Decrypt Base64 encoded, RSA encrypted text with given RSA Private Key with supported Java Cipher algorithm.
     * @param encryptedText: Text/String that you want to be encrypted with.
     * @param rsaPrivateKey: Private Key created by KeyPair object. Remember generate KeyPair object using generateNewRSAKeyPair().
     * @param rsaCipherAlgorithmWithPadding: Supported Java Cipher algorithms.
     *                                     Refer to: https://docs.oracle.com/javase/7/docs/api/javax/crypto/Cipher.html
     * @param algorithmParameterSpec : Specification that needs to be added into the Cipher, for example OAEPParameterSpec.
     * @return Plain text.
     */
    public String decryptStringWithGivenPrivateKeyAndAlgorithm(String encryptedText,
                                                               PrivateKey rsaPrivateKey,
                                                               String rsaCipherAlgorithmWithPadding,
                                                               AlgorithmParameterSpec algorithmParameterSpec) {
        Charset charSet = StandardCharsets.UTF_8;
        try {
            Cipher cipher = Cipher.getInstance(rsaCipherAlgorithmWithPadding);

            if (ObjectUtils.isEmpty(algorithmParameterSpec)) {
                cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey, algorithmParameterSpec);
            }

            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes, charSet);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException |
                InvalidKeyException |
                InvalidAlgorithmParameterException |
                BadPaddingException |
                IllegalBlockSizeException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
    }

    /**
     * Part of the concept of signing and verifying the message with different 3rd parties.
     * https://stackoverflow.com/questions/21179959/rsa-signing-and-verifying-in-java
     * https://www.baeldung.com/java-digital-signature
     * Sign the message using Private Key from RSA Key Pair, provided by BouncyCastle.
     * NOTE: You must give BouncyCastle generated RSA Key Pair, or else the following algorithm will give exceptions.
     *
     * @param message:    Signed message(or called signature) to be signed with RSA encryption provided by BouncyCastle.
     * @param privateKey: RSA Private Key provided by BouncyCastle.
     * @return A signed message, to be verified where needed.
     */
    public String signStringWithGivenPrivateKey(String message, PrivateKey privateKey) {
        try {
            Signature signature = generateSignatureType(DigitalSignatureAlgorithm.SHA1WithRSAWithBC);
            signature.initSign(privateKey, new SecureRandom());

            signature.update(message.getBytes());

            byte[] signatureBytes = signature.sign();

            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (NoSuchAlgorithmException |
                NoSuchProviderException |
                InvalidKeyException |
                SignatureException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
    }

    /**
     * Part of the concept of signing and verifying the message with different 3rd parties.
     * https://stackoverflow.com/questions/21179959/rsa-signing-and-verifying-in-java
     * https://www.baeldung.com/java-digital-signature
     * Verify the message using Public Key from RSA Key Pair, provided by BouncyCastle.
     * NOTE: You must give BouncyCastle generated RSA Key Pair, or else the following algorithm will give exceptions.
     * NOTE: You need to bring in the originalMessage in order to verify the message successfully.
     *
     * @param originalMessage: Original message (not signed/encrypted by RSA).
     * @param signedMessage:   Signed message(or called signature) to be verified with RSA encryption provided by BouncyCastle.
     * @param publicKey:       RSA Public Key provided by BouncyCastle.
     * @return A boolean value.
     * If true, the signed message verification is successful and signed message is correct match with the Public Key.
     * Else, vice versa.
     */
    public boolean verifyStringWithGivenPublicKey(String originalMessage, String signedMessage, PublicKey publicKey) {
        try {
            Signature signature = generateSignatureType(DigitalSignatureAlgorithm.SHA1WithRSAWithBC);
            signature.initVerify(publicKey);

            byte[] signedMessageBytes = Base64.getDecoder().decode(signedMessage);

            signature.update(originalMessage.getBytes());

            return signature.verify(signedMessageBytes);
        } catch (NoSuchAlgorithmException |
                InvalidKeyException |
                SignatureException |
                IllegalArgumentException |
                NoSuchProviderException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
    }

    /**
     * Part of the concept of signing and verifying the message with different 3rd parties.
     * https://stackoverflow.com/questions/21179959/rsa-signing-and-verifying-in-java
     * https://www.baeldung.com/java-digital-signature
     * Sign the message using Private Key from RSA Key Pair, provided by BouncyCastle.
     * NOTE: You must give BouncyCastle generated RSA Key Pair, or else the following algorithm will give exceptions.
     *
     * @param message:    Signed message to be signed with RSA encryption provided by BouncyCastle.
     * @param privateKey: RSA Private Key provided by BouncyCastle.
     * @param digitalSignatureAlgorithm: DigitalSignatureAlgorithm object to generate Signature object based on different
     *                                 algorithm requirements, provided by 3rd parties.
     * @return A signed message, to be verified where needed.
     */
    public String signStringWithGivenPrivateKeyAndAlgorithm(String message, PrivateKey privateKey, DigitalSignatureAlgorithm digitalSignatureAlgorithm) {
        try {
            Signature signature = generateSignatureType(digitalSignatureAlgorithm);
            signature.initSign(privateKey);

            signature.update(message.getBytes());

            byte[] signatureBytes = signature.sign();

            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (NoSuchAlgorithmException |
                NoSuchProviderException |
                InvalidKeyException |
                SignatureException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
    }

    /**
     * Part of the concept of signing and verifying the message with different 3rd parties.
     * https://stackoverflow.com/questions/21179959/rsa-signing-and-verifying-in-java
     * https://www.baeldung.com/java-digital-signature
     * Verify the message using Public Key from RSA Key Pair, provided by BouncyCastle.
     * NOTE: You must give BouncyCastle generated RSA Key Pair, or else the following algorithm will give exceptions.
     * NOTE: You need to bring in the originalMessage in order to verify the message successfully.
     *
     * @param originalMessage: Original message (not signed/encrypted by RSA).
     * @param signedMessage:   Signed message(or called signature) to be verified with RSA encryption provided by BouncyCastle.
     * @param publicKey:       RSA Public Key provided by BouncyCastle.
     * @param digitalSignatureAlgorithm: DigitalSignatureAlgorithm object to generate Signature object based on different
     *                                 algorithm requirements, provided by 3rd parties.
     * @return A boolean value.
     * If true, the signed message verification is successful and signed message is correct match with the Public Key.
     * Else, vice versa.
     */
    public boolean verifyStringWithGivenPublicKeyAndAlgorithm(String originalMessage, String signedMessage, PublicKey publicKey, DigitalSignatureAlgorithm digitalSignatureAlgorithm) {
        try {
            Signature signature = generateSignatureType(digitalSignatureAlgorithm);
            signature.initVerify(publicKey);

            byte[] signedMessageBytes = Base64.getDecoder().decode(signedMessage);

            signature.update(originalMessage.getBytes());

            return signature.verify(signedMessageBytes);
        } catch (NoSuchAlgorithmException |
                InvalidKeyException |
                SignatureException |
                IllegalArgumentException |
                NoSuchProviderException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
    }

    /**
     *
     * @param digitalSignatureAlgorithm: DigitalSignatureAlgorithm object to generate Signature object based on different
     *                                 algorithm requirements, provided by 3rd parties.
     * @return Initialized Signature object, ready for signing/verifying messages.
     * @throws NoSuchAlgorithmException: Exception when no such String value inputted into the Signature.getInstance(...).
     * @throws NoSuchProviderException: Exception when no such provider String value inputted into the Signature.getInstance(...).
     */
    private Signature generateSignatureType(DigitalSignatureAlgorithm digitalSignatureAlgorithm) throws NoSuchAlgorithmException, NoSuchProviderException {
        switch (digitalSignatureAlgorithm) {
            case NoneWithRSA:
                return Signature.getInstance("RSA");
            case SHA1WithRSA:
                return Signature.getInstance("SHA1withRSA");
            case SHA256WithRSA:
                return Signature.getInstance("SHA256WithRSA");
            case SHA1WithDSA:
                return Signature.getInstance("SHA1WithDSA");
            case MD5WithRSA:
                return Signature.getInstance("MD5WithRSA");
            case SHA1WithRSAWithBC:
                return Signature.getInstance("SHA1withRSA", "BC");
            default:
                throw new EncryptionErrorException("Unknown DigitalSignatureAlgorithm detected.");
        }
    }

    /************************************************RSA END************************************************/

    /************************************************AES START***********************************************/

    /***
     * https://howtodoinjava.com/java/java-security/java-aes-encryption-example/
     *
     * Encrypt with aesSecretKeySpec with a secret from encryption.aes.default.secret.
     *
     * @param plainText: Any plain text to be secured with.
     * @return Encrypted, Base64 encoded string with AES.
     */
    public String encryptWithDefaultAESKey(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(aesCipherAlgorithmWithPadding2);
            cipher.init(Cipher.ENCRYPT_MODE, aesSecretKeySpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
        } catch (InvalidKeyException |
                NoSuchAlgorithmException |
                NoSuchPaddingException |
                BadPaddingException |
                IllegalBlockSizeException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
    }

    /***
     * https://howtodoinjava.com/java/java-security/java-aes-encryption-example/
     * Decrypt base64 encoded and AES encrypted string with aesSecretKeySpec with a secret from encryption.aes.default.secret.
     *
     * @param plainText: Any plain text to be secured with.
     * @return Plain text.
     */
    public String decryptWithDefaultAESKey(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(aesCipherAlgorithmWithPadding2);
            cipher.init(Cipher.DECRYPT_MODE, aesSecretKeySpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(plainText)));
        } catch (InvalidKeyException |
                NoSuchAlgorithmException |
                NoSuchPaddingException |
                BadPaddingException |
                IllegalBlockSizeException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
    }

    /***
     * https://howtodoinjava.com/java/java-security/java-aes-encryption-example/
     *
     * Encrypt with aesSecretKeySpec with a secret from encryption.aes.default.secret.
     *
     * @param plainText: Any plain text to be secured with.
     * @param aesSecretKeySpec : An object that is used to perform AES encryption.
     *                     Remember to generate from @method generateAESSecretKeySpecWithSecret().
     * @return Encrypted, Base64 encoded string with AES.
     */
    public String encryptWithGivenAESKey(String plainText, SecretKeySpec aesSecretKeySpec) {
        try {
            Cipher cipher = Cipher.getInstance(aesCipherAlgorithmWithPadding2);
            cipher.init(Cipher.ENCRYPT_MODE, aesSecretKeySpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
        } catch (InvalidKeyException |
                NoSuchAlgorithmException |
                NoSuchPaddingException |
                BadPaddingException |
                IllegalBlockSizeException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
    }

    /***
     * https://howtodoinjava.com/java/java-security/java-aes-encryption-example/
     * Decrypt base64 encoded and AES encrypted string with aesSecretKeySpec with a secret from encryption.aes.default.secret.
     *
     * @param plainText: Any plain text to be secured with.
     * @param aesSecretKeySpec : An object that is used to perform AES decryption.
     *                     Remember to generate from @method generateAESSecretKeySpecWithSecret().
     * @return Plain text.
     */
    public String decryptWithGivenAESKey(String plainText, SecretKeySpec aesSecretKeySpec) {
        try {
            Cipher cipher = Cipher.getInstance(aesCipherAlgorithmWithPadding2);
            cipher.init(Cipher.DECRYPT_MODE, aesSecretKeySpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(plainText)));
        } catch (InvalidKeyException |
                NoSuchAlgorithmException |
                NoSuchPaddingException |
                BadPaddingException |
                IllegalBlockSizeException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
    }

    /***
     * https://howtodoinjava.com/java/java-security/java-aes-encryption-example/
     * Generate a SecretKeySpec object that can be used for AES encryption dynamically.
     *
     * @param secret: Plain text to be encrypted.
     * @return SecretKeySpec object that contains the key to encrypt/decrypt anything. Do not compromise the key.
     */
    public SecretKeySpec generateAESSecretKeySpecWithSecret(String secret) throws NoSuchAlgorithmException {
        SecretKeySpec aesSecretKeySpec;
        MessageDigest sha;
        byte[] key;

        key = secret.getBytes(StandardCharsets.UTF_8);
        sha = MessageDigest.getInstance(aesMessageDigestAlgorithm);
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        aesSecretKeySpec = new SecretKeySpec(key, aesSecretKeySpecAlgorithm);

        return aesSecretKeySpec;
    }

    /**********************************************AES END***********************************************/

    /**
     * Set up Key Factory for RSA for symmetric encryption.
     */
    private void setupRSAKeyFactory() throws NoSuchAlgorithmException {
        keyFactory = KeyFactory.getInstance(rsaKeyFactoryAlgorithm);
    }

    /**
     * Set up RSA Private Key for symmetric encryption.
     * Please read this forum first: https://stackoverflow.com/questions/11410770/load-rsa-public-key-from-file
     * <p>
     * NOTE: This is used to read a FIXED RSA private key from encryption.rsa.private.key.directory.
     * Refer to README2.txt to generate RSA key pair correctly.
     */
    private void setupRSAPrivateKey() throws IOException, InvalidKeySpecException {
        // DO NOT use new ClassPathResource(**DIRECTORY**).getFile(); anymore, especially if you're using Docker.
        // https://stackoverflow.com/questions/14876836/file-inside-jar-is-not-visible-for-spring
        InputStream fileInputStream = new ClassPathResource(rsaPrivateKeyDirectory).getInputStream();
        byte[] privateKeyBytes = fileInputStream.readAllBytes();

        KeySpec keySpec
                = new PKCS8EncodedKeySpec(privateKeyBytes);

        rsaPrivateKey = keyFactory.generatePrivate(keySpec);
    }

    /**
     * Set up RSA Public key for symmetric encryption.
     * Please read this forum first: https://stackoverflow.com/questions/11410770/load-rsa-public-key-from-file
     * <p>
     * NOTE: This is used to read a FIXED RSA private key from encryption.rsa.public.key.directory.
     * Refer to README2.txt to generate RSA key pair correctly.
     */
    private void setupRSAPublicKey() throws IOException, InvalidKeySpecException {
        logger.info("setupRSAPublicKey()");
        // DO NOT use new ClassPathResource(**DIRECTORY**).getFile(); anymore, especially if you're using Docker.
        // https://stackoverflow.com/questions/14876836/file-inside-jar-is-not-visible-for-spring
        InputStream fileInputStream = new ClassPathResource(rsaPublicKeyDirectory).getInputStream();
        byte[] publicKeyBytes = fileInputStream.readAllBytes();

        KeySpec keySpec
                = new X509EncodedKeySpec(publicKeyBytes);

        rsaPublicKey = keyFactory.generatePublic(keySpec);
    }

    /***
     * Setup default AES key for AES encryption with using encryption.aes.default.secret.
     * Reference: https://howtodoinjava.com/java/java-security/java-aes-encryption-example/
     */
    private void setupDefaultAESKey() throws NoSuchAlgorithmException {
        MessageDigest sha;
        byte[] key;

        key = aesDefaultSecret.getBytes(StandardCharsets.UTF_8);
        sha = MessageDigest.getInstance(aesMessageDigestAlgorithm);
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        aesSecretKeySpec = new SecretKeySpec(key, aesSecretKeySpecAlgorithm);
    }
}
