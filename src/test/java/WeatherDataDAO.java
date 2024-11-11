import java.util.Date;
import java.util.List;

public interface WeatherDataDAO {
    void addWeatherData(WeatherData weatherData);
    List<WeatherData> getWeatherDataByLocationAndDate(Location location, Date date);
}

