package controladores_dao;

import modelos.Usuario;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import vistas_modelos.UsuarioV;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class UsuarioDao {

    private DataSource dataSource;
    private QueryRunner queryRunner;
    private static UsuarioDao usuarioDao;

    private String SQL_INSERT = "insert into usuario (nombre, apellido, telefono)" +
            "                   values(?,?,?)";
    private String SQL_SELECT = "select * from usuario";
    private String SQL_DELETE = "delete from usuario where id = ?";
    private String SQL_UPDATE = "update usuario set (nombre, apellido, telefono)" +
            "                   values(?,?,?)";

    public UsuarioDao() throws NamingException{
        this.dataSource = UsuarioV.getDataSource();
        this.queryRunner = new QueryRunner(dataSource);
    }

    public static UsuarioDao getInstance() throws NamingException {
        if(usuarioDao == null) {
            usuarioDao = new UsuarioDao();
        }

        return usuarioDao;
    }

//  MOSTRAR USUARIOS
    public List<Usuario> mostrarUsuarios() throws SQLException{
        BeanListHandler<Usuario> resultHandler = new BeanListHandler<>(Usuario.class);

        return queryRunner.query(SQL_SELECT, resultHandler);
    }

//  INSERTAR USUSARIO
    public int insertaUsuario(Usuario usuario) {
        int result = 0;
        Object[] params = {
                usuario.getNombre(), usuario.getApellido(), usuario.getTelefono()
        };

        try {
            result = queryRunner.update(SQL_INSERT, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

//    ACTUALIZAR USUARIO
    public int actualizaUsuario(Usuario usuario) {
        int result = 0;
        Object[] params = {
                usuario.getNombre(), usuario.getApellido(), usuario.getTelefono()
        };

        try {
            result = queryRunner.update(SQL_UPDATE, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

//    ELIMINAR USUARIO
    public int eliminarUsuario(Usuario usuario) {
        int result = 0;

        Object[] params = { usuario.getId() };

        try {
            result = queryRunner.update(SQL_DELETE, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
