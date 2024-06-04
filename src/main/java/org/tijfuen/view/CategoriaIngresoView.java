package org.tijfuen.view;

import org.tijfuen.controller.CategoriaIngresoController;
import org.tijfuen.model.CategoriaIngreso;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class CategoriaIngresoView {

    private final CategoriaIngresoController controller;

    public CategoriaIngresoView() {
        this.controller = new CategoriaIngresoController();
    }
    public CategoriaIngresoView(CategoriaIngresoController controller) {
        this.controller = controller;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Añadir categoría de ingreso");
            System.out.println("2. Listar categorías de ingreso");
            System.out.println("3. Modificar categoría de ingreso");
            System.out.println("4. Eliminar categoría de ingreso");
            System.out.println("5. Mostrar categoría de ingreso por ID");
            System.out.println("0. Volver al menú principal");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (opcion) {
                    case 1:
                        agregarCategoriaIngreso(scanner);
                        break;
                    case 2:
                        listarCategoriasIngreso();
                        break;
                    case 3:
                        modificarCategoriaIngreso(scanner);
                        break;
                    case 4:
                        eliminarCategoriaIngreso(scanner);
                        break;
                    case 5:
                        mostrarCategoriaIngresoPorId(scanner);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();  // Limpiar el buffer
            }
        }
    }

    private void agregarCategoriaIngreso(Scanner scanner) {
        System.out.println("Ingrese el nombre de la categoría de ingreso:");
        String nombre = scanner.nextLine();
        CategoriaIngreso categoria = new CategoriaIngreso(0, nombre, 0); // ID se asigna automáticamente, 0: activo
        System.out.println(controller.addCategoriaIngreso(categoria));
    }

    private void listarCategoriasIngreso() {
        Map<Integer, CategoriaIngreso> categoriasIngreso = controller.getAllCategoriasIngreso();
        if (categoriasIngreso.isEmpty()) {
            System.out.println("No hay categorías de ingreso para mostrar.");
        } else {
            System.out.println("Categorías de ingreso:");
            for (CategoriaIngreso CategoriaIngreso : categoriasIngreso.values()) {
                System.out.println(CategoriaIngreso);
            }
        }
    }

    private void modificarCategoriaIngreso(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la categoría de ingreso a modificar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            CategoriaIngreso categoria = controller.getCategoriaIngresoById(id);
            if (categoria == null) {
                System.out.println("Categoría de ingreso no encontrada.");
                return;
            }

            System.out.println("Ingrese el nuevo nombre de la categoría de ingreso:");
            String nuevoNombre = scanner.nextLine();
            categoria.setNombre(nuevoNombre);
            controller.updateCategoriaIngreso(categoria);
            System.out.println("Categoría de ingreso actualizada.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void eliminarCategoriaIngreso(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la categoría de ingreso a eliminar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            controller.deleteCategoriaIngreso(id);
            System.out.println("Categoría de ingreso eliminada.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void mostrarCategoriaIngresoPorId(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la categoría de ingreso a mostrar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            CategoriaIngreso categoria = controller.getCategoriaIngresoById(id);

            if (categoria != null) {
                System.out.println("Categoría de ingreso encontrada:");
                System.out.println(categoria);
            } else {
                System.out.println("Categoría de ingreso no encontrada.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

}
