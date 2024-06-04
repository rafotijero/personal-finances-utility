package org.tijfuen.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.tijfuen.model.Ingreso;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
public class IngresoService {
    private Map<Integer, Ingreso> ingresoStore = new HashMap<>();
    private Gson gson = new Gson();
    private String filename = "data/data_ingreso.json";
    private CategoriaIngresoService categoriaIngresoService; // Agregar referencia al servicio de categoría de cuenta
    private MonedaService monedaService; // Agregar referencia al servicio de moneda

    public IngresoService() {
        cargarIngresosDesdeArchivo();
    }

    public IngresoService(CategoriaIngresoService categoriaIngresoService, MonedaService monedaService) {
        this.categoriaIngresoService = categoriaIngresoService;
        this.monedaService = monedaService;
    }

    public Map<Integer, Ingreso> getIngresoStore() {
        return ingresoStore;
    }

    public String addIngreso(Ingreso ingreso) {
        int newId = ingresoStore.size() + 1;
        ingreso.setId(newId); // Asignar el nuevo ID

        // Verificar si la categoría de ingreso existe
        if (categoriaIngresoService.getCategoriaIngresoById(ingreso.getCategoria().getId()) == null) {
            return "La categoría de ingreso especificada no existe.";
        }

        // Verificar si la moneda existe
        if (monedaService.getMonedaById(ingreso.getMoneda().getId()) == null) {
            return "La moneda especificada en el ingreso no existe.";
        }

        if (ingresoStore.containsKey(newId)) { // Verificar el nuevo ID
            return "Ingreso con ID " + newId + " ya existe.";
        }
        ingresoStore.put(newId, ingreso); // Usar el nuevo ID
        guardarIngresosEnArchivo();
        return "Ingreso añadido.";
    }

    public void updateIngreso(Ingreso ingreso) {
        // Verificar si el ingreso que se está actualizando existe
        if (!ingresoStore.containsKey(ingreso.getId())) {
            System.out.println("El ingreso que intenta actualizar no existe.");
            return;
        }

        // Verificar si la categoría de ingreso especificada existe
        if (categoriaIngresoService.getCategoriaIngresoById(ingreso.getCategoria().getId()) == null) {
            System.out.println("La categoría de ingreso especificada no existe.");
            return;
        }

        // Verificar si la moneda especificada existe
        if (monedaService.getMonedaById(ingreso.getMoneda().getId()) == null) {
            System.out.println("La moneda especificada en el ingreso no existe.");
            return;
        }

        ingresoStore.put(ingreso.getId(), ingreso);
        guardarIngresosEnArchivo();
    }

    public void deleteIngreso(int id) {
        Ingreso ingreso = ingresoStore.get(id);
        if (ingreso != null) {
            ingreso.setEliminado(1); // Marcar como eliminado
            guardarIngresosEnArchivo();
        }
    }

    public Ingreso getIngresoById(int id) {
        return ingresoStore.get(id);
    }

    public Map<Integer, Ingreso> getIngresos() {
        return ingresoStore;
    }

    private void cargarIngresosDesdeArchivo() {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Archivo de ingresos no encontrado, creando nuevo archivo.");
            ingresoStore = new HashMap<>();
            return;
        }

        try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            Type collectionType = new TypeToken<HashMap<Integer, Ingreso>>() {}.getType();
            ingresoStore = gson.fromJson(reader, collectionType);
            if (ingresoStore == null) {
                ingresoStore = new HashMap<>();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de ingresos no encontrado, creando nuevo archivo.");
            ingresoStore = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarIngresosEnArchivo() {
        try (Writer writer = new FileWriter(filename, StandardCharsets.UTF_8)) {
            gson.toJson(ingresoStore, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}