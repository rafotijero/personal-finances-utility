package org.tijfuen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaGasto {

    private int id;
    private String nombre;
    private int eliminado;

    @Override
    public String toString() {
        return "CategoriaGasto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado=" + (eliminado == 0 ? "activo" : "eliminado") +
                '}';
    }

}
