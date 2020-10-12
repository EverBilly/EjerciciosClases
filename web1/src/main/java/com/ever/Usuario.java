package com.ever;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Log4j2
public class Usuario extends models.Usuario {

    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        connection = Conexion.getMySQLConnection();

        QueryRunner queryRunner = new QueryRunner();

        BeanListHandler<Usuario> resultHandler = new BeanListHandler<>(Usuario.class);

        try {
            List<Usuario> usuarios = queryRunner.query(connection, "{call mostrarUsuarios}", resultHandler);

            for (models.Usuario usuario: usuarios) {
                log.log(Level.INFO, usuario.getNombre());
            }
        } finally {
            DbUtils.close(connection);
        }
    }

}
