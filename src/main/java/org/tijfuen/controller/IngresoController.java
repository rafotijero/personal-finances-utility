package org.tijfuen.controller;

import org.tijfuen.model.Ingreso;
import org.tijfuen.service.IngresoService;

import java.util.Map;

public class IngresoController {

    private final IngresoService ingresoService;

    public IngresoController() {
        this.ingresoService = new IngresoService();
    }

    public IngresoController(IngresoService ingresoService) {
        this.ingresoService = ingresoService;
    }

    public String addIngreso(Ingreso ingreso) {
        return ingresoService.addIngreso(ingreso);
    }

    public void updateIngreso(Ingreso ingreso) {
        ingresoService.updateIngreso(ingreso);
    }

    public void deleteIngreso(int id) {
        ingresoService.deleteIngreso(id);
    }

    public Ingreso getIngresoById(int id) {
        return ingresoService.getIngresoById(id);
    }

    public Map<Integer, Ingreso> getAllIngresos() {
        return ingresoService.getIngresos();
    }

}
