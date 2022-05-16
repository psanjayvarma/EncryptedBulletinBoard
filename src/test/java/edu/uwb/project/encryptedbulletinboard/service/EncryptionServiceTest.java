package edu.uwb.project.encryptedbulletinboard.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionServiceTest {

    //This test will pass
    @Test
    void testEncryption()
    {
        EncryptionService encryptionService = new EncryptionService();
        String message = "Test Message";
        String verification = "This test message is not the same";
        String key = encryptionService.generateKey();
        String encryptedMessage = encryptionService.encrypt(message, encryptionService.stringToKey(key));
        String decryptedMessage = encryptionService.decrypt(encryptedMessage, encryptionService.stringToKey(key));
        //Checking both conditions
        assertNotEquals(verification,decryptedMessage);
        assertEquals(message,decryptedMessage);
    }

    //This test will fail since the key used to decrypt is incorrect.
    @Test
    void testEncryptionThatFails()
    {
        EncryptionService encryptionService = new EncryptionService();
        String message = "Test Message";
        String verification = "This test message is not the same";
        String key = encryptionService.generateKey();
        String incorrectKey = encryptionService.generateKey();
        String encryptedMessage = encryptionService.encrypt(message, encryptionService.stringToKey(key));
        String decryptedMessage = encryptionService.decrypt(encryptedMessage, encryptionService.stringToKey(incorrectKey));
        //Checking both conditions
        assertNotEquals(verification,decryptedMessage);
        assertNotEquals(message,decryptedMessage);
    }
}