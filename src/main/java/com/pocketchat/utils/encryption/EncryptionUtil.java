package com.pocketchat.utils.encryption;

import com.pocketchat.server.exceptions.encryption.EncryptionErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

//
@Service
public class EncryptionUtil {

    private String rsaPublicKeyDirectory;

    private String rsaPrivateKeyDirectory;

    private String aesPrivateKeyDirectory;

    private String rsaCipherAlgorithmWithPadding;

    private String aesCipherAlgorithmWithPadding;

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
            @Value("${encryption.aes.cipher.algorithm.with.padding}") String aesCipherAlgorithmWithPadding,
            @Value("${encryption.rsa.cipher.key.size}") int rsaCipherKeySize) {
        this.rsaPublicKeyDirectory = rsaPublicKeyDirectory;
        this.rsaPrivateKeyDirectory = rsaPrivateKeyDirectory;
        this.aesPrivateKeyDirectory = aesPrivateKeyDirectory;
        this.rsaCipherAlgorithmWithPadding = rsaCipherAlgorithmWithPadding;
        this.aesCipherAlgorithmWithPadding = aesCipherAlgorithmWithPadding;
        this.rsaCipherKeySize = rsaCipherKeySize;

        try {
            setupRSAKeyFactory();
            setupRSAPrivateKey();
            setupRSAPublicKey();
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
    }

    /**
     * Generate new RSA Key Pair.
     * Built for dynamic RSA key pair between server and user. But end to end encryption it is not enough. Server are keeping the keys for the user, means the company itself, if know the method to reverse engineer it, they can break them.
     * Please store it somewhere safe in the database.
     * */
    public KeyPair generateNewRSAKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(rsaCipherAlgorithmWithPadding);
            keyPairGenerator.initialize(4096);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new EncryptionErrorException(noSuchAlgorithmException.getMessage());
        }
    }

    public String encryptStringWithRSABase64Encoded(String plainText) {
        String encoded = null;
        try {
            Cipher cipher = Cipher.getInstance(rsaCipherAlgorithmWithPadding);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // Encrypt with RSA
            byte[] rsaEncrypted = cipher.doFinal(plainText.getBytes());
            // Encode with Base64
            encoded = Base64.getEncoder().encodeToString(rsaEncrypted);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
        return encoded;
    }

    /**
     * Encrypt a plain text with base64 encoded, with a public key from external
     * */
    public String encryptStringWithRSABase64Encoded(String plainText, PublicKey publicKey) {
        String encoded = null;
        try {
            Cipher cipher = Cipher.getInstance(aesCipherAlgorithmWithPadding);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // Encrypt with RSA
            byte[] rsaEncrypted = cipher.doFinal(plainText.getBytes());
            // Encode with Base64
            encoded = Base64.getEncoder().encodeToString(rsaEncrypted);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }
        return encoded;
    }

    public String decryptWithRSABase64Encoded(String base64EncodedEncryptedString) {
        String plainText = null;
        try {
            // SonarLint recommends*
            // https://rules.sonarsource.com/java/RSPEC-5542
            Cipher cipher = Cipher.getInstance(rsaCipherAlgorithmWithPadding);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // Decode Base64
            byte[] encryptedString = Base64
                    .getDecoder()
                    .decode(base64EncodedEncryptedString);
            // Decrypt string
            byte[] decrypted = cipher.doFinal(encryptedString);
            plainText = new String(decrypted);
        } catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }

        return plainText;
    }

    /**
     * Decrypt a encoded Base64, RSA encrypted text with PrivateKey object from external
     * */
    public String decryptWithRSABase64Encoded(String base64EncodedEncryptedString, PrivateKey privateKey) {
        String plainText = null;
        try {
            // SonarLint recommends*
            // https://rules.sonarsource.com/java/RSPEC-5542
            Cipher cipher = Cipher.getInstance(rsaCipherAlgorithmWithPadding);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // Decode Base64
            byte[] encryptedString = Base64
                    .getDecoder()
                    .decode(base64EncodedEncryptedString);
            // Decrypt string
            byte[] decrypted = cipher.doFinal(encryptedString);
            plainText = new String(decrypted);
        } catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException generalSecurityException) {
            throw new EncryptionErrorException(generalSecurityException.getMessage());
        }

        return plainText;
    }

    private void setupRSAKeyFactory() throws NoSuchAlgorithmException {
        keyFactory = KeyFactory.getInstance("RSA");
    }

    private void setupRSAPrivateKey() throws IOException, InvalidKeySpecException {
        // DO NOT use new ClassPathResource(**DIRECTORY**).getFile(); anymore, especially if you're using Docker.
        // https://stackoverflow.com/questions/14876836/file-inside-jar-is-not-visible-for-spring
        InputStream fileInputStream = new ClassPathResource(rsaPrivateKeyDirectory).getInputStream();
        String privateKeyString = new String(fileInputStream.readAllBytes());

        String filteredPrivateKey = privateKeyString
                .replaceAll("\\n", "")
                .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("-----END PRIVATE KEY-----", "")
                .trim();

        byte[] decoded = Base64
                .getDecoder()
                .decode(filteredPrivateKey);

        KeySpec keySpec
                = new PKCS8EncodedKeySpec(decoded);

        privateKey = keyFactory.generatePrivate(keySpec);
    }

    private void setupRSAPublicKey() throws IOException, InvalidKeySpecException {
        // DO NOT use new ClassPathResource(**DIRECTORY**).getFile(); anymore, especially if you're using Docker.
        // https://stackoverflow.com/questions/14876836/file-inside-jar-is-not-visible-for-spring
        InputStream fileInputStream = new ClassPathResource(rsaPublicKeyDirectory).getInputStream();
        String publicKeyString = new String(fileInputStream.readAllBytes());

        String filterPublicKey = publicKeyString
                .replaceAll("\\n", "")
                .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .trim();

        byte[] decoded = Base64
                .getDecoder()
                .decode(filterPublicKey);

        KeySpec keySpec
                = new X509EncodedKeySpec(decoded);

        publicKey = keyFactory.generatePublic(keySpec);
    }
}
