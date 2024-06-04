package org.tijfuen.view;

import org.tijfuen.controller.CategoriaIngresoController;
import org.tijfuen.controller.IngresoController;
import org.tijfuen.controller.MonedaController;
import org.tijfuen.model.CategoriaIngreso;
import org.tijfuen.model.Ingreso;
import org.tijfuen.model.Moneda;
import org.tijfuen.util.DateUtil;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class IngresoView {

    private final IngresoController ingresoController;
    private final CategoriaIngresoController categoriaIngresoController;
    private final MonedaController monedaController;

    public IngresoView() {
        this.ingresoController = new IngresoController();
        this.categoriaIngresoController = new CategoriaIngresoController();
        this.monedaController = new MonedaController();
    }

    public IngresoView(IngresoController ingresoController, CategoriaIngresoController categoriaIngresoController, MonedaController monedaController) {
        this.ingresoController = ingresoController;
        this.categoriaIngresoController = categoriaIngresoController;
        this.monedaController = monedaController;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Añadir ingreso");
            System.out.println("2. Listar ingresos");
            System.out.println("3. Modificar ingreso");
            System.out.println("4. Eliminar ingreso");
            System.out.println("5. Mostrar ingreso por ID");
            System.out.println("0. Volver al menú principal");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (opcion) {
                    case 1:
                        agregarIngreso(scanner);
                        break;
                    case 2:
                        listarIngresos();
                        break;
                    case 3:
                        modificarIngreso(scanner);
                        break;
                    case 4:
                        eliminarIngreso(scanner);
                        break;
                    case 5:
                        mostrarIngresoPorId(scanner);
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

    // Método para agregar ingreso
    private void agregarIngreso(Scanner scanner) {
        try {
            System.out.println("Ingrese el nombre del ingreso:");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese el ID de la categoría de ingreso:");
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

            CategoriaIngreso categoriaIngreso = categoriaIngresoController.getCategoriaIngresoById(categoriaId);
            if (categoriaIngreso == null || categoriaIngreso.getEliminado() == 1) {
                System.out.println("Categoría de ingreso no encontrada o está eliminada.");
                return;
            }

            Moneda moneda = monedaController.getMonedaById(monedaId);
            if (moneda == null || moneda.getEliminado() == 1) {
                System.out.println("Moneda no encontrada o está eliminada.");
                return;
            }

            Ingreso ingreso = new Ingreso(0, nombre, categoriaIngreso, moneda, monto, DateUtil.parseDate(fechaStr), nota, 0);
            System.out.println(ingresoController.addIngreso(ingreso));
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese datos correctos.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    // Método para listar ingresos
    private void listarIngresos() {
        Map<Integer, Ingreso> ingresos = ingresoController.getAllIngresos();
        if (ingresos.isEmpty()) {
            System.out.println("No hay ingresos para mostrar.");
        } else {
            // Imprimir cabecera de la tabla
            System.out.printf("%-5s %-20s %-15s %-15s %-10s %-10s %-20s %-10s%n",
                    "ID", "Nombre", "Categoría", "Moneda", "Monto", "Fecha", "Nota", "Estado");
            System.out.println("=================================================================================================");

            // Imprimir cada ingreso
            for (Ingreso ingreso : ingresos.values()) {
                if (ingreso.getEliminado() == 0) {
                    System.out.printf("%-5d %-20s %-15s %-15s %-10.2f %-10s %-20s %-10s%n",
                            ingreso.getId(),
                            ingreso.getNombre(),
                            ingreso.getCategoria().getNombre(),
                            ingreso.getMoneda().getNombre(),
                            ingreso.getMonto(),
                            ingreso.getFecha(),
                            ingreso.getNota(),
                            ingreso.getEliminado() == 0 ? "activo" : "eliminado");
                }
            }
        }
    }

    // Método para modificar ingreso
    private void modificarIngreso(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID del ingreso a modificar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            Ingreso ingreso = ingresoController.getIngresoById(id);
            if (ingreso == null || ingreso.getEliminado() == 1) {
                System.out.println("Ingreso no encontrado o está eliminado.");
                return;
            }

            System.out.println("Ingrese el nuevo nombre del ingreso:");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese el nuevo ID de la categoría de ingreso:");
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

            CategoriaIngreso categoriaIngreso = categoriaIngresoController.getCategoriaIngresoById(categoriaId);
            if (categoriaIngreso == null || categoriaIngreso.getEliminado() == 1) {
                System.out.println("Categoría de ingreso no encontrada o está eliminada.");
                return;
            }

            Moneda moneda = monedaController.getMonedaById(monedaId);
            if (moneda == null) {
                System.out.println("Moneda no encontrada.");
                return;
            }

            // Crear un nuevo objeto Ingreso con los datos actualizados
            Ingreso nuevoIngreso = new Ingreso(ingreso.getId(), nombre, categoriaIngreso, moneda, monto, DateUtil.parseDate(fechaStr), nota, ingreso.getEliminado());
            ingresoController.updateIngreso(nuevoIngreso);
            System.out.println("Ingreso actualizado.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese datos correctos.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    // Método para eliminar ingreso
    private void eliminarIngreso(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID del ingreso a eliminar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            ingresoController.deleteIngreso(id);
            System.out.println("Ingreso eliminado.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    // Método para mostrar ingreso por ID
    private void mostrarIngresoPorId(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID del ingreso a mostrar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            Ingreso ingreso = ingresoController.getIngresoById(id);

            if (ingreso != null) {
                System.out.println(ingreso);
            } else {
                System.out.println("Ingreso no encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }
}
