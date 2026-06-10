package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

public class DatabaseConnection {
    // SQLite uses a local file to store the database, requiring no server installation
    private static final String DB_FILE = "loan_management.db";
    private static final String URL = "jdbc:sqlite:" + DB_FILE;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC Driver not found. Please add the sqlite-jdbc jar to your project.");
            e.printStackTrace();
        }
        
        boolean needsInitialization = !new File(DB_FILE).exists();
        Connection conn = DriverManager.getConnection(URL);
        
        if (needsInitialization) {
            initializeDatabase(conn);
        }
        
        return conn;
    }

    private static void initializeDatabase(Connection conn) {
        String[] queries = {
            "CREATE TABLE IF NOT EXISTS users (" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    full_name VARCHAR(100) NOT NULL," +
            "    email VARCHAR(100) UNIQUE NOT NULL," +
            "    password VARCHAR(255) NOT NULL," +
            "    role VARCHAR(20) DEFAULT 'USER'," +
            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ");",
            
            "CREATE TABLE IF NOT EXISTS admins (" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    username VARCHAR(50) UNIQUE NOT NULL," +
            "    password VARCHAR(255) NOT NULL" +
            ");",
            
            "CREATE TABLE IF NOT EXISTS loan_applications (" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    user_id INTEGER," +
            "    full_name VARCHAR(100)," +
            "    dob DATE," +
            "    pan_aadhar VARCHAR(20)," +
            "    loan_type VARCHAR(50)," +
            "    loan_amount REAL," +
            "    monthly_income REAL," +
            "    employment_type VARCHAR(50)," +
            "    reason TEXT," +
            "    id_proof_path VARCHAR(255)," +
            "    income_proof_path VARCHAR(255)," +
            "    status VARCHAR(20) DEFAULT 'PENDING'," +
            "    admin_comments TEXT," +
            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "    FOREIGN KEY (user_id) REFERENCES users(id)" +
            ");",
            
            "CREATE TABLE IF NOT EXISTS notifications (" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    user_id INTEGER," +
            "    message TEXT," +
            "    is_read BOOLEAN DEFAULT 0," +
            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "    FOREIGN KEY (user_id) REFERENCES users(id)" +
            ");",
            
            "INSERT INTO admins (username, password) VALUES ('admin', 'admin123');"
        };

        try (Statement stmt = conn.createStatement()) {
            for (String query : queries) {
                stmt.execute(query);
            }
            System.out.println("Database initialized successfully!");
        } catch (SQLException e) {
            System.err.println("Error initializing database.");
            e.printStackTrace();
        }
    }
}
