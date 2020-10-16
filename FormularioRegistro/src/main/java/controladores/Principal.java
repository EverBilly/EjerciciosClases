package controladores;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

public class Principal extends GenericForwardComposer {

    Label lblPrueba;
    public void onClick$pruebaClick(Event event) {
        Messagebox.show("Bienvenido al Controlador " + lblPrueba.getValue(),
                "Controlador", Messagebox.OK, Messagebox.INFORMATION);
    }
}
