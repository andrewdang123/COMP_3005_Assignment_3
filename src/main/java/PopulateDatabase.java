import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class PopulateDatabase {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/school";
        String user = "postgres";
        String password = "postgres";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Connected to PostgreSQL successfully!");

            URL resource = PopulateDatabase.class.getClassLoader().getResource("School.sql");
            if (resource == null) {
                throw new IllegalArgumentException("Could not find School.sql in resources folder.");
            }

            Path sqlPath = Paths.get(resource.toURI());
            String sql = Files.readString(sqlPath);

            // Execute SQL statements
            try (Statement stmt = conn.createStatement()) {
                for (String query : sql.split(";")) {
                    if (!query.trim().isEmpty())
                        stmt.execute(query);
                }
            }

            System.out.println("📘 Executed School.sql successfully!");
        } catch (URISyntaxException e) {
            System.err.println("⚠️ Invalid URI for School.sql: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
