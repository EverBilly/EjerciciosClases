package ever;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

@Log4j2
public class App {

//    private static final Logger logger = LogManager.getLogger(App.class);
//    private static String driver = "com.mysql.jdbc.Driver";
//    private static String url = "jdbc:mysql://localhost:3306/java";
//    private static String user = "ever";
//    private static String pass = "Ever2020--";

    public static void main(String[] args) throws SQLException {

        Connection connection = null;
        connection = Conexion.getMySQLConnection();

//        Connection connection = null;
        QueryRunner queryRunner = new QueryRunner();
//        DbUtils.loadDriver(driver);

//        connection = DriverManager.getConnection(url, user, pass);

        BeanListHandler<Usuario> resultHandler = new BeanListHandler<>(Usuario.class);

        try {
            List<Usuario> usuarios = queryRunner.query(connection, "{call mostrarUsuarios}", resultHandler);

            for(Usuario usuario: usuarios) {
                log.log(Level.TRACE, usuario.getNombre());
                log.log(Level.INFO, usuario.getApellido());
                log.log(Level.INFO, usuario.getTelefono());
                log.log(Level.INFO, usuario.getId());
            }
        } finally {
            DbUtils.close(connection);
        }
    }
}
