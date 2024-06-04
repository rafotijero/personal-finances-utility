package org.tijfuen.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.tijfuen.model.Cuenta;
import org.tijfuen.model.CuentaTransaccion;
import org.tijfuen.model.Gasto;
import org.tijfuen.model.Ingreso;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CuentaTransaccionService {
    private Map<Integer, CuentaTransaccion> transaccionStore = new HashMap<>();
    private Gson gson = new Gson();
    private String filename = "data/data_transaccion.json";
    private CuentaService cuentaService;
    private IngresoService ingresoService;
    private GastoService gastoService;

    public CuentaTransaccionService() {
        cargarTransaccionesDesdeArchivo();
        cuentaService = new CuentaService();
        ingresoService = new IngresoService();
        gastoService = new GastoService();
    }

    public Map<Integer, CuentaTransaccion> getTransaccionStore() {
        return transaccionStore;
    }

    public String addTransaccion(CuentaTransaccion transaccion) {
        int newId = transaccionStore.size() + 1;
        transaccion.setId(newId); // Asignar el nuevo ID

        // Verificar si la cuenta existe
        CuentaService cuentaService = new CuentaService();
        Cuenta cuenta = cuentaService.getCuentaById(transaccion.getCuenta().getId());
        if (cuenta == null) {
            return "La cuenta especificada no existe.";
        }

        // Verificar si el ingreso existe
        if (transaccion.getIngreso() != null) {
            IngresoService ingresoService = new IngresoService();
            Ingreso ingreso = ingresoService.getIngresoById(transaccion.getIngreso().getId());
            if (ingreso == null) {
                // Crear un ingreso vacío con monto 0 si no existe
                transaccion.setIngreso(new Ingreso(0, "", null, null, 0, null, "", 0));
            }
        }

        // Verificar si el gasto existe
        if (transaccion.getGasto() != null) {
            GastoService gastoService = new GastoService();
            Gasto gasto = gastoService.getGastoById(transaccion.getGasto().getId());
            if (gasto == null) {
                // Crear un gasto vacío con monto 0 si no existe
                transaccion.setGasto(new Gasto(0, "", null, null, 0, null, "", 0));
            }
        }

        if (transaccionStore.containsKey(newId)) { // Verificar el nuevo ID
            return "Transacción con ID " + newId + " ya existe.";
        }

        transaccionStore.put(newId, transaccion); // Usar el nuevo ID
        guardarTransaccionesEnArchivo();
        return "Transacción añadida.";
    }



    public void updateTransaccion(CuentaTransaccion transaccion) {
        // Verificar si la transacción que se está actualizando existe
        if (!transaccionStore.containsKey(transaccion.getId())) {
            System.out.println("La transacción que intenta actualizar no existe.");
            return;
        }

        // Verificar si la cuenta existe
        CuentaService cuentaService = new CuentaService();
        Cuenta cuenta = cuentaService.getCuentaById(transaccion.getCuenta().getId());
        if (cuenta == null) {
            System.out.println("La cuenta especificada no existe.");
            return;
        }

        // Verificar si el ingreso existe
        if (transaccion.getIngreso() != null) {
            IngresoService ingresoService = new IngresoService();
            Ingreso ingreso = ingresoService.getIngresoById(transaccion.getIngreso().getId());
            if (ingreso == null) {
                // Crear un ingreso vacío con monto 0 si no existe
                transaccion.setIngreso(new Ingreso(0, "", null, null, 0, null, "", 0));
            }
        }

        // Verificar si el gasto existe
        if (transaccion.getGasto() != null) {
            GastoService gastoService = new GastoService();
            Gasto gasto = gastoService.getGastoById(transaccion.getGasto().getId());
            if (gasto == null) {
                // Crear un gasto vacío con monto 0 si no existe
                transaccion.setGasto(new Gasto(0, "", null, null, 0, null, "", 0));
            }
        }

        transaccionStore.put(transaccion.getId(), transaccion);
        guardarTransaccionesEnArchivo();
        System.out.println("Transacción actualizada.");
    }

    public void deleteTransaccion(int id) {
        if (transaccionStore.containsKey(id)) {
            transaccionStore.remove(id);
            guardarTransaccionesEnArchivo();
        } else {
            System.out.println("La transacción que intenta eliminar no existe.");
        }
    }

    public CuentaTransaccion getTransaccionById(int id) {
        return transaccionStore.get(id);
    }

    public Map<Integer, CuentaTransaccion> getAllTransacciones() {
        return transaccionStore;
    }

    private void cargarTransaccionesDesdeArchivo() {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Archivo de transacciones no encontrado, creando nuevo archivo.");
            transaccionStore = new HashMap<>();
            return;
        }

        try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            Type collectionType = new TypeToken<HashMap<Integer, CuentaTransaccion>>() {}.getType();
            transaccionStore = gson.fromJson(reader, collectionType);
            if (transaccionStore == null) {
                transaccionStore = new HashMap<>();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de transacciones no encontrado, creando nuevo archivo.");
            transaccionStore = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarTransaccionesEnArchivo() {
        try (Writer writer = new FileWriter(filename, StandardCharsets.UTF_8)) {
            gson.toJson(transaccionStore, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void procesarTransacciones() {
        // Obtener todas las transacciones
        List<CuentaTransaccion> transacciones = new ArrayList<>(transaccionStore.values());

        // Iterar sobre todas las transacciones
        for (CuentaTransaccion transaccion : transacciones) {
            if (transaccion.getProcesado() == 0) { // Verificar si la transacción no ha sido procesada
                // Obtener la cuenta asociada a la transacción
                Cuenta cuenta = transaccion.getCuenta();

                // Obtener los montos de ingreso y gasto de la transacción
                double montoIngreso = (transaccion.getIngreso() != null) ? transaccion.getIngreso().getMonto() : 0;
                double montoGasto = (transaccion.getGasto() != null) ? transaccion.getGasto().getMonto() : 0;

                // Actualizar el saldo de la cuenta
                Cuenta cuentaActualizar = cuentaService.getCuentaById(cuenta.getId());
                cuentaActualizar.setSaldo(cuentaActualizar.getSaldo() + montoIngreso - montoGasto);
                cuentaService.updateCuenta(cuentaActualizar);

                // Marcar la transacción como procesada
                transaccion.setProcesado(1);
            }
        }

        // Guardar los cambios en la base de datos
        guardarTransaccionesEnArchivo();
        System.out.println("Transacciones procesadas exitosamente.");
    }
}