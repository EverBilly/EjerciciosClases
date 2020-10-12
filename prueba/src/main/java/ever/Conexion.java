package ever;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

@Log4j2
public class Conexion {

    private static Properties getPropertiesFromFile(String filename) {
        Properties properties = null;

        try {
            String path = System.getProperty("user.dir") + System.getProperty("file.separator") + filename;

            File file = new File(path);

            try (InputStream is = new FileInputStream(file)) {
                properties = new Properties();
                properties.load(is);
            }
        } catch (Exception e) {
            System.out.println(new Date() + "\t\t [getPropertiesFromFile]\tWebSocketServer>>> " + e.getMessage());
        }

        return properties;
    }

    private static String[] getDataConnection(Properties properties) {
        String[] datosConexion = new String[3];

        try {
            if (properties.containsKey("host") && properties.containsKey("port")
                    && properties.containsKey("user") && properties.containsKey("pass")) {

                String host = properties.getProperty("host");
                String port = properties.getProperty("port");
                String servicename = properties.getProperty("servicename");
                String user = properties.getProperty("user");
                String pass = properties.getProperty("pass");

                String urlDatabase = "jdbc:mysql://" + host + ":" + port + "/" + "java";
                datosConexion[0] = urlDatabase;
                datosConexion[1] = user;
                datosConexion[2] = pass;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return datosConexion;
    }

    public static Connection getMySQLConnection() throws SQLException {
        Connection cnx = null;

        Properties p = getPropertiesFromFile("conexion.properties");

        if (p != null) {
            String[] datos = getDataConnection(p);
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            cnx = DriverManager.getConnection(datos[0], datos[1], datos[2]);
        } else {
            log.error("El objeto properties llego nulo.");
        }

        return cnx;
    }
}
