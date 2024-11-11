import java.util.List;

public interface LocationDAO {
    void addLocation(Location location);
    List<Location> getAllLocations();
    Location getLocationById(int id);
    void updateLocation(Location location);
    void deleteLocation(int id);
}

