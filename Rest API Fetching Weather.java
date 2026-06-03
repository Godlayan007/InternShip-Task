import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class WeatherApp {

    // Open-Meteo API endpoint for current weather in New Delhi
    private static final String API_URL = "https://api.open-meteo.com/v1/forecast?latitude=28.61&longitude=77.21&current_weather=true";

    public static void main(String[] args) {
        System.out.println("Fetching the latest weather data...\n");

        try {
            // 1. Create a modern HttpClient (Available in Java 11+)
            HttpClient client = HttpClient.newHttpClient();

            // 2. Build the HTTP GET Request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .GET() // We are fetching data, so we use GET
                    .build();

            // 3. Send the request and store the response as a String
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 4. Verify the request was successful (HTTP Status 200 means OK)
            if (response.statusCode() == 200) {
                // If successful, pass the JSON string to our formatting method
                formatAndDisplayData(response.body());
            } else {
                System.out.println("Failed to fetch data. HTTP Status Code: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("An error occurred while connecting to the API: " + e.getMessage());
        }
    }

    /**
     * Parses the raw JSON string and displays it in a structured, human-readable UI.
     */
    private static void formatAndDisplayData(String rawJson) {
        // Initialize Gson to parse the JSON
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(rawJson, JsonObject.class);

        // The API wraps the weather data inside a "current_weather" object. Let's isolate it.
        JsonObject currentWeather = jsonObject.getAsJsonObject("current_weather");

        // Extract the specific data points we care about
        double temperature = currentWeather.get("temperature").getAsDouble();
        double windSpeed = currentWeather.get("windspeed").getAsDouble();
        int weatherCode = currentWeather.get("weathercode").getAsInt();
        String time = currentWeather.get("time").getAsString();

        // Format the time string (e.g., "2023-10-25T14:00" -> "2023-10-25 14:00")
        String formattedTime = time.replace("T", " ");

        // Print the structured output to the console
        System.out.println("=====================================");
        System.out.println("        CURRENT WEATHER REPORT       ");
        System.out.println("=====================================");
        System.out.println(" Location:    New Delhi, India");
        System.out.println(" Time:        " + formattedTime);
        System.out.println("-------------------------------------");
        System.out.println(" Temperature: " + temperature + " °C");
        System.out.println(" Wind Speed:  " + windSpeed + " km/h");
        System.out.println(" Status Code: " + weatherCode);
        System.out.println("=====================================");
    }
}
