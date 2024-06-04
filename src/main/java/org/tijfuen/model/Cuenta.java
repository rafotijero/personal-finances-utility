package org.tijfuen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta {

    private int id;
    private String nombre;
    private String banco;
    private CategoriaCuenta categoria;
    private Moneda moneda; // Nuevo atributo de tipo Moneda
    private double saldo;
    private int eliminado; // Flag eliminado

    @Override
    public String toString() {
        return "Cuenta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", banco='" + banco + '\'' +
                ", categoria=" + categoria.getNombre() + // Mostrar el nombre de la categoría de cuenta
                ", moneda=" + moneda.getNombre() + // Mostrar el nombre de la moneda
                ", saldo=" + saldo +
                ", estado=" + (eliminado == 0 ? "activo" : "eliminado") +
                '}';
    }

}
