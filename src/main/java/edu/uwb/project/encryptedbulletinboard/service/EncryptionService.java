package edu.uwb.project.encryptedbulletinboard.service;

import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class EncryptionService {

    public String generateKey() {
        SecureRandom secureRandom = new SecureRandom();

        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(secureRandom);
            SecretKey key = keyGenerator.generateKey();
            return keyToString(key);
        } catch (Exception e) {
            return null;
        }
    }

    public String keyToString(SecretKey key){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
    public SecretKey stringToKey(String key){
        return new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
    }

    public String encrypt(String message, SecretKey key){

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedMessage = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(encryptedMessage);
        } catch (Exception e) {
            return null;
        }
    }

    public String decrypt(String message, SecretKey key){
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedMessage = cipher.doFinal(Base64.getDecoder().decode(message));
            return new String(decryptedMessage);
        } catch (Exception e) {
            return "Invalid Key";
        }
    }

}
