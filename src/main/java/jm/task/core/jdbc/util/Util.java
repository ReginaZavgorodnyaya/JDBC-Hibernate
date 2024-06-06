package jm.task.core.jdbc.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final Logger log = Logger.getLogger(Util.class.getName());

    public static Connection connection() {


        log.info("Loading application properties");
        Properties properties = new Properties();
        try {
            properties.load(Util.class.getClassLoader()
                    .getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Вы не смогли найти application.properties", e);

        }

        try {
            log.info("Loading postgresql.Driver");
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        log.info("Connecting to the database");

        try {
            return DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


