package vistas_modelos;

import controladores_dao.UsuarioDao;
import modelos.Usuario;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zk.ui.util.Notification;
import org.zkoss.zul.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class UsuarioV  extends Usuario {

    private Listbox listboxUsuarios;

    private Textbox nombreUsuario;
    private Textbox apellidoUsuario;
    private Textbox telefonoUsuario;

    @Override
    public void doAfterCompose(Component comp) throws Exception {

        super.doAfterCompose(comp);
        cargarUsuarios();
    }

    public static DataSource getDataSource() throws NamingException {
        Context initialContext = new InitialContext();

        DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/java");

        return dataSource;
    }


    private void cargarUsuarios() {
        List<Usuario> list = null;
        try {
            list = UsuarioDao.getInstance().mostrarUsuarios();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        if (list != null && list.size() > 0) {

            listboxUsuarios.getItems().clear();

            Listitem listitem;
            Listcell listcell;

            for (Usuario usuario1: list) {

                listitem = new Listitem();
                listitem.setParent(listboxUsuarios);
                listitem.setValue(usuario1);

                listcell = new Listcell();
                listcell.setParent(listitem);
                listcell.setLabel(usuario1.getNombre());

                listcell = new Listcell();
                listcell.setParent(listitem);
                listcell.setLabel(usuario1.getApellido());

                listcell = new Listcell();
                listcell.setParent(listitem);
                listcell.setLabel(usuario1.getTelefono());

//                listcell = new Listcell();
//                listcell.setParent(listitem);
//                listcell.setLabel(String.valueOf(usuario1.getId()));

                listcell = new Listcell();
                listcell.setAttribute("data", listcell);
                ComponentsCtrl.applyForward(listcell, "onDoubleClick=onDoubleClicked");
            }

            listboxUsuarios.invalidate();
        }
    }

    public void onClick$btnGuardar(Event event){
        guardarUsuario();
    }
    private void guardarUsuario() {
        Usuario usuario1 = new Usuario();

        usuario1.setNombre(nombreUsuario.getValue().trim());
        usuario1.setApellido(apellidoUsuario.getValue().trim());
        usuario1.setTelefono(telefonoUsuario.getValue().trim());

        int result;

        try {
            result = UsuarioDao.getInstance().insertaUsuario(usuario1);

            if(result == 0) {
                Notification.show("No Registrado");
                System.out.println("Error");
            }
            else {
                Notification.show("Usuario Registrado");
                System.out.println("Insertado");
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public void onDoubleClicked(Event event) throws Exception {
        Listitem listitem = listboxUsuarios.getSelectedItem();
        Usuario usuario = (Usuario) listitem.getAttribute("data");

        nombreUsuario.setText(usuario.getNombre());
        apellidoUsuario.setText(usuario.getApellido());
        telefonoUsuario.setText(usuario.getTelefono());
    }


    public void onClick$btnModificar(Event event){
        actualizarUsuario();
    }

    private void actualizarUsuario() {
        Usuario usuario1 = new Usuario();

        usuario1.setNombre(nombreUsuario.getValue().trim());
        usuario1.setApellido(apellidoUsuario.getValue().trim());
        usuario1.setTelefono(telefonoUsuario.getValue().trim());

        int result;

        try {
            result = UsuarioDao.getInstance().actualizaUsuario(usuario1);

            if(result == 0) {
                Notification.show("No Registrado");
                System.out.println("Error");
            }
            else {
                Notification.show("Usuario Actualizado");
                System.out.println("Actualizado");
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public void onClick$btnEliminar(Event event) {
        eliminaUsuario();
    }

    private void eliminaUsuario() {

    }
}
