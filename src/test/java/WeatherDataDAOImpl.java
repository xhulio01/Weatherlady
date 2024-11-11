import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherDataDAOImpl implements WeatherDataDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/weatherlady";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    @Override
    public void addWeatherData(WeatherData weatherData) {
        String sql = "INSERT INTO weather_data (location_id, date, temperature, pressure, humidity, wind_speed, wind_direction) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, weatherData.getLocation().getId());
            pstmt.setTimestamp(2, new Timestamp(weatherData.getDate().getTime()));
            pstmt.setDouble(3, weatherData.getTemperature());
            pstmt.setDouble(4, weatherData.getPressure());
            pstmt.setDouble(5, weatherData.getHumidity());
            pstmt.setDouble(6, weatherData.getWindSpeed());
            pstmt.setDouble(7, weatherData.getWindDirection());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<WeatherData> getWeatherDataByLocationAndDate(Location location, Date date) {
        List<WeatherData> weatherDataList = new ArrayList<>();
        String sql = "SELECT * FROM weather_data WHERE location_id = ? AND date = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, location.getId());
            pstmt.setTimestamp(2, new Timestamp(date.getTime()));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                WeatherData weatherData = new WeatherData();
                weatherData.setId(rs.getInt("id"));
                weatherData.setLocation(location);
                weatherData.setDate(new Date(rs.getTimestamp("date").getTime()));
                weatherData.setTemperature(rs.getDouble("temperature"));
                weatherData.setPressure(rs.getDouble("pressure"));
                weatherData.setHumidity(rs.getDouble("humidity"));
                weatherData.setWindSpeed(rs.getDouble("wind_speed"));
                weatherData.setWindDirection(rs.getDouble("wind_direction"));
                weatherDataList.add(weatherData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weatherDataList;
    }
}
