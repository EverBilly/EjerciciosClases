package com.ever;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.logging.log4j.Level;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

//@Log4j2
public class Usuario extends models.Usuario {

    @Wire
    private Button btnMostrar;

    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        connection = Conexion.getMySQLConnection();

        QueryRunner queryRunner = new QueryRunner();

        BeanListHandler<Usuario> resultHandler = new BeanListHandler<>(Usuario.class);

        try {
            List<Usuario> usuarios = queryRunner.query(connection, "{call mostrarUsuarios}", resultHandler);

            for (models.Usuario usuario: usuarios) {
//                log.log(Level.INFO, usuario.getNombre());
                Messagebox.show(usuario.getNombre());
            }
        } finally {
            DbUtils.close(connection);
        }
    }

    @Listen("onClick = #btnMostrar")
    public void muestraUsuarios() throws SQLException{

        Connection connection = null;
        connection = Conexion.getMySQLConnection();
        QueryRunner queryRunner = new QueryRunner();

        BeanListHandler<models.Usuario> resultHandler = new BeanListHandler<>(models.Usuario.class);

        try {
            List<models.Usuario> usuarios = queryRunner.query(connection, "{call mostrarUsuarios}", resultHandler);

            for (models.Usuario usuario: usuarios) {
                Messagebox.show(usuario.getNombre());
//                System.out.println(usuario.getNombre());
            }
        } finally {
            DbUtils.close(connection);
        }
    }

}
