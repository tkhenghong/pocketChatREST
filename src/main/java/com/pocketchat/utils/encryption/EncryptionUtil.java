package com.pocketchat.utils.encryption;

import com.pocketchat.server.exceptions.encryption.EncryptionErrorException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class EncryptionUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String rsaPublicKeyDirectory;

    private String rsaPrivateKeyDirectory;

    private String aesPrivateKeyDirectory;

    private String rsaCipherAlgorithmWithPadding;

    private String aesCipherAlgorithmWithPadding;

    // Problem source: https://stackoverflow.com/questions/29922176/java-security-nosuchalgorithmexception-cannot-find-any-provider-supporting-rsa
    // NOTE: You need to specify provider for RSA/None/OAEPWITHSHA-256ANDMGF1PADDING.
    // https://www.baeldung.com/java-bouncy-castle
    private String rsaCipherAlgorithmProvider;

    private int rsaCipherKeySize;

    private KeyFactory keyFactory;

    private PrivateKey privateKey;

    private PublicKey publicKey;

    @Autowired
    public EncryptionUtil(
            @Value("${encryption.rsa.public.key.directory}") String rsaPublicKeyDirectory,
            @Value("${encryption.rsa.private.key.directory}") String rsaPrivateKeyDirectory,
            @Value("${encryption.aes.private.key.directory}") String aesPrivateKeyDirectory,
            @Value("${encryption.rsa.cipher.algorithm.with.padding}") String rsaCipherAlgorithmWithPadding,
            @Value("${encryption.rsa.cipher.algorithm.provider}") String rsaCipherAlgorithmProvider,
            @Value("${encryption.aes.cipher.algorithm.with.padding}") String aesCipherAlgorithmWithPadding,
            @Value("${encryption.rsa.cipher.key.size}") int rsaCipherKeySize) {
        this.rsaPublicKeyDirectory = rsaPublicKeyDirectory;
        this.rsaPrivateKeyDirectory = rsaPrivateKeyDirectory;
        this.aesPrivateKeyDirectory = aesPrivateKeyDirectory;
        this.rsaCipherAlgorithmWithPadding = rsaCipherAlgorithmWithPadding;
        this.rsaCipherAlgorithmProvider = rsaCipherAlgorithmProvider;
        this.aesCipherAlgorithmWithPadding = aesCipherAlgorithmWithPadding;
        this.rsaCipherKeySize = rsaCipherKeySize;

        try {
            // Why java.security.NoSuchProviderException No such provider: BC?
            // https://stackoverflow.com/questions/3711754/why-java-security-nosuchproviderexception-no-such-provider-bc
            Security.addProvider(new BouncyCastleProvider());

            setupRSAKeyFactory();
            setupRSAPrivateKey();
            setupRSAPublicKey();
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
    }

    // TODO: May need to encrypt/decrypt texts with RSA encryption by researching the following link:
    // https://stackoverflow.com/questions/32161720/breaking-down-rsa-ecb-oaepwithsha-256andmgf1padding

    /**
     * Generate new RSA Key Pair.
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
     * Encrypt a plain text with PublicKey object loaded from a file of directory encryption.rsa.public.key.directory.
     * @param plainText : Any normal text that you want to encrypt on.
     */
    public String encryptStringWithSpecialRSABase64Encoded(String plainText) {
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
     * Only use this if you have a PublicKey object with the correct encryption.aes.cipher.algorithm.with.padding.
     * Encrypt a plain text with PublicKey object loaded from a file, with directory from encryption.rsa.public.key.directory.
     * @param plainText : Any normal text that you want to encrypt on.
     * @param publicKey : A PublicKey object get from this class itself, loaded from encryption.rsa.public.key.directory.
     */
    public String encryptStringWithSpecialRSABase64Encoded(String plainText, PublicKey publicKey) {
        String encoded;
        try {
            Cipher cipher = Cipher.getInstance(aesCipherAlgorithmWithPadding, rsaCipherAlgorithmProvider);
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
     * Encrypt a plain text with PublicKey object made from the Java program itself.
     * @param plainText : Any normal text that you want to encrypt on.
     * @param publicKey : A PublicKey object get from @return of the generateNewRSAKeyPair() itself.
     */
    public String encryptStringWithRSABase64Encoded(String plainText, PublicKey publicKey) {
        String encoded;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
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
    public String decryptWithSpecialRSABase64Encoded(String base64EncodedEncryptedString) {
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
     * Use this if you have a PrivateKey object with the correct encryption.rsa.cipher.algorithm.with.padding.
     * Decrypt a encoded Base64, RSA encrypted text with PrivateKey object from the file, with directory from  encryption.rsa.private.key.directory.
     *
     * @param base64EncodedEncryptedString: A base64 encoded, RSA encrypted String using file encryption.rsa.public.key.directory.
     * @param privateKey                    : PrivateKey object, loaded by using encryption.rsa.private.key.directory during startup.
     */
    public String decryptWithSpecialRSABase64Encoded(String base64EncodedEncryptedString, PrivateKey privateKey) {
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
     * @param base64EncodedEncryptedString : A base64 encoded, RSA encrypted String using @method generateNewRSAKeyPair().
     * @param privateKey : A PrivateKey object get from @return of the generateNewRSAKeyPair() itself.
     */
    public String decryptWithRSABase64Encoded(String base64EncodedEncryptedString, PrivateKey privateKey) {
        String plainText;
        try {
            // SonarLint recommends*
            // https://rules.sonarsource.com/java/RSPEC-5542
            Cipher cipher = Cipher.getInstance("RSA");
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

    /**
     * Set up Key Factory for RSA for symmetric encryption.
     * */
    private void setupRSAKeyFactory() throws NoSuchAlgorithmException {
        keyFactory = KeyFactory.getInstance("RSA");
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

        privateKey = keyFactory.generatePrivate(keySpec);
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

        publicKey = keyFactory.generatePublic(keySpec);
    }
}
