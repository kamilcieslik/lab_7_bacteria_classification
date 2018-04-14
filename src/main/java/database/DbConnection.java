package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private Connection connection;

    public DbConnection(String db_url, String login, String password) throws SQLException {
        connection = DriverManager.getConnection(db_url, login, password);
        connection.setAutoCommit(true);
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (!connection.isClosed())
            connection.close();
    }
}
