package edu.uwb.project.encryptedbulletinboard.service;

import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Encryption Service is responsible for Encrypting and decrypting a message
 */
@Service
public class EncryptionService {

    /**
     * Method to generate random key that can be used for encryption
     */
    public String generateKey() {
       try{
           //Using Blowfish algorithm keys
            KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
            //set key size to 32 bit
            keyGenerator.init(32);
            SecretKey key = keyGenerator.generateKey();

            //return generated key
            return keyToString(key);
        } catch (Exception e) {
           //return null in case of any exceptions
            return null;
        }
    }

    /**
     * Method to convert SecretKey type to String
     */
    public String keyToString(SecretKey key){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * Method to convert String to SecretKey
     */
    public SecretKey stringToKey(String key){
        return new SecretKeySpec(Base64.getDecoder().decode(key), "Blowfish");
    }

    /**
     * Method to return encrypted String for a given String and Key
     */
    public String encrypt(String message, SecretKey key){

        try {
            //Using Blowfish algorithm to encrypt
            Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
            //Initializing with the key
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //Encrypt the message
            byte[] encryptedMessage = cipher.doFinal(message.getBytes());

            //return string value
            return Base64.getEncoder().encodeToString(encryptedMessage);
        } catch (Exception e) {
            //return null in case of any exception
            return null;
        }
    }

    public String decrypt(String message, SecretKey key){
        try {
            //Using Blowfish algorithm to decrypt
            Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
            //Initializing with the key
            cipher.init(Cipher.DECRYPT_MODE, key);
            //Decrypt the message
            byte[] decryptedMessage = cipher.doFinal(Base64.getDecoder().decode(message));

            //return a string value
            return new String(decryptedMessage);
        } catch (Exception e) {
            //return error message in case of exception
            return "Invalid Key";
        }
    }

}
