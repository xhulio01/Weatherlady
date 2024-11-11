import java.util.Date;

public class WeatherData {
    private int id;
    private Location location;
    private Date date;
    private double temperature;
    private double pressure;
    private double humidity;
    private double windSpeed;
    private double windDirection;

    // Constructors
    public WeatherData() {}

    public WeatherData(Location location, Date date, double temperature, double pressure, double humidity, double windSpeed, double windDirection) {
        this.location = location;
        this.date = date;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public double getPressure() { return pressure; }
    public void setPressure(double pressure) { this.pressure = pressure; }

    public double getHumidity() { return humidity; }
    public void setHumidity(double humidity) { this.humidity = humidity; }

    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }

    public double getWindDirection() { return windDirection; }
    public void setWindDirection(double windDirection) { this.windDirection = windDirection; }
}
