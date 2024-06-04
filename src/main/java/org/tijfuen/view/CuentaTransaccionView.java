package org.tijfuen.view;

import org.tijfuen.controller.CuentaController;
import org.tijfuen.controller.CuentaTransaccionController;
import org.tijfuen.controller.GastoController;
import org.tijfuen.controller.IngresoController;
import org.tijfuen.model.Cuenta;
import org.tijfuen.model.CuentaTransaccion;
import org.tijfuen.model.Gasto;
import org.tijfuen.model.Ingreso;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
public class CuentaTransaccionView {

    private final CuentaTransaccionController transaccionController;
    private final CuentaController cuentaController;
    private final IngresoController ingresoController;
    private final GastoController gastoController;

    public CuentaTransaccionView() {
        this.transaccionController = new CuentaTransaccionController();
        this.cuentaController = new CuentaController();
        this.ingresoController = new IngresoController();
        this.gastoController = new GastoController();
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Añadir transacción");
            System.out.println("2. Listar transacciones");
            System.out.println("3. Modificar transacción");
            System.out.println("4. Eliminar transacción");
            System.out.println("5. Mostrar transacción por ID");
            System.out.println("6. Procesar transacciones pendientes");
            System.out.println("0. Volver al menú principal");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (opcion) {
                    case 1:
                        agregarTransaccion(scanner);
                        break;
                    case 2:
                        listarTransacciones();
                        break;
                    case 3:
                        modificarTransaccion(scanner);
                        break;
                    case 4:
                        eliminarTransaccion(scanner);
                        break;
                    case 5:
                        mostrarTransaccionPorId(scanner);
                        break;
                    case 6:
                        procesarTransacciones();
                        break;
                    case 0:
                        return;  // Volver al menú principal
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();  // Limpiar el buffer
            }
        }
    }

    private void agregarTransaccion(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la cuenta:");
            int cuentaId = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            Cuenta cuenta = cuentaController.getCuentaById(cuentaId);
            if (cuenta == null) {
                System.out.println("La cuenta especificada no existe.");
                return;
            }

            System.out.println("Ingrese el ID del ingreso (opcional, presione Enter si no aplica):");
            String ingresoIdStr = scanner.nextLine();
            Ingreso ingreso = null;
            if (!ingresoIdStr.isEmpty()) {
                int ingresoId = Integer.parseInt(ingresoIdStr);
                ingreso = ingresoController.getIngresoById(ingresoId);
            }

            System.out.println("Ingrese el ID del gasto (opcional, presione Enter si no aplica):");
            String gastoIdStr = scanner.nextLine();
            Gasto gasto = null;
            if (!gastoIdStr.isEmpty()) {
                int gastoId = Integer.parseInt(gastoIdStr);
                gasto = gastoController.getGastoById(gastoId);
            }

            CuentaTransaccion transaccion = new CuentaTransaccion(0, cuenta, ingreso, gasto, 0);
            System.out.println(transaccionController.addTransaccion(transaccion));
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese datos correctos.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void modificarTransaccion(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la transacción a modificar:");
            int transaccionId = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            CuentaTransaccion transaccion = transaccionController.getTransaccionById(transaccionId);
            if (transaccion == null) {
                System.out.println("La transacción especificada no existe.");
                return;
            }

            System.out.println("Ingrese el nuevo ID de la cuenta:");
            int cuentaId = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            Cuenta cuenta = cuentaController.getCuentaById(cuentaId);
            if (cuenta == null) {
                System.out.println("La cuenta especificada no existe.");
                return;
            }

            System.out.println("Ingrese el nuevo ID del ingreso (opcional, presione Enter si no aplica):");
            String ingresoIdStr = scanner.nextLine();
            Ingreso ingreso = null;
            if (!ingresoIdStr.isEmpty()) {
                int ingresoId = Integer.parseInt(ingresoIdStr);
                ingreso = ingresoController.getIngresoById(ingresoId);
            }

            System.out.println("Ingrese el nuevo ID del gasto (opcional, presione Enter si no aplica):");
            String gastoIdStr = scanner.nextLine();
            Gasto gasto = null;
            if (!gastoIdStr.isEmpty()) {
                int gastoId = Integer.parseInt(gastoIdStr);
                gasto = gastoController.getGastoById(gastoId);
            }

            CuentaTransaccion nuevaTransaccion = new CuentaTransaccion(transaccionId, cuenta, ingreso, gasto, transaccion.getProcesado());
            transaccionController.updateTransaccion(nuevaTransaccion);
            System.out.println("Transacción actualizada.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese datos correctos.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void listarTransacciones() {
        Map<Integer, CuentaTransaccion> transacciones = transaccionController.getAllTransacciones();
        if (transacciones.isEmpty()) {
            System.out.println("No hay transacciones para mostrar.");
        } else {
            // Imprimir cabecera de la tabla
            System.out.printf("%-5s %-10s %-10s %-10s %-10s %-10s%n",
                    "ID", "Cuenta ID", "Ingreso ID", "Gasto ID", "Monto", "Procesado");
            System.out.println("=======================================================");

            // Imprimir cada transacción
            for (CuentaTransaccion transaccion : transacciones.values()) {
                String ingresoId = (transaccion.getIngreso() != null) ? String.valueOf(transaccion.getIngreso().getId()) : "N/A";
                String gastoId = (transaccion.getGasto() != null) ? String.valueOf(transaccion.getGasto().getId()) : "N/A";
                double monto = (transaccion.getIngreso() != null) ? transaccion.getIngreso().getMonto() : 0;
                monto -= (transaccion.getGasto() != null) ? transaccion.getGasto().getMonto() : 0;

                System.out.printf("%-5d %-10d %-10s %-10s %-10.2f %-10s%n",
                        transaccion.getId(),
                        transaccion.getCuenta().getId(),
                        ingresoId,
                        gastoId,
                        monto,
                        transaccion.getProcesado() == 1 ? "Sí" : "No");
            }
        }
    }

    private void eliminarTransaccion(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la transacción a eliminar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            CuentaTransaccion transaccion = transaccionController.getTransaccionById(id);
            if (transaccion == null) {
                System.out.println("La transacción que intenta eliminar no existe.");
                return;
            }

            transaccionController.deleteTransaccion(id);
            System.out.println("Transacción eliminada.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void mostrarTransaccionPorId(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la transacción a mostrar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            CuentaTransaccion transaccion = transaccionController.getTransaccionById(id);

            if (transaccion != null) {
                String ingresoId = (transaccion.getIngreso() != null) ? String.valueOf(transaccion.getIngreso().getId()) : "N/A";
                String gastoId = (transaccion.getGasto() != null) ? String.valueOf(transaccion.getGasto().getId()) : "N/A";
                double monto = (transaccion.getIngreso() != null) ? transaccion.getIngreso().getMonto() : 0;
                monto -= (transaccion.getGasto() != null) ? transaccion.getGasto().getMonto() : 0;

                System.out.printf("ID: %d%n", transaccion.getId());
                System.out.printf("Cuenta ID: %d%n", transaccion.getCuenta().getId());
                System.out.printf("Ingreso ID: %s%n", ingresoId);
                System.out.printf("Gasto ID: %s%n", gastoId);
                System.out.printf("Monto: %.2f%n", monto);
                System.out.printf("Procesado: %s%n", transaccion.getProcesado() == 1 ? "Sí" : "No");
            } else {
                System.out.println("Transacción no encontrada.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void procesarTransacciones() {
        System.out.println("Procesando todas las transacciones pendientes...");
        transaccionController.procesarTransacciones();
        System.out.println("Transacciones procesadas exitosamente.");
    }
}
