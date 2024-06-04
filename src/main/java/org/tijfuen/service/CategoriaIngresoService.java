package org.tijfuen.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.tijfuen.model.CategoriaIngreso;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CategoriaIngresoService {

    private final Map<Integer, CategoriaIngreso> categoriaIngresoStore = new HashMap<>();
    private final Gson gson = new Gson();
    private final String filename = "data/data_categoriaingreso.json";

    public CategoriaIngresoService() {
        cargarCategoriasIngresoDesdeArchivo();
    }

    public String addCategoriaIngreso(CategoriaIngreso categoria) {
        int newId = categoriaIngresoStore.size() + 1;
        categoria.setId(newId);
        if (categoriaIngresoStore.containsKey(newId)) {
            return "Categoría de ingreso con ID " + newId + " ya existe.";
        }
        categoriaIngresoStore.put(newId, categoria);
        guardarCategoriasIngresoEnArchivo();
        return "Categoría de ingreso añadida.";
    }

    public void updateCategoriaIngreso(CategoriaIngreso categoria) {
        if (categoriaIngresoStore.containsKey(categoria.getId())) {
            categoriaIngresoStore.put(categoria.getId(), categoria);
            guardarCategoriasIngresoEnArchivo();
        }
    }

    public void deleteCategoriaIngreso(int id) {
        CategoriaIngreso categoria = categoriaIngresoStore.get(id);
        if (categoria != null) {
            categoria.setEliminado(1);
            guardarCategoriasIngresoEnArchivo();
        }
    }

    public CategoriaIngreso getCategoriaIngresoById(int id) {
        return categoriaIngresoStore.get(id);
    }

    public Map<Integer, CategoriaIngreso> getAllCategoriasIngreso() {
        Map<Integer, CategoriaIngreso> activas = new HashMap<>();
        for (Map.Entry<Integer, CategoriaIngreso> entry : categoriaIngresoStore.entrySet()) {
            if (entry.getValue().getEliminado() == 0) {
                activas.put(entry.getKey(), entry.getValue());
            }
        }
        return activas;
    }

    private void cargarCategoriasIngresoDesdeArchivo() {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Archivo de categorías de ingreso no encontrado, creando nuevo archivo.");
            categoriaIngresoStore.clear();
            return;
        }

        try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            Type collectionType = new TypeToken<HashMap<Integer, CategoriaIngreso>>() {}.getType();
            categoriaIngresoStore.clear();
            categoriaIngresoStore.putAll(gson.fromJson(reader, collectionType));
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de categorías de ingreso no encontrado, creando nuevo archivo.");
            categoriaIngresoStore.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarCategoriasIngresoEnArchivo() {
        try (Writer writer = new FileWriter(filename, StandardCharsets.UTF_8)) {
            gson.toJson(categoriaIngresoStore, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
