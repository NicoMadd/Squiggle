package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBProperties {

    private static Properties properties;

    public static Properties getInstance() {
        if (properties == null) {
            properties = new Properties();
            try {
                properties.load(new FileInputStream("src/test/java/resources/database.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;

    }

    private DBProperties() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("database.properties")) {
            props.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
