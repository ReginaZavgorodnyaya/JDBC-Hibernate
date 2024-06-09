package jm.task.core.jdbc.util;

import jdk.jfr.Event;
import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class Util {

    //Настройка соединения через JDBC
//
    private static final Logger log = Logger.getLogger(Util.class.getName());

    public static Connection connection() {

        log.info("Loading postgresql.Driver");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Properties properties = getProperties();
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

    private static Properties getProperties() {
        log.info("Loading application properties");
        Properties properties = new Properties();
        try {
            properties
                    .load(Util.class.getClassLoader()
                    .getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Вы не смогли найти application.properties", e);
        }
        return properties;
    }

    //Настройка соединения через Hibernate

    public static SessionFactory setUp() {
        Properties properties = getProperties();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.url", properties.getProperty("hibernate.connection.url"))
                .applySetting("hibernate.connection.username", properties.getProperty("hibernate.connection.username"))
                .applySetting("hibernate.connection.password", properties.getProperty("hibernate.connection.password"))
                .applySetting("hibernate.show_sql", properties.getProperty("hibernate.show_sql"))
                .build();
        try {
            MetadataSources sources = new MetadataSources(registry);
            sources.addAnnotatedClass(User.class);

            Metadata metadata = sources.getMetadataBuilder().build();

            return metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw new RuntimeException("Вы не смогли подключится к БД проверьте настройки");
        }
    }

}


