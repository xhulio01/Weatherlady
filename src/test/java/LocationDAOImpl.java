import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAOImpl implements LocationDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/weatherlady";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    @Override
    public void addLocation(Location location) {
        String sql = "INSERT INTO locations (latitude, longitude, region, country) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, location.getLatitude());
            pstmt.setDouble(2, location.getLongitude());
            pstmt.setString(3, location.getRegion());
            pstmt.setString(4, location.getCountry());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM locations";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Location location = new Location();
                location.setId(rs.getInt("id"));
                location.setLatitude(rs.getDouble("latitude"));
                location.setLongitude(rs.getDouble("longitude"));
                location.setRegion(rs.getString("region"));
                location.setCountry(rs.getString("country"));
                locations.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }

    @Override
    public Location getLocationById(int id) {
        Location location = null;
        String sql = "SELECT * FROM locations WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                location = new Location();
                location.setId(rs.getInt("id"));
                location.setLatitude(rs.getDouble("latitude"));
                location.setLongitude(rs.getDouble("longitude"));
                location.setRegion(rs.getString("region"));
                location.setCountry(rs.getString("country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        String sql = "UPDATE locations SET latitude = ?, longitude = ?, region = ?, country = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, location.getLatitude());
            pstmt.setDouble(2, location.getLongitude());
            pstmt.setString(3, location.getRegion());
            pstmt.setString(4, location.getCountry());
            pstmt.setInt(5, location.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLocation(int id) {
        String sql = "DELETE FROM locations WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
