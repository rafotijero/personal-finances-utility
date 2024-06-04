package org.tijfuen.controller;

import org.tijfuen.model.Moneda;
import org.tijfuen.service.MonedaService;

import java.util.Map;

public class MonedaController {
    private MonedaService service;

    public MonedaController() {
        this.service = new MonedaService();
    }
    public MonedaController(MonedaService service) {
        this.service = service;
    }

    public String addMoneda(Moneda moneda) {
        return service.addMoneda(moneda);
    }

    public Map<Integer, Moneda> getAllMonedas() {
        return service.getAllMonedas();
    }

    public Moneda getMonedaById(int id) {
        return service.getMonedaById(id);
    }

    public void updateMoneda(Moneda moneda) {
        service.updateMoneda(moneda);
    }

    public void deleteMoneda(int id) {
        service.deleteMoneda(id);
    }

    public boolean existeMonedaLocal() {
        return service.existeMonedaLocal();
    }

    public boolean existeMonedaLocalExcepto(int id) {
        for (Moneda moneda : service.getAllMonedas().values()) {
            if (moneda.isEsMonedaLocal() && moneda.getId() != id) {
                return true;
            }
        }
        return false;
    }

}
