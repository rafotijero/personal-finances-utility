package org.tijfuen.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.tijfuen.model.Gasto;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GastoService {
    private Map<Integer, Gasto> gastoStore = new HashMap<>();
    private Gson gson = new Gson();
    private String filename = "data/data_gasto.json";
    private CategoriaGastoService categoriaGastoService; // Agregar referencia al servicio de categoría de gasto
    private MonedaService monedaService; // Agregar referencia al servicio de moneda

    public GastoService() {
        cargarGastosDesdeArchivo();
        categoriaGastoService = new CategoriaGastoService();
        monedaService = new MonedaService();
    }

    public GastoService(CategoriaGastoService categoriaGastoService, MonedaService monedaService) {
        this.categoriaGastoService = categoriaGastoService;
        this.monedaService = monedaService;
    }

    public Map<Integer, Gasto> getGastoStore() {
        return gastoStore;
    }

    public String addGasto(Gasto gasto) {
        int newId = gastoStore.size() + 1;
        gasto.setId(newId); // Asignar el nuevo ID

        // Verificar si la categoría de gasto existe
        if (categoriaGastoService.getCategoriaGastoById(gasto.getCategoria().getId()) == null) {
            return "La categoría de gasto especificada no existe.";
        }

        // Verificar si la moneda existe
        if (monedaService.getMonedaById(gasto.getMoneda().getId()) == null) {
            return "La moneda especificada en el gasto no existe.";
        }

        if (gastoStore.containsKey(newId)) { // Verificar el nuevo ID
            return "Gasto con ID " + newId + " ya existe.";
        }
        gastoStore.put(newId, gasto); // Usar el nuevo ID
        guardarGastosEnArchivo();
        return "Gasto añadido.";
    }

    public void updateGasto(Gasto gasto) {
        // Verificar si el gasto que se está actualizando existe
        if (!gastoStore.containsKey(gasto.getId())) {
            System.out.println("El gasto que intenta actualizar no existe.");
            return;
        }

        // Verificar si la categoría de gasto especificada existe
        if (categoriaGastoService.getCategoriaGastoById(gasto.getCategoria().getId()) == null) {
            System.out.println("La categoría de gasto especificada no existe.");
            return;
        }

        // Verificar si la moneda especificada existe
        if (monedaService.getMonedaById(gasto.getMoneda().getId()) == null) {
            System.out.println("La moneda especificada en el gasto no existe.");
            return;
        }

        gastoStore.put(gasto.getId(), gasto);
        guardarGastosEnArchivo();
    }

    public void deleteGasto(int id) {
        Gasto gasto = gastoStore.get(id);
        if (gasto != null) {
            gasto.setEliminado(1); // Marcar como eliminado
            guardarGastosEnArchivo();
        }
    }

    public Gasto getGastoById(int id) {
        return gastoStore.get(id);
    }

    public Map<Integer, Gasto> getGastos() {
        return gastoStore;
    }

    private void cargarGastosDesdeArchivo() {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Archivo de gastos no encontrado, creando nuevo archivo.");
            gastoStore = new HashMap<>();
            return;
        }

        try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            Type collectionType = new TypeToken<HashMap<Integer, Gasto>>() {}.getType();
            gastoStore = gson.fromJson(reader, collectionType);
            if (gastoStore == null) {
                gastoStore = new HashMap<>();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de gastos no encontrado, creando nuevo archivo.");
            gastoStore = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarGastosEnArchivo() {
        try (Writer writer = new FileWriter(filename, StandardCharsets.UTF_8)) {
            gson.toJson(gastoStore, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
