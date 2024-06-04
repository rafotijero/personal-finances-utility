package org.tijfuen.view;

import org.tijfuen.controller.CategoriaGastoController;
import org.tijfuen.controller.GastoController;
import org.tijfuen.controller.MonedaController;
import org.tijfuen.model.CategoriaGasto;
import org.tijfuen.model.Gasto;
import org.tijfuen.model.Moneda;
import org.tijfuen.util.DateUtil;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class GastoView {

    private final GastoController gastoController;
    private final CategoriaGastoController categoriaGastoController;
    private final MonedaController monedaController;

    public GastoView() {
        this.gastoController = new GastoController();
        this.categoriaGastoController = new CategoriaGastoController();
        this.monedaController = new MonedaController();
    }

    public GastoView(GastoController gastoController, CategoriaGastoController categoriaGastoController, MonedaController monedaController) {
        this.gastoController = gastoController;
        this.categoriaGastoController = categoriaGastoController;
        this.monedaController = monedaController;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Añadir gasto");
            System.out.println("2. Listar gastos");
            System.out.println("3. Modificar gasto");
            System.out.println("4. Eliminar gasto");
            System.out.println("5. Mostrar gasto por ID");
            System.out.println("0. Volver al menú principal");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (opcion) {
                    case 1:
                        agregarGasto(scanner);
                        break;
                    case 2:
                        listarGastos();
                        break;
                    case 3:
                        modificarGasto(scanner);
                        break;
                    case 4:
                        eliminarGasto(scanner);
                        break;
                    case 5:
                        mostrarGastoPorId(scanner);
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

    private void agregarGasto(Scanner scanner) {
        try {
            System.out.println("Ingrese el nombre del gasto:");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese el ID de la categoría de gasto:");
            int categoriaId = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.println("Ingrese el ID de la moneda:");
            int monedaId = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.println("Ingrese el monto:");
            double monto = scanner.nextDouble();
            scanner.nextLine();  // Consume newline
            System.out.println("Ingrese la fecha (YYYY-MM-DD):");
            String fechaStr = scanner.nextLine();
            System.out.println("Ingrese una nota (opcional):");
            String nota = scanner.nextLine();

            CategoriaGasto categoriaGasto = categoriaGastoController.getCategoriaGastoById(categoriaId);
            if (categoriaGasto == null || categoriaGasto.getEliminado() == 1) {
                System.out.println("Categoría de gasto no encontrada o está eliminada.");
                return;
            }

            Moneda moneda = monedaController.getMonedaById(monedaId);
            if (moneda == null || moneda.getEliminado() == 1) {
                System.out.println("Moneda no encontrada o está eliminada.");
                return;
            }

            Gasto gasto = new Gasto(0, nombre, categoriaGasto, moneda, monto, DateUtil.parseDate(fechaStr), nota, 0);
            System.out.println(gastoController.addGasto(gasto));
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese datos correctos.");
            scanner.nextLine();  // Limpiar el buffer
        }

    }

    private void listarGastos() {
        Map<Integer, Gasto> gastos = gastoController.getAllGastos();
        if (gastos.isEmpty()) {
            System.out.println("No hay gastos para mostrar.");
        } else {
            // Imprimir cabecera de la tabla
            System.out.printf("%-5s %-20s %-15s %-15s %-10s %-10s %-20s %-10s%n",
                    "ID", "Nombre", "Categoría", "Moneda", "Monto", "Fecha", "Nota", "Estado");
            System.out.println("=================================================================================================");

            // Imprimir cada gasto
            for (Gasto gasto : gastos.values()) {
                if (gasto.getEliminado() == 0) {
                    System.out.printf("%-5d %-20s %-15s %-15s %-10.2f %-10s %-20s %-10s%n",
                            gasto.getId(),
                            gasto.getNombre(),
                            gasto.getCategoria().getNombre(),
                            gasto.getMoneda().getNombre(),
                            gasto.getMonto(),
                            gasto.getFecha(),
                            gasto.getNota(),
                            gasto.getEliminado() == 0 ? "activo" : "eliminado");
                }
            }
        }
    }

    private void modificarGasto(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID del gasto a modificar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            Gasto gasto = gastoController.getGastoById(id);
            if (gasto == null || gasto.getEliminado() == 1) {
                System.out.println("Gasto no encontrado o está eliminado.");
                return;
            }

            System.out.println("Ingrese el nuevo nombre del gasto:");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese el nuevo ID de la categoría de gasto:");
            int categoriaId = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.println("Ingrese el nuevo ID de la moneda:");
            int monedaId = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.println("Ingrese el nuevo monto:");
            double monto = scanner.nextDouble();
            scanner.nextLine();  // Consume newline
            System.out.println("Ingrese la nueva fecha (Formato: yyyy-MM-dd):");
            String fechaStr = scanner.nextLine();
            System.out.println("Ingrese la nueva nota:");
            String nota = scanner.nextLine();

            CategoriaGasto categoriaGasto = categoriaGastoController.getCategoriaGastoById(categoriaId);
            if (categoriaGasto == null || categoriaGasto.getEliminado() == 1) {
                System.out.println("Categoría de gasto no encontrada o está eliminada.");
                return;
            }

            Moneda moneda = monedaController.getMonedaById(monedaId);
            if (moneda == null) {
                System.out.println("Moneda no encontrada.");
                return;
            }

            // Crear un nuevo objeto Gasto con los datos actualizados
            Gasto nuevoGasto = new Gasto(gasto.getId(), nombre, categoriaGasto, moneda, monto, DateUtil.parseDate(fechaStr), nota, gasto.getEliminado());
            gastoController.updateGasto(nuevoGasto);
            System.out.println("Gasto actualizado.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese datos correctos.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void eliminarGasto(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID del gasto a eliminar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            gastoController.deleteGasto(id);
            System.out.println("Gasto eliminado.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void mostrarGastoPorId(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID del gasto a mostrar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            Gasto gasto = gastoController.getGastoById(id);

            if (gasto != null) {
                System.out.println(gasto);
            } else {
                System.out.println("Gasto no encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }
}
