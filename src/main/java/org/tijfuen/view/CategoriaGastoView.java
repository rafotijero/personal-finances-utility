package org.tijfuen.view;

import org.tijfuen.controller.CategoriaGastoController;
import org.tijfuen.model.CategoriaGasto;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class CategoriaGastoView {

    private final CategoriaGastoController controller;

    public CategoriaGastoView() {
        this.controller = new CategoriaGastoController();
    }
    public CategoriaGastoView(CategoriaGastoController controller) {
        this.controller = controller;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Añadir categoría de gasto");
            System.out.println("2. Listar categorías de gasto");
            System.out.println("3. Modificar categoría de gasto");
            System.out.println("4. Eliminar categoría de gasto");
            System.out.println("5. Mostrar categoría de gasto por ID");
            System.out.println("0. Volver al menú principal");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (opcion) {
                    case 1:
                        agregarCategoriaGasto(scanner);
                        break;
                    case 2:
                        listarCategoriasGasto();
                        break;
                    case 3:
                        modificarCategoriaGasto(scanner);
                        break;
                    case 4:
                        eliminarCategoriaGasto(scanner);
                        break;
                    case 5:
                        mostrarCategoriaGastoPorId(scanner);
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

    private void agregarCategoriaGasto(Scanner scanner) {
        System.out.println("Ingrese el nombre de la categoría de gasto:");
        String nombre = scanner.nextLine();
        CategoriaGasto categoria = new CategoriaGasto(0, nombre, 0); // ID se asigna automáticamente, 0: activo
        System.out.println(controller.addCategoriaGasto(categoria));
    }

    private void listarCategoriasGasto() {
        Map<Integer, CategoriaGasto> categoriasGasto = controller.getAllCategoriasGasto();
        if (categoriasGasto.isEmpty()) {
            System.out.println("No hay categorías de gasto para mostrar.");
        } else {
            System.out.println("Categorías de gasto:");
            for (CategoriaGasto categoriaGasto : categoriasGasto.values()) {
                System.out.println(categoriaGasto);
            }
        }
    }

    private void modificarCategoriaGasto(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la categoría de gasto a modificar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            CategoriaGasto categoria = controller.getCategoriaGastoById(id);
            if (categoria == null) {
                System.out.println("Categoría de gasto no encontrada.");
                return;
            }

            System.out.println("Ingrese el nuevo nombre de la categoría de gasto:");
            String nuevoNombre = scanner.nextLine();
            categoria.setNombre(nuevoNombre);
            controller.updateCategoriaGasto(categoria);
            System.out.println("Categoría de gasto actualizada.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void eliminarCategoriaGasto(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la categoría de gasto a eliminar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            controller.deleteCategoriaGasto(id);
            System.out.println("Categoría de gasto eliminada.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void mostrarCategoriaGastoPorId(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la categoría de gasto a mostrar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            CategoriaGasto categoria = controller.getCategoriaGastoById(id);

            if (categoria != null) {
                System.out.println("Categoría de gasto encontrada:");
                System.out.println(categoria);
            } else {
                System.out.println("Categoría de gasto no encontrada.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

}
