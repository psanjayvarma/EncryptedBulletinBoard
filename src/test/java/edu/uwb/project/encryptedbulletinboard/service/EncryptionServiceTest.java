package edu.uwb.project.encryptedbulletinboard.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionServiceTest {

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
}