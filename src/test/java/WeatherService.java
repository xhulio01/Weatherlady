import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class WeatherService {
    private static final String API_KEY = "your_api_key"; // Replace with your actual API key
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric";
    private WeatherDataDAO weatherDataDAO;

    public WeatherService(WeatherDataDAO weatherDataDAO) {
        this.weatherDataDAO = weatherDataDAO;
    }

    public WeatherData fetchWeatherData(Location location) {
        String url = String.format(API_URL, location.getLatitude(), location.getLongitude(), API_KEY);
        WeatherData weatherData = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                InputStream content = response.getEntity().getContent();
                JsonNode rootNode = mapper.readTree(content);

                weatherData = new WeatherData();
                weatherData.setLocation(location);
                weatherData.setDate(new Date());
                weatherData.setTemperature(rootNode.path("main").path("temp").asDouble());
                weatherData.setPressure(rootNode.path("main").path("pressure").asDouble());
                weatherData.setHumidity(rootNode.path("main").path("humidity").asDouble());
                weatherData.setWindSpeed(rootNode.path("wind").path("speed").asDouble());
                weatherData.setWindDirection(rootNode.path("wind").path("deg").asDouble());

                weatherDataDAO.addWeatherData(weatherData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherData;
    }

    public List<WeatherData> getWeatherDataByLocationAndDate(Location location, Date date) {
        return weatherDataDAO.getWeatherDataByLocationAndDate(location, date);
    }
}
