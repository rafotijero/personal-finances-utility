package org.tijfuen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaTransaccion {

    private int id;
    private Cuenta cuenta;
    private Ingreso ingreso;
    private Gasto gasto;
    private int procesado; // 0: No procesado, 1: Procesado

    public CuentaTransaccion(int id, Cuenta cuenta, Ingreso ingreso, int procesado) {
        this.id = id;
        this.cuenta = cuenta;
        this.ingreso = ingreso;
        this.procesado = procesado;
    }

    public CuentaTransaccion(int id, Cuenta cuenta, Gasto gasto, int procesado) {
        this.id = id;
        this.cuenta = cuenta;
        this.gasto = gasto;
        this.procesado = procesado;
    }

    @Override
    public String toString() {
        String ingresoNombre = (ingreso != null) ? ingreso.getNombre() : "";
        String gastoNombre = (gasto != null) ? gasto.getNombre() : "";

        return "CuentaTransaccion{" +
                "id=" + id +
                ", cuenta=" + cuenta.getNombre() +
                ", ingreso=" + ingresoNombre +
                ", gasto=" + gastoNombre +
                ", procesado=" + procesado +
                '}';
    }

}
