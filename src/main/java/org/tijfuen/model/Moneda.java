package org.tijfuen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Moneda {
    private int id;
    private String nombre;
    private String simbolo;
    private boolean esMonedaLocal;
    private double tipoCambio;
    private int eliminado; // Flag eliminado

    @Override
    public String toString() {
        return "Moneda{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", simbolo='" + simbolo + '\'' +
                ", esMonedaLocal=" + esMonedaLocal +
                ", tipoCambio=" + tipoCambio +
                ", estado=" + (eliminado == 0 ? "activo" : "eliminado") +
                '}';
    }
}
