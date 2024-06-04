package org.tijfuen.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.tijfuen.model.Cuenta;
import org.tijfuen.model.Moneda;
import org.tijfuen.util.FileUtil;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CuentaService {
    private Map<Integer, Cuenta> cuentaStore = new HashMap<>();
    private Gson gson = new Gson();
    private String filename = "data/data_cuenta.json";

    private CategoriaCuentaService categoriaCuentaService; // Agregar referencia al servicio de categoría de cuenta
    private MonedaService monedaService; // Agregar referencia al servicio de moneda

    public CuentaService() {
        cargarCuentasDesdeArchivo();
        categoriaCuentaService = new CategoriaCuentaService();
        monedaService = new MonedaService();
    }

    public CuentaService(CategoriaCuentaService categoriaCuentaService) {
        this.categoriaCuentaService = categoriaCuentaService;
        cargarCuentasDesdeArchivo();
    }

    public CuentaService(CategoriaCuentaService categoriaCuentaService, MonedaService monedaService) {
        this.categoriaCuentaService = categoriaCuentaService;
        this.monedaService = monedaService;
        cargarCuentasDesdeArchivo();
    }

    public CuentaService(CategoriaCuentaService categoriaCuentaService, String filename) {
        this.categoriaCuentaService = categoriaCuentaService;
        this.filename = filename;
        cargarCuentasDesdeArchivo();
    }

    public CuentaService(String filename) {
        this.filename = filename;
        cargarCuentasDesdeArchivo();
    }

    public Map<Integer, Cuenta> getCuentaStore() {
        return cuentaStore;
    }

    public String addCuenta(Cuenta cuenta) {
        int newId = cuentaStore.size() + 1;
        cuenta.setId(newId); // Asignar el nuevo ID

        // Verificar si la categoría de cuenta existe
        if (categoriaCuentaService.getCategoriaById(cuenta.getCategoria().getId()) == null) {
            return "La categoría de cuenta especificada no existe.";
        }

        // Verificar si la moneda de la cuenta existe
        Moneda moneda = cuenta.getMoneda();
        if (monedaService.getMonedaById(moneda.getId()) == null) {
            return "La moneda especificada en la cuenta no existe.";
        }

        if (cuentaStore.containsKey(newId)) { // Verificar el nuevo ID
            return "Cuenta con ID " + newId + " ya existe.";
        }
        cuentaStore.put(newId, cuenta); // Usar el nuevo ID
        guardarCuentasEnArchivo();
        return "Cuenta añadida.";
    }

    public void updateCuenta(Cuenta cuenta) {
        // Verificar si la cuenta que se está actualizando existe
        if (!cuentaStore.containsKey(cuenta.getId())) {
            System.out.println("La cuenta que intenta actualizar no existe.");
            return;
        }

        // Verificar si la categoría de cuenta especificada existe
        if (categoriaCuentaService.getCategoriaById(cuenta.getCategoria().getId()) == null) {
            System.out.println("La categoría de cuenta especificada no existe.");
            return;
        }

        // Verificar si la moneda de la cuenta especificada existe
        Moneda moneda = cuenta.getMoneda();
        if (monedaService.getMonedaById(moneda.getId()) == null) {
            System.out.println("La moneda especificada en la cuenta no existe.");
            return;
        }

        cuentaStore.put(cuenta.getId(), cuenta);
        guardarCuentasEnArchivo();
    }

    public void deleteCuenta(int id) {
        Cuenta cuenta = cuentaStore.get(id);
        if (cuenta != null) {
            cuenta.setEliminado(1); // Marcar como eliminado
            guardarCuentasEnArchivo();
        }
    }

    public Cuenta getCuentaById(int id) {
        return cuentaStore.get(id);
    }

    public Map<Integer, Cuenta> getCuentas() {
        return cuentaStore;
    }

    private void cargarCuentasDesdeArchivo() {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Archivo de cuentas no encontrado, creando nuevo archivo.");
            cuentaStore = new HashMap<>();
            return;
        }

        try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            Type collectionType = new TypeToken<HashMap<Integer, Cuenta>>() {}.getType();
            cuentaStore = gson.fromJson(reader, collectionType);
            if (cuentaStore == null) {
                cuentaStore = new HashMap<>();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de cuentas no encontrado, creando nuevo archivo.");
            cuentaStore = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarCuentasEnArchivo() {
        try (Writer writer = new FileWriter(filename, StandardCharsets.UTF_8)) {
            gson.toJson(cuentaStore, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
