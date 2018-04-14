package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private Connection connection;

    public DbConnection(String db_url, String login, String password) throws SQLException {
        DriverManager.setLoginTimeout(3);
        connection = DriverManager.getConnection("jdbc:mysql://" + db_url + "?useSSL=false&useUnicode=true" +
                "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", login, password);
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
