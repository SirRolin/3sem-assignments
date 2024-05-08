package week13.ReactIII.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class secretKey {
    public static String generateSecretKey(int keyLengthBytes) {
        // Ensure that the key length is at least 256 bits (32 bytes)
        if (keyLengthBytes < 32) {
            throw new IllegalArgumentException("Key length must be at least 256 bits (32 bytes)");
        }

        // Generate random bytes for the secret key
        byte[] randomBytes = new byte[keyLengthBytes];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);

        // Encode the random bytes using Base64 encoding
        return Base64.getEncoder().encodeToString(randomBytes);
    }
}
