package org.tijfuen.controller;

import org.tijfuen.model.CategoriaGasto;
import org.tijfuen.service.CategoriaGastoService;

import java.util.Map;

public class CategoriaGastoController {

    private final CategoriaGastoService service;

    public CategoriaGastoController() {
        this.service = new CategoriaGastoService();
    }

    public CategoriaGastoController(CategoriaGastoService service) {
        this.service = service;
    }

    public String addCategoriaGasto(CategoriaGasto categoria) {
        return service.addCategoriaGasto(categoria);
    }

    public Map<Integer, CategoriaGasto> getAllCategoriasGasto() {
        return service.getAllCategoriasGasto();
    }

    public CategoriaGasto getCategoriaGastoById(int id) {
        return service.getCategoriaGastoById(id);
    }

    public void updateCategoriaGasto(CategoriaGasto categoria) {
        service.updateCategoriaGasto(categoria);
    }

    public void deleteCategoriaGasto(int id) {
        service.deleteCategoriaGasto(id);
    }

}
