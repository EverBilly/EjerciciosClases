package vistas_modelos;

import modelos.Usuario;
import org.zkoss.bind.annotation.*;
import controladores_dao.UsuarioDao;
import org.zkoss.zul.Messagebox;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public class Usuario2 {
    private UsuarioDao usuarioDao;
    public List<Usuario> listUsuarios;

    public List<Usuario> getListUsuarios() {
        return listUsuarios;
    }

    @Command
    @NotifyChange("listUsuarios")
    public void mostrarVista() {
        try {
            listUsuarios = UsuarioDao.getInstance().mostrarUsuarios();
//            Messagebox.show("Registros");
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        } catch (NamingException e) {
            System.out.println("No se ha mostrado nada: " + e.getMessage());
        }

    }
}
