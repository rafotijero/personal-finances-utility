package org.tijfuen.controller;

import org.tijfuen.model.CategoriaCuenta;
import org.tijfuen.service.CategoriaCuentaService;

import java.util.Map;

public class CategoriaCuentaController {

    private CategoriaCuentaService service;

    public CategoriaCuentaController() {
        this.service = new CategoriaCuentaService();
    }
    public CategoriaCuentaController(CategoriaCuentaService service) {
        this.service = service;
    }

    public String addCategoria(CategoriaCuenta categoria) {
        return service.addCategoria(categoria);
    }

    public Map<Integer, CategoriaCuenta> getAllCategorias() {
        return service.getCategoriasActivas();
    }

    public CategoriaCuenta getCategoriaById(int id) {
        return service.getCategoriaById(id);
    }

    public void updateCategoria(CategoriaCuenta categoria) {
        service.updateCategoria(categoria);
    }

    public void deleteCategoria(int id) {
        service.deleteCategoria(id);
    }

}
