package org.tijfuen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingreso {
    private int id;
    private String nombre;
    private CategoriaIngreso categoria;
    private Moneda moneda;
    private double monto;
    private Date fecha;
    private String nota;
    private int eliminado;

    @Override
    public String toString() {
        return "Ingreso{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria=" + categoria.getNombre() +
                ", moneda=" + moneda.getNombre() +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", nota='" + nota + '\'' +
                ", estado=" + (eliminado == 0 ? "activo" : "eliminado") +
                '}';
    }

}
