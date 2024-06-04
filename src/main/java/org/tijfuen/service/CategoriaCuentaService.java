package org.tijfuen.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.tijfuen.model.CategoriaCuenta;
import org.tijfuen.util.FileUtil;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CategoriaCuentaService {
    private Map<Integer, CategoriaCuenta> categoriaStore = new HashMap<>();
    private Gson gson = new Gson();
    private String filename = "data/data_categoriacuenta.json";

    public CategoriaCuentaService() {
        cargarCategoriasDesdeArchivo();
    }

    public CategoriaCuentaService(String filename) {
        this.filename = filename;
        cargarCategoriasDesdeArchivo();
    }

    public Map<Integer, CategoriaCuenta> getCategoriaStore() {
        return categoriaStore;
    }

    public String addCategoria(CategoriaCuenta categoria) {
        int newId = categoriaStore.size() + 1;
        categoria.setId(newId); // Asignar el nuevo ID
        if (categoriaStore.containsKey(newId)) { // Verificar el nuevo ID
            return "Categoría con ID " + newId + " ya existe.";
        }
        categoriaStore.put(newId, categoria); // Usar el nuevo ID
        guardarCategoriasEnArchivo();
        return "Categoría añadida.";
    }

    public void updateCategoria(CategoriaCuenta categoria) {
        if (categoriaStore.containsKey(categoria.getId())) {
            categoriaStore.put(categoria.getId(), categoria);
            guardarCategoriasEnArchivo();
        }
    }

    public void deleteCategoria(int id) {
        CategoriaCuenta categoria = categoriaStore.get(id);
        if (categoria != null) {
            categoria.setEliminado(1); // Marcar como eliminado
            guardarCategoriasEnArchivo();
        }
    }

    public CategoriaCuenta getCategoriaById(int id) {
        return categoriaStore.get(id);
    }

    public Map<Integer, CategoriaCuenta> getCategoriasActivas() {
        Map<Integer, CategoriaCuenta> activas = new HashMap<>();
        for (Map.Entry<Integer, CategoriaCuenta> entry : categoriaStore.entrySet()) {
            if (entry.getValue().getEliminado() == 0) {
                activas.put(entry.getKey(), entry.getValue());
            }
        }
        return activas;
    }

    private void cargarCategoriasDesdeArchivo() {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Archivo de categorías no encontrado, creando nuevo archivo.");
            categoriaStore = new HashMap<>();
            return;
        }

        try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            Type collectionType = new TypeToken<HashMap<Integer, CategoriaCuenta>>() {}.getType();
            categoriaStore = gson.fromJson(reader, collectionType);
            if (categoriaStore == null) {
                categoriaStore = new HashMap<>();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de categorías no encontrado, creando nuevo archivo.");
            categoriaStore = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarCategoriasEnArchivo() {
        try (Writer writer = new FileWriter(filename, StandardCharsets.UTF_8)) {
            gson.toJson(categoriaStore, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
