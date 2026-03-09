

package utils;

import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility class for loading structured test data used across the automation suite.
 */
public class DataReader {

    /**
     * Reads the JSON test data file and returns it as a {@link JSONObject}.
     *
     * <p>Expected file location: {@code src/test/resources/testdata.json}</p>
     *
     * @return parsed JSON object containing test data
     * @throws Exception if the file cannot be read or the JSON content is invalid
     */
    public static JSONObject getTestData() throws Exception {
        // Read the full file content as text before parsing into JSON.
        String content = new String(
                Files.readAllBytes(Paths.get("src/test/resources/testdata.json")));
        return new JSONObject(content);
    }
}
