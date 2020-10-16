package modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zkoss.zk.ui.util.GenericForwardComposer;

@Data @NoArgsConstructor @AllArgsConstructor
public class Usuario extends GenericForwardComposer {

    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
}
