

package utils;

import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataReader {

    public static JSONObject getTestData() throws Exception {
        String content = new String(
                Files.readAllBytes(Paths.get("src/test/resources/testdata.json")));
        return new JSONObject(content);
    }
}
