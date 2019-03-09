package tk.wonderdance.user.helper;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class StringGenerator {
    private static final String SOURCES =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    public String generateString(int length) {
        SecureRandom random = new SecureRandom();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = SOURCES.charAt(random.nextInt(SOURCES.length()));
        }
        return new String(text);
    }
}
