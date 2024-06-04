package org.tijfuen.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaCuenta {

    private int id;
    private String nombre;
    private int eliminado;

    @Override
    public String toString() {
        return "CategoriaCuenta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado=" + (eliminado == 0 ? "activo" : "eliminado") +
                '}';
    }
}
