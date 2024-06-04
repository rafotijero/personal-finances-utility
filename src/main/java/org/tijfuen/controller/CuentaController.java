package org.tijfuen.controller;

import org.tijfuen.model.Cuenta;
import org.tijfuen.service.CuentaService;
import org.tijfuen.service.MonedaService;

import java.util.Map;

public class CuentaController {
    private final CuentaService cuentaService;
    private final MonedaService monedaService;

    public CuentaController() {
        cuentaService = new CuentaService();
        monedaService = new MonedaService();
    }

    public CuentaController(CuentaService cuentaService, MonedaService monedaService) {
        this.cuentaService = cuentaService;
        this.monedaService = monedaService;
    }

    public String addCuenta(Cuenta cuenta) {
        // Verificar si la moneda de la cuenta existe
        if (monedaService.getMonedaById(cuenta.getMoneda().getId()) == null) {
            return "La moneda especificada no existe.";
        }

        return cuentaService.addCuenta(cuenta);
    }

    public void updateCuenta(Cuenta cuenta) {
        // Verificar si la cuenta que se está actualizando existe
        if (cuentaService.getCuentaById(cuenta.getId()) == null) {
            System.out.println("La cuenta que intenta actualizar no existe.");
            return;
        }

        // Verificar si la moneda de la cuenta existe
        if (monedaService.getMonedaById(cuenta.getMoneda().getId()) == null) {
            System.out.println("La moneda especificada no existe.");
            return;
        }

        cuentaService.updateCuenta(cuenta);
    }

    public void deleteCuenta(int id) {
        cuentaService.deleteCuenta(id);
    }

    public Cuenta getCuentaById(int id) {
        return cuentaService.getCuentaById(id);
    }

    public Map<Integer, Cuenta> getAllCuentas() {
        return cuentaService.getCuentas();
    }
}
