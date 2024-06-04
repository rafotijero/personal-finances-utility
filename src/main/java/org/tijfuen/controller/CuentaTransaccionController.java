package org.tijfuen.controller;

import org.tijfuen.model.CuentaTransaccion;
import org.tijfuen.service.CuentaTransaccionService;

import java.util.Map;

public class CuentaTransaccionController {

    private final CuentaTransaccionService transaccionService;

    public CuentaTransaccionController() {
        this.transaccionService = new CuentaTransaccionService();
    }
    public CuentaTransaccionController(CuentaTransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    public String addTransaccion(CuentaTransaccion transaccion) {
        return transaccionService.addTransaccion(transaccion);
    }

    public void updateTransaccion(CuentaTransaccion transaccion) {
        transaccionService.updateTransaccion(transaccion);
    }

    public void deleteTransaccion(int id) {
        transaccionService.deleteTransaccion(id);
    }

    public CuentaTransaccion getTransaccionById(int id) {
        return transaccionService.getTransaccionById(id);
    }

    public Map<Integer, CuentaTransaccion> getAllTransacciones() {
        return transaccionService.getAllTransacciones();
    }

    public void procesarTransacciones() {
        transaccionService.procesarTransacciones();
    }
}
