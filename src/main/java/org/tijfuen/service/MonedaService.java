package org.tijfuen.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.tijfuen.model.Moneda;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MonedaService {
    private Map<Integer, Moneda> monedaStore = new HashMap<>();
    private Gson gson = new Gson();
    private String filename = "data/data_moneda.json";

    public MonedaService() {
        cargarMonedasDesdeArchivo();
    }

    public MonedaService(String filename) {
        this.filename = filename;
        cargarMonedasDesdeArchivo();
    }

    public Map<Integer, Moneda> getMonedaStore() {
        return monedaStore;
    }

    public String addMoneda(Moneda moneda) {
        // Verificar si la moneda que se va a agregar es local y si ya existe una moneda local en el sistema
        if (moneda.isEsMonedaLocal()) {
            for (Moneda m : monedaStore.values()) {
                if (m.isEsMonedaLocal()) {
                    return "Ya existe una moneda local en el sistema.";
                }
            }
            // Si no hay una moneda local en el sistema, se verifica que el tipo de cambio sea 1
            if (moneda.getTipoCambio() != 1.0) {
                return "El tipo de cambio de la moneda local debe ser 1.";
            }
        }

        int newId = monedaStore.size() + 1;
        moneda.setId(newId); // Asignar el nuevo ID
        if (monedaStore.containsKey(newId)) { // Verificar el nuevo ID
            return "Moneda con ID " + newId + " ya existe.";
        }
        monedaStore.put(newId, moneda); // Usar el nuevo ID
        guardarMonedasEnArchivo();
        return "Moneda añadida.";
    }

    public void updateMoneda(Moneda moneda) {
        // Verificar si la moneda que se va a actualizar es local y si ya existe una moneda local en el sistema
        if (moneda.isEsMonedaLocal()) {
            for (Moneda m : monedaStore.values()) {
                if (m.getId() != moneda.getId() && m.isEsMonedaLocal()) {
                    System.out.println("Ya existe una moneda local en el sistema.");
                    return;
                }
            }
            // Si no hay una moneda local en el sistema, se verifica que el tipo de cambio sea 1
            if (moneda.getTipoCambio() != 1.0) {
                System.out.println("El tipo de cambio de la moneda local debe ser 1.");
                return;
            }
        }

        if (!monedaStore.containsKey(moneda.getId())) {
            System.out.println("La moneda que intenta actualizar no existe.");
            return;
        }

        monedaStore.put(moneda.getId(), moneda);
        guardarMonedasEnArchivo();
    }

    public void deleteMoneda(int id) {
        Moneda moneda = monedaStore.get(id);
        if (moneda != null) {
            moneda.setEliminado(1); // Marcar como eliminado
            monedaStore.put(id, moneda); // Actualizar en el mapa
            guardarMonedasEnArchivo();
        }
    }

    public Map<Integer, Moneda> getAllMonedas() {
        Map<Integer, Moneda> monedasActivas = new HashMap<>();
        for (Map.Entry<Integer, Moneda> entry : monedaStore.entrySet()) {
            if (entry.getValue().getEliminado() == 0) {
                monedasActivas.put(entry.getKey(), entry.getValue());
            }
        }
        return monedasActivas;
    }

    public Moneda getMonedaById(int id) {
        return monedaStore.get(id);
    }

    public Map<Integer, Moneda> getMonedas() {
        return monedaStore;
    }

    private void cargarMonedasDesdeArchivo() {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Archivo de monedas no encontrado, creando nuevo archivo.");
            monedaStore = new HashMap<>();
            return;
        }

        try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            Type collectionType = new TypeToken<HashMap<Integer, Moneda>>() {}.getType();
            monedaStore = gson.fromJson(reader, collectionType);
            if (monedaStore == null) {
                monedaStore = new HashMap<>();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de monedas no encontrado, creando nuevo archivo.");
            monedaStore = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarMonedasEnArchivo() {
        try (Writer writer = new FileWriter(filename, StandardCharsets.UTF_8)) {
            gson.toJson(monedaStore, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean existeMonedaLocal() {
        for (Moneda moneda : monedaStore.values()) {
            if (moneda.isEsMonedaLocal()) {
                return true;
            }
        }
        return false;
    }

}
