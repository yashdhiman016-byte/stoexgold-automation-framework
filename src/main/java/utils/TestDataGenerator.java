//package utils;
//
//public class TestDataGenerator {
//
//    public static String generateUniqueEmail() {
//        return "auto" + System.currentTimeMillis() + "@stoexqa.com";
//    }
//}


package utils;

import java.util.UUID;

public class TestDataGenerator {

    public static String generateUniqueEmail() {
        return "test" + UUID.randomUUID().toString().substring(0, 6)
                + "@mailinator.com";
    }
}
