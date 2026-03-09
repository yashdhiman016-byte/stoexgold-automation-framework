package utils;

import java.util.UUID;

/**
 * Generates dynamic test data values to avoid collisions between runs.
 */
public class TestDataGenerator {

    /**
     * Creates a short unique email id for registration scenarios.
     */
    public static String generateUniqueEmail() {
        return "test" + UUID.randomUUID().toString().substring(0, 6)
                + "@mailinator.com";
    }
}
