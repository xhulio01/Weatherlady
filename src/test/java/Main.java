import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LocationDAO locationDAO = new LocationDAOImpl();
        WeatherDataDAO weatherDataDAO = new WeatherDataDAOImpl();
        WeatherService weatherService = new WeatherService(weatherDataDAO);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Location");
            System.out.println("2. Display Locations");
            System.out.println("3. Download Weather Data");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addLocation(locationDAO, scanner);
                    break;
                case 2:
                    displayLocations(locationDAO);
                    break;
                case 3:
                    downloadWeatherData(locationDAO, weatherService, scanner);
                    break;
                case 4:
                    System.exit(0);
            }
        }
    }

    private static void addLocation(LocationDAO locationDAO, Scanner scanner) {
        System.out.print("Enter latitude: ");
        double latitude = scanner.nextDouble();
        System.out.print("Enter longitude: ");
        double longitude = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter region: ");
        String region = scanner.nextLine();
        System.out.print("Enter country: ");
        String country = scanner.nextLine();

        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setRegion(region);
        location.setCountry(country);
        locationDAO.addLocation(location);
    }

    private static void displayLocations(LocationDAO locationDAO) {
        List<Location> locations = locationDAO.getAllLocations();
        locations.forEach(location -> System.out.println(location.getId() + ": " + location.getLatitude() + ", " + location.getLongitude() + " - " + location.getRegion() + ", " + location.getCountry()));
    }

    private static void downloadWeatherData(LocationDAO locationDAO, WeatherService weatherService, Scanner scanner) {
        System.out.print("Enter location ID: ");
        int locationId = scanner.nextInt();
        Location location = locationDAO.getLocationById(locationId);
        if (location != null) {
            WeatherData weatherData = weatherService.fetchWeatherData(location);
            if (weatherData != null) {
                System.out.println("Temperature: " + weatherData.getTemperature() + "°C");
                System.out.println("Pressure: " + weatherData.getPressure() + " hPa");
                System.out.println("Humidity: " + weatherData.getHumidity() + "%");
                System.out.println("Wind Speed: " + weatherData.getWindSpeed() + " m/s");
                System.out.println("Wind Direction: " + weatherData.getWindDirection() + "°");
            }
        } else {
            System.out.println("Location not found!");
        }
    }
}
