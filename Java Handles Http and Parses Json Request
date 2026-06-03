import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;

public class HttpJsonHandler {

    // 1. Define a standard Java Class (or Record) to map the JSON keys to variables
    public static class PostData {
        public int userId;
        public int id;
        public String title;
        public String body;
    }

    public static void main(String[] args) {
        // The API endpoint we are hitting
        String apiUrl = "https://jsonplaceholder.typicode.com/posts/1";

        // 2. Initialize the native Java HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // 3. Build the HTTP GET Request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Accept", "application/json") // Good practice
                .GET()
                .build();

        try {
            // 4. Send the request and capture the response as a String
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the request was successful (HTTP 200 OK)
            if (response.statusCode() == 200) {
                
                // 5. Initialize Gson to parse the JSON string
                Gson gson = new Gson();
                
                // 6. Magically parse the JSON string directly into our Java object
                PostData parsedData = gson.fromJson(response.body(), PostData.class);

                // Output the parsed results like a human
                System.out.println("--- REQUEST SUCCESSFUL ---");
                System.out.println("Post ID: " + parsedData.id);
                System.out.println("User ID: " + parsedData.userId);
                System.out.println("Title:   " + parsedData.title);
                System.out.println("Body:    " + parsedData.body.replace("\n", " "));

            } else {
                System.out.println("Request failed. Server responded with Status Code: " + response.statusCode());
            }

        } catch (Exception e) {
            System.err.println("An error occurred during the HTTP request:");
            e.printStackTrace();
        }
    }
}
