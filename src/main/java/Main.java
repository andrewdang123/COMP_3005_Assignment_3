import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.time.LocalDate;

public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/school";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void getAllStudents() {
        String query = "SELECT * FROM students;";
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("All Students:");
            while (rs.next()) {
                int id = rs.getInt("student_id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String email = rs.getString("email");
                LocalDate date = rs.getDate("enrollment_date").toLocalDate();
                System.out.printf("%d | %s %s | %s | %s%n", id, first_name, last_name, email, date);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }
    }

    public static void addStudent() {
        System.out.println("addStudent");
    }

    public static void updateStudentEmail() {
        System.out.println("updateStudentEmail");
    }

    public static void deleteStudent() {
        System.out.println("deleteStudent");
    }

    public static void main(String[] args) {
        Options.runView(Options.ApplicationFunctionEnum.class);
        Scanner scanner = new Scanner(System.in);
        scanner.close();
    }

}
