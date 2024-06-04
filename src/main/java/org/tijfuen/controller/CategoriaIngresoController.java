package org.tijfuen.controller;

import org.tijfuen.model.CategoriaIngreso;
import org.tijfuen.service.CategoriaIngresoService;

import java.util.Map;

public class CategoriaIngresoController {

    private final CategoriaIngresoService service;

    public CategoriaIngresoController() {
        this.service = new CategoriaIngresoService();
    }
    public CategoriaIngresoController(CategoriaIngresoService service) {
        this.service = service;
    }

    public String addCategoriaIngreso(CategoriaIngreso categoria) {
        return service.addCategoriaIngreso(categoria);
    }

    public Map<Integer, CategoriaIngreso> getAllCategoriasIngreso() {
        return service.getAllCategoriasIngreso();
    }

    public CategoriaIngreso getCategoriaIngresoById(int id) {
        return service.getCategoriaIngresoById(id);
    }

    public void updateCategoriaIngreso(CategoriaIngreso categoria) {
        service.updateCategoriaIngreso(categoria);
    }

    public void deleteCategoriaIngreso(int id) {
        service.deleteCategoriaIngreso(id);
    }
}
