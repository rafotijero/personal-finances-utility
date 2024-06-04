package org.tijfuen.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.tijfuen.model.CategoriaGasto;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CategoriaGastoService {

    private final Map<Integer, CategoriaGasto> categoriaGastoStore = new HashMap<>();
    private final Gson gson = new Gson();
    private final String filename = "data/data_categoriagasto.json";

    public CategoriaGastoService() {
        cargarCategoriasGastoDesdeArchivo();
    }

    public String addCategoriaGasto(CategoriaGasto categoria) {
        int newId = categoriaGastoStore.size() + 1;
        categoria.setId(newId);
        if (categoriaGastoStore.containsKey(newId)) {
            return "Categoría de gasto con ID " + newId + " ya existe.";
        }
        categoriaGastoStore.put(newId, categoria);
        guardarCategoriasGastoEnArchivo();
        return "Categoría de gasto añadida.";
    }

    public void updateCategoriaGasto(CategoriaGasto categoria) {
        if (categoriaGastoStore.containsKey(categoria.getId())) {
            categoriaGastoStore.put(categoria.getId(), categoria);
            guardarCategoriasGastoEnArchivo();
        }
    }

    public void deleteCategoriaGasto(int id) {
        CategoriaGasto categoria = categoriaGastoStore.get(id);
        if (categoria != null) {
            categoria.setEliminado(1);
            guardarCategoriasGastoEnArchivo();
        }
    }

    public CategoriaGasto getCategoriaGastoById(int id) {
        return categoriaGastoStore.get(id);
    }

    public Map<Integer, CategoriaGasto> getAllCategoriasGasto() {
        Map<Integer, CategoriaGasto> activas = new HashMap<>();
        for (Map.Entry<Integer, CategoriaGasto> entry : categoriaGastoStore.entrySet()) {
            if (entry.getValue().getEliminado() == 0) {
                activas.put(entry.getKey(), entry.getValue());
            }
        }
        return activas;
    }

    private void cargarCategoriasGastoDesdeArchivo() {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Archivo de categorías de gasto no encontrado, creando nuevo archivo.");
            categoriaGastoStore.clear();
            return;
        }

        try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            Type collectionType = new TypeToken<HashMap<Integer, CategoriaGasto>>() {}.getType();
            categoriaGastoStore.clear();
            categoriaGastoStore.putAll(gson.fromJson(reader, collectionType));
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de categorías de gasto no encontrado, creando nuevo archivo.");
            categoriaGastoStore.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarCategoriasGastoEnArchivo() {
        try (Writer writer = new FileWriter(filename, StandardCharsets.UTF_8)) {
            gson.toJson(categoriaGastoStore, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
