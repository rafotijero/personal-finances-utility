package org.tijfuen;

import org.tijfuen.controller.*;
import org.tijfuen.service.*;
import org.tijfuen.util.FileUtil;
import org.tijfuen.view.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileUtil.crearDirectorioSiNoExiste();

        // Inicializar servicios
        /*
        CategoriaCuentaService categoriaCuentaService = new CategoriaCuentaService();
        CuentaService cuentaService = new CuentaService(categoriaCuentaService);
        CategoriaGastoService categoriaGastoService = new CategoriaGastoService();
        CategoriaIngresoService categoriaIngresoService = new CategoriaIngresoService();
        MonedaService monedaService = new MonedaService();
        IngresoService ingresoService = new IngresoService(categoriaIngresoService, monedaService); // Agregar servicio de ingreso
        GastoService gastoService = new GastoService(categoriaGastoService, monedaService); // Agregar servicio de gasto
         */

        // Inicializar controladores
        /*
        CategoriaCuentaController categoriaCuentaController = new CategoriaCuentaController(categoriaCuentaService);
        CuentaController cuentaController = new CuentaController(cuentaService, monedaService);
        CategoriaGastoController categoriaGastoController = new CategoriaGastoController(categoriaGastoService);
        CategoriaIngresoController categoriaIngresoController = new CategoriaIngresoController(categoriaIngresoService);
        MonedaController monedaController = new MonedaController(monedaService);
        IngresoController ingresoController = new IngresoController(ingresoService); // Agregar controlador de ingreso
        GastoController gastoController = new GastoController(gastoService); // Agregar controlador de gasto
         */

        // Inicializar vistas
        //CategoriaCuentaView categoriaCuentaView = new CategoriaCuentaView(categoriaCuentaController);
        CategoriaCuentaView categoriaCuentaView = new CategoriaCuentaView();
        //CuentaView cuentaView = new CuentaView(cuentaController, categoriaCuentaController, monedaController);
        CuentaView cuentaView = new CuentaView();
        //CategoriaGastoView categoriaGastoView = new CategoriaGastoView(categoriaGastoController);
        CategoriaGastoView categoriaGastoView = new CategoriaGastoView();
        //CategoriaIngresoView categoriaIngresoView = new CategoriaIngresoView(categoriaIngresoController);
        CategoriaIngresoView categoriaIngresoView = new CategoriaIngresoView();
        //MonedaView monedaView = new MonedaView(monedaController);
        MonedaView monedaView = new MonedaView();
        //IngresoView ingresoView = new IngresoView(ingresoController, categoriaIngresoController, monedaController); // Agregar vista de ingreso
        IngresoView ingresoView = new IngresoView();
        //GastoView gastoView = new GastoView(gastoController, categoriaGastoController, monedaController); // Agregar vista de gasto
        GastoView gastoView = new GastoView();

        CuentaTransaccionView cuentaTransaccionView = new CuentaTransaccionView();

        // Mostrar menú principal
        displayMainMenu(categoriaCuentaView, cuentaView, categoriaGastoView, categoriaIngresoView, monedaView, ingresoView, gastoView, cuentaTransaccionView);
    }

    private static void displayMainMenu(CategoriaCuentaView categoriaCuentaView, CuentaView cuentaView,
                                        CategoriaGastoView categoriaGastoView, CategoriaIngresoView categoriaIngresoView,
                                        MonedaView monedaView, IngresoView ingresoView, GastoView gastoView, CuentaTransaccionView cuentaTransaccionView) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Gestionar categorías de cuenta");
            System.out.println("2. Gestionar cuentas");
            System.out.println("3. Gestionar categorías de gasto");
            System.out.println("4. Gestionar categorías de ingreso");
            System.out.println("5. Gestionar monedas");
            System.out.println("6. Gestionar ingresos"); // Agregar opción para gestionar ingresos
            System.out.println("7. Gestionar gastos"); // Agregar opción para gestionar gastos
            System.out.println("8. Procesar Transacciones"); // Agregar opción para gestionar gastos
            System.out.println("0. Salir");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (opcion) {
                    case 1:
                        categoriaCuentaView.displayMenu();
                        break;
                    case 2:
                        cuentaView.displayMenu();
                        break;
                    case 3:
                        categoriaGastoView.displayMenu();
                        break;
                    case 4:
                        categoriaIngresoView.displayMenu();
                        break;
                    case 5:
                        monedaView.displayMenu();
                        break;
                    case 6:
                        ingresoView.displayMenu(); // Mostrar menú de ingresos
                        break;
                    case 7:
                        gastoView.displayMenu(); // Mostrar menú de gastos
                        break;
                    case 8:
                        cuentaTransaccionView.displayMenu(); // Mostrar menú de gastos
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
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
}