package org.tijfuen.view;

import org.tijfuen.controller.CategoriaCuentaController;
import org.tijfuen.controller.CuentaController;
import org.tijfuen.controller.MonedaController;
import org.tijfuen.model.CategoriaCuenta;
import org.tijfuen.model.Cuenta;
import org.tijfuen.model.Moneda;
import org.tijfuen.service.CuentaService;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class CuentaView {

    private final CuentaController controller;
    private final CategoriaCuentaController categoriaCuentaController;
    private final MonedaController monedaController;

    public CuentaView() {
        this.controller = new CuentaController();
        this.categoriaCuentaController = new CategoriaCuentaController();
        this.monedaController = new MonedaController();
    }

    public CuentaView(CuentaController controller, CategoriaCuentaController categoriaCuentaController, MonedaController monedaController) {
        this.controller = controller;
        this.categoriaCuentaController = categoriaCuentaController;
        this.monedaController = monedaController;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Añadir cuenta");
            System.out.println("2. Listar cuentas");
            System.out.println("3. Modificar cuenta");
            System.out.println("4. Eliminar cuenta");
            System.out.println("5. Mostrar cuenta por ID");
            System.out.println("0. Salir");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (opcion) {
                    case 1:
                        agregarCuenta(scanner);
                        break;
                    case 2:
                        listarCuentas();
                        break;
                    case 3:
                        modificarCuenta(scanner);
                        break;
                    case 4:
                        eliminarCuenta(scanner);
                        break;
                    case 5:
                        mostrarCuentaPorId(scanner);
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

    private void agregarCuenta(Scanner scanner) {
        try {
            System.out.println("Ingrese el nombre de la cuenta:");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese el nombre del banco:");
            String banco = scanner.nextLine();
            System.out.println("Ingrese el ID de la categoría de la cuenta:");
            int categoriaId = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.println("Ingrese el saldo de la cuenta:");
            double saldo = scanner.nextDouble();
            scanner.nextLine();  // Consume newline
            System.out.println("Ingrese el ID de la moneda:");
            int monedaId = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            CategoriaCuenta categoria = categoriaCuentaController.getCategoriaById(categoriaId);
            Moneda moneda = monedaController.getMonedaById(monedaId);

            if (categoria == null || categoria.getEliminado() == 1) {
                System.out.println("Categoría no encontrada o está eliminada.");
                return;
            }

            if (moneda == null) {
                System.out.println("Moneda no encontrada.");
                return;
            }

            Cuenta cuenta = new Cuenta(0, nombre, banco, categoria, moneda, saldo, 0); // ID se asigna automáticamente, 0: activo
            System.out.println(controller.addCuenta(cuenta));
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese datos correctos.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void listarCuentas() {
        Map<Integer, Cuenta> cuentas = controller.getAllCuentas();
        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas para mostrar.");
        } else {
            // Imprimir cabecera de la tabla
            System.out.printf("%-5s %-20s %-15s %-20s %-10s %-10s %-10s%n",
                    "ID", "Nombre", "Banco", "Categoría", "Saldo", "Moneda", "Estado");
            System.out.println("=============================================================================================");

            // Imprimir cada cuenta
            for (Cuenta cuenta : cuentas.values()) {
                if (cuenta.getEliminado() == 0) {
                    System.out.printf("%-5d %-20s %-15s %-20s %-10.2f %-10s %-10s%n",
                            cuenta.getId(),
                            cuenta.getNombre(),
                            cuenta.getBanco(),
                            cuenta.getCategoria().getNombre(),
                            cuenta.getSaldo(),
                            cuenta.getMoneda().getNombre(), // Agregar la moneda
                            cuenta.getEliminado() == 0 ? "activo" : "eliminado");
                }
            }
        }
    }

    private void mostrarCuentaPorId(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la cuenta a mostrar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            Cuenta cuenta = controller.getCuentaById(id);

            if (cuenta != null) {
                System.out.println("ID: " + cuenta.getId() + ", Nombre: " + cuenta.getNombre() +
                        ", Banco: " + cuenta.getBanco() + ", Categoría: " + cuenta.getCategoria().getNombre() +
                        ", Saldo: " + cuenta.getSaldo() + ", Estado: " + (cuenta.getEliminado() == 0 ? "activo" : "eliminado"));
            } else {
                System.out.println("Cuenta no encontrada.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void modificarCuenta(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la cuenta a modificar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            Cuenta cuenta = controller.getCuentaById(id);
            if (cuenta == null || cuenta.getEliminado() == 1) {
                System.out.println("Cuenta no encontrada o está eliminada.");
                return;
            }

            System.out.println("Ingrese el nuevo nombre de la cuenta:");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese el nuevo banco de la cuenta:");
            String banco = scanner.nextLine();
            System.out.println("Ingrese el ID de la nueva categoría de la cuenta:");
            int categoriaId = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.println("Ingrese el nuevo saldo de la cuenta:");
            double saldo = scanner.nextDouble();
            scanner.nextLine();  // Consume newline

            CategoriaCuenta categoria = categoriaCuentaController.getCategoriaById(categoriaId);
            if (categoria == null || categoria.getEliminado() == 1) {
                System.out.println("Categoría no encontrada o está eliminada.");
                return;
            }

            cuenta.setNombre(nombre);
            cuenta.setBanco(banco);
            cuenta.setCategoria(categoria);
            cuenta.setSaldo(saldo);
            controller.updateCuenta(cuenta);
            System.out.println("Cuenta actualizada.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese datos correctos.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void eliminarCuenta(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la cuenta a eliminar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            controller.deleteCuenta(id);
            System.out.println("Cuenta eliminada.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }
}

