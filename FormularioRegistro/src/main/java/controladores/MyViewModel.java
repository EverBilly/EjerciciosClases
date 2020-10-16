package controladores;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

public class MyViewModel extends SelectorComposer<Component> {

	private int count;

	@Wire
	private Button btnGuardar;
	private Button pruebaClick;

	@Wire
	private Checkbox aceptarTerminos;

	@Init
	public void init() {
		count = 100;
	}

	@Command
	@NotifyChange("count")
	public void cmd() {
		++count;
	}

	public int getCount() {
		return count;
	}

	@Listen("onCheck = #aceptarTerminos")
	public void cambiarEstadoGuardar() {
		if(aceptarTerminos.isChecked()) {
			btnGuardar.setDisabled(false);
		}
		else {
			btnGuardar.setDisabled(true);
		}
	}

	@Listen("onClick = #pruebaClick")
	public void mostrarMensaje() {
		Messagebox.show("Controlador");
	}

}
