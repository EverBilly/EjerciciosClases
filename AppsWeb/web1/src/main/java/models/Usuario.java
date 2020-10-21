package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;

@Data @AllArgsConstructor @NoArgsConstructor
public class Usuario extends SelectorComposer<Component> {
    private String nombre;
    private String apellido;
    private String telefono;
    private int id;
}
