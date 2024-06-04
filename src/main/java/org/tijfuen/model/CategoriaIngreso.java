package org.tijfuen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaIngreso {
    private int id;
    private String nombre;
    private int eliminado;

    @Override
    public String toString() {
        return "CategoriaIngreso{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado=" + (eliminado == 0 ? "activo" : "eliminado") +
                '}';
    }
}
