import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.io.InputStream;

public class ChatGPTClient {

    public static String chatGPT(String prompt) throws URISyntaxException, IOException {

        InputStream input = ChatGPTClient.class.getClassLoader().getResourceAsStream("config.properties");
        Properties prop = new Properties();
        prop.load( input );

        String urlString  = "https://api.openai.com/v1/engines/gpt-3.5-turbo/completions";
        String apiKey = prop.getProperty("API_KEY");
        String model = "gpt-3.5-turbo";

        try {

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [" +
                    "{\"role\": \"system\", \"content\": \"Hi GPT! Please write an interesting essay about the river Danube that describes its main " +
                    "characteristics, considering additional aspects below:\"}," +
                    "{\"role\": \"user\", \"content\": \"" + prompt + "\"}" +
                    "]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();

                // Calls the method to extract the message.
                return extractContentFromResponse(response.toString());
            } else {
                throw new IOException("HTTP error code: " + responseCode);
            }

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    // This method extracts the response expected from chatgpt and returns it.
    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content")+11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
    }
}