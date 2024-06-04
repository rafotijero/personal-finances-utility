package org.tijfuen.view;

import org.tijfuen.controller.CategoriaCuentaController;
import org.tijfuen.model.CategoriaCuenta;
import org.tijfuen.service.CategoriaCuentaService;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class CategoriaCuentaView {

    private final CategoriaCuentaController controller;

    public CategoriaCuentaView() {
        this.controller = new CategoriaCuentaController();
    }

    public CategoriaCuentaView(CategoriaCuentaController controller) {
        this.controller = controller;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Añadir categoría de cuenta");
            System.out.println("2. Listar categorías de cuenta");
            System.out.println("3. Modificar categoría de cuenta");
            System.out.println("4. Eliminar categoría de cuenta");
            System.out.println("5. Mostrar categoría por ID");
            System.out.println("0. Salir");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (opcion) {
                    case 1:
                        agregarCategoria(scanner);
                        break;
                    case 2:
                        listarCategorias();
                        break;
                    case 3:
                        modificarCategoria(scanner);
                        break;
                    case 4:
                        eliminarCategoria(scanner);
                        break;
                    case 5:
                        mostrarCategoriaPorId(scanner);
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

    private void agregarCategoria(Scanner scanner) {
        System.out.println("Ingrese el nombre de la categoría:");
        String nombre = scanner.nextLine();
        CategoriaCuenta categoria = new CategoriaCuenta(0, nombre, 0); // ID se asigna automáticamente, 0: activo
        System.out.println(controller.addCategoria(categoria));
    }

    private void listarCategorias() {
        Map<Integer, CategoriaCuenta> categorias = controller.getAllCategorias();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías para mostrar.");
        } else {
            // Imprimir cabecera de la tabla
            System.out.printf("%-5s %-20s %-10s%n", "ID", "Nombre", "Estado");
            System.out.println("========================================");

            // Imprimir cada categoría
            for (CategoriaCuenta categoria : categorias.values()) {
                if (categoria.getEliminado() == 0) {
                    System.out.printf("%-5d %-20s %-10s%n",
                            categoria.getId(),
                            categoria.getNombre(),
                            categoria.getEliminado() == 0 ? "activo" : "eliminado");
                }
            }
        }
    }

    private void mostrarCategoriaPorId(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la categoría a mostrar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            CategoriaCuenta categoria = controller.getCategoriaById(id);

            if (categoria != null) {
                System.out.println("ID: " + categoria.getId() + ", Nombre: " + categoria.getNombre() +
                        ", Estado: " + (categoria.getEliminado() == 0 ? "activo" : "eliminado"));
            } else {
                System.out.println("Categoría no encontrada.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void modificarCategoria(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la categoría a modificar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            CategoriaCuenta categoria = controller.getCategoriaById(id);
            if (categoria == null || categoria.getEliminado() == 1) {
                System.out.println("Categoría no encontrada o está eliminada.");
                return;
            }

            System.out.println("Ingrese el nuevo nombre de la categoría:");
            String nombre = scanner.nextLine();
            categoria.setNombre(nombre);
            controller.updateCategoria(categoria);
            System.out.println("Categoría actualizada.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void eliminarCategoria(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la categoría a eliminar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            controller.deleteCategoria(id);
            System.out.println("Categoría eliminada.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

}
