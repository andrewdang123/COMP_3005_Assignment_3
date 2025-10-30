import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
        try (Connection connection = connect();
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query)) {

            System.out.println("All Students:");
            while (result.next()) {
                int student_id = result.getInt("student_id");
                String first_name = result.getString("first_name");
                String last_name = result.getString("last_name");
                String email = result.getString("email");
                LocalDate date = result.getDate("enrollment_date").toLocalDate();
                System.out.printf("%d | %s %s | %s | %s%n", student_id, first_name, last_name, email, date);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }
    }

    public static void addStudent() {
        Scanner scanner = ScannerUtil.getScanner();

        try {
            System.out.print("Enter first name: ");
            String first_name = scanner.nextLine().trim();

            System.out.print("Enter last name: ");
            String last_name = scanner.nextLine().trim();

            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter enrollment date (YYYY-MM-DD): ");
            String dateInput = scanner.nextLine().trim();
            LocalDate enrollment_date = LocalDate.parse(dateInput);

            addStudent(first_name, last_name, email, enrollment_date);

        } catch (Exception e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }

    public static void addStudent(String first_name, String last_name, String email, LocalDate enrollment_date) {
        String query = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?);";
        try (Connection conn = connect();
                PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, email);
            preparedStatement.setDate(4, java.sql.Date.valueOf(enrollment_date));
            preparedStatement.executeUpdate();

            System.out.println("Student " + first_name + " " + last_name + " added.");

        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }

    }

    public static void updateStudentEmail() {
        Scanner scanner = ScannerUtil.getScanner();
        try {
            System.out.print("Enter student ID: ");
            int student_id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter new email: ");
            String new_email = scanner.nextLine().trim();

            updateStudentEmail(student_id, new_email);

        } catch (Exception e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }

    public static void updateStudentEmail(int student_id, String new_email) {
        String query = "UPDATE students SET email = ? WHERE student_id = ?;";
        try (Connection conn = connect();
                PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, new_email);
            preparedStatement.setInt(2, student_id);
            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("Student ID " + student_id + "'s email updated to " + new_email + ".");
            } else {
                System.out.println("No student found with ID " + student_id + ".");
            }

        } catch (SQLException e) {
            System.out.println("Error updating email: " + e.getMessage());
        }

    }

    public static void deleteStudent() {
        Scanner scanner = ScannerUtil.getScanner();
        try {
            System.out.print("Enter student ID: ");
            int student_id = scanner.nextInt();
            scanner.nextLine();

            deleteStudent(student_id);

        } catch (Exception e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }

    public static void deleteStudent(int student_id) {
        String query = "DELETE FROM students WHERE student_id = ?;";
        try (Connection conn = connect();
                PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, student_id);
            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("Student ID " + student_id + " deleted successfully.");
            } else {
                System.out.println("No student found with ID " + student_id + ".");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }

    }

    public static void main(String[] args) {
        Options.runView(Options.ApplicationFunctionEnum.class);
        ScannerUtil.shutdown();
    }

}
