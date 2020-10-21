package com.ever;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listitem;

public class MyViewModel {

	private int count;
	private Label nombre;
	private Listitem itemList;

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

	@Command
	public void cambiarNombre() {
		nombre.setValue("Desde EL Boton");
	}
}
