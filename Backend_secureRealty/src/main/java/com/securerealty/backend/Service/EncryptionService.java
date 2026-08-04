package com.securerealty.backend.Service;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import org.springframework.stereotype.Service;

import com.securerealty.backend.dto.EncryptedData;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@Service
public class EncryptionService {

	@Value("${encryption.secret}")
    private String secret;

    private SecretKey secretKey;
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;
    @PostConstruct
    public void init() {

        byte[] keyBytes = Base64.getDecoder().decode(secret);

        secretKey = new SecretKeySpec(
                keyBytes,
                "AES"
        );
    }
    
    public EncryptedData encrypt(String plainText) throws Exception {

        byte[] iv = new byte[GCM_IV_LENGTH];

        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        GCMParameterSpec spec =
                new GCMParameterSpec(GCM_TAG_LENGTH, iv);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);

        byte[] encrypted =
                cipher.doFinal(
                        plainText.getBytes(StandardCharsets.UTF_8));
        

        return new EncryptedData(

                Base64.getEncoder().encodeToString(encrypted),

                Base64.getEncoder().encodeToString(iv)

        );
    }
    public String decrypt(String cipherText, String iv) throws Exception {

        byte[] cipherBytes =
                Base64.getDecoder().decode(cipherText);

        byte[] ivBytes =
                Base64.getDecoder().decode(iv);

        Cipher cipher =
                Cipher.getInstance("AES/GCM/NoPadding");

        GCMParameterSpec spec =
                new GCMParameterSpec(GCM_TAG_LENGTH, ivBytes);

        cipher.init(
                Cipher.DECRYPT_MODE,
                secretKey,
                spec);

        byte[] decrypted =
                cipher.doFinal(cipherBytes);

        return new String(
                decrypted,
                StandardCharsets.UTF_8);
    }

}