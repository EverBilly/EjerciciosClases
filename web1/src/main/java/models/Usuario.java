package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Usuario {
    private String nombre;
    private String apellido;
    private String telefono;
    private int id;
}
