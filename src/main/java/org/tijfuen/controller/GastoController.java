package org.tijfuen.controller;

import org.tijfuen.model.Gasto;
import org.tijfuen.service.GastoService;

import java.util.Map;

public class GastoController {

    private final GastoService gastoService;

    public GastoController() {
        this.gastoService = new GastoService();
    }
    public GastoController(GastoService gastoService) {
        this.gastoService = gastoService;
    }

    public String addGasto(Gasto gasto) {
        return gastoService.addGasto(gasto);
    }

    public void updateGasto(Gasto gasto) {
        gastoService.updateGasto(gasto);
    }

    public void deleteGasto(int id) {
        gastoService.deleteGasto(id);
    }

    public Gasto getGastoById(int id) {
        return gastoService.getGastoById(id);
    }

    public Map<Integer, Gasto> getAllGastos() {
        return gastoService.getGastos();
    }
}
