package org.tijfuen.view;

import org.tijfuen.controller.MonedaController;
import org.tijfuen.model.Moneda;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class MonedaView {

    private final MonedaController controller;

    public MonedaView() {
        this.controller = new MonedaController();
    }
    public MonedaView(MonedaController controller) {
        this.controller = controller;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Agregar moneda");
            System.out.println("2. Listar monedas");
            System.out.println("3. Modificar moneda");
            System.out.println("4. Eliminar moneda");
            System.out.println("5. Mostrar moneda por ID");
            System.out.println("0. Salir");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (opcion) {
                    case 1:
                        agregarMoneda(scanner);
                        break;
                    case 2:
                        listarMonedas();
                        break;
                    case 3:
                        modificarMoneda(scanner);
                        break;
                    case 4:
                        eliminarMoneda(scanner);
                        break;
                    case 5:
                        mostrarMonedaPorId(scanner);
                        break;
                    case 0:
                        return;  // Salir del menú
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();  // Limpiar el buffer
            }
        }
    }

    // Método para añadir moneda
    private void agregarMoneda(Scanner scanner) {
        try {
            System.out.println("Ingrese el nombre de la moneda:");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese el símbolo de la moneda:");
            String simbolo = scanner.nextLine();
            System.out.println("¿Es moneda local? (true/false):");
            boolean esMonedaLocal = scanner.nextBoolean();
            scanner.nextLine();  // Consume newline

            // Verificar si ya existe una moneda local en el sistema
            if (esMonedaLocal && controller.existeMonedaLocal()) {
                System.out.println("Ya existe una moneda local en el sistema.");
                return;
            }

            double tipoCambio = esMonedaLocal ? 1.0 : 0.0;  // Valor predeterminado para moneda local
            if (!esMonedaLocal) {
                System.out.println("Ingrese el tipo de cambio:");
                tipoCambio = scanner.nextDouble();
                scanner.nextLine();  // Consume newline
            }

            Moneda moneda = new Moneda(0, nombre, simbolo, esMonedaLocal, tipoCambio, 0); // ID se asigna automáticamente, 0: activo
            System.out.println(controller.addMoneda(moneda));
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese datos correctos.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    // Método para listar monedas
    private void listarMonedas() {
        Map<Integer, Moneda> monedas = controller.getAllMonedas();
        if (monedas.isEmpty()) {
            System.out.println("No hay monedas para mostrar.");
        } else {
            // Imprimir cabecera de la tabla
            System.out.printf("%-5s %-20s %-10s %-15s %-10s%n", "ID", "Nombre", "Símbolo", "Es local", "Tipo cambio");
            System.out.println("===========================================================================");

            // Imprimir cada moneda
            for (Moneda moneda : monedas.values()) {
                System.out.printf("%-5d %-20s %-10s %-15s %-10.2f%n",
                        moneda.getId(),
                        moneda.getNombre(),
                        moneda.getSimbolo(),
                        moneda.isEsMonedaLocal(),
                        moneda.getTipoCambio());
            }
        }
    }

    // Método para modificar moneda
    private void modificarMoneda(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la moneda a modificar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            Moneda moneda = controller.getMonedaById(id);
            if (moneda == null || moneda.getEliminado() == 1) {
                System.out.println("Moneda no encontrada o está eliminada.");
                return;
            }

            System.out.println("Ingrese el nuevo nombre de la moneda:");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese el nuevo símbolo de la moneda:");
            String simbolo = scanner.nextLine();
            System.out.println("¿Es moneda local? (true/false):");
            boolean esMonedaLocal = scanner.nextBoolean();
            scanner.nextLine();  // Consume newline

            // Verificar si ya existe una moneda local en el sistema
            if (esMonedaLocal && !moneda.isEsMonedaLocal() && controller.existeMonedaLocalExcepto(moneda.getId())) {
                System.out.println("Ya existe una moneda local en el sistema.");
                return;
            }

            double tipoCambio = esMonedaLocal ? 1.0 : 0.0;  // Valor predeterminado para moneda local
            if (!esMonedaLocal) {
                System.out.println("Ingrese el nuevo tipo de cambio:");
                tipoCambio = scanner.nextDouble();
                scanner.nextLine();  // Consume newline
            }

            moneda.setNombre(nombre);
            moneda.setSimbolo(simbolo);
            moneda.setEsMonedaLocal(esMonedaLocal);
            moneda.setTipoCambio(tipoCambio);

            controller.updateMoneda(moneda);
            System.out.println("Moneda actualizada.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese datos correctos.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    // Método para eliminar moneda
    private void eliminarMoneda(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la moneda a eliminar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            controller.deleteMoneda(id);
            System.out.println("Moneda eliminada.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    // Método para mostrar moneda por ID
    private void mostrarMonedaPorId(Scanner scanner) {
        try {
            System.out.println("Ingrese el ID de la moneda a mostrar:");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            Moneda moneda = controller.getMonedaById(id);

            if (moneda != null) {
                System.out.println("ID: " + moneda.getId() + ", Nombre: " + moneda.getNombre() +
                        ", Símbolo: " + moneda.getSimbolo() + ", Es local: " + moneda.isEsMonedaLocal() +
                        ", Tipo cambio: " + moneda.getTipoCambio());
            } else {
                System.out.println("Moneda no encontrada.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }
}

