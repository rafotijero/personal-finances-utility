# Personal-finances-utility

## Descripción

La aplicación de finanzas personales es una aplicación diseñada para gestionar tus finanzas personales, permitiendo registrar y controlar tus ingresos, gastos y transacciones de manera sencilla. Este proyecto incluye funcionalidades principales como la creación, actualización, eliminación y visualización de ingresos, gastos y transacciones, así como la capacidad de procesar transacciones para mantener actualizados los saldos de las cuentas.

### Funcionalidades Principales

- **Gestión de Ingresos:** Crear, actualizar, eliminar y listar ingresos.
- **Gestión de Gastos:** Crear, actualizar, eliminar y listar gastos.
- **Gestión de Transacciones:** Crear, actualizar, eliminar y listar transacciones, además de procesarlas para actualizar los saldos de las cuentas.
- **Procesamiento de Transacciones:** Automatiza el ajuste de saldos de las cuentas según las transacciones registradas.

### Dependencias

- **Gson:** Para la serialización y deserialización de objetos JSON.
- **Java Standard Libraries:** Utilizadas para la manipulación de colecciones, entrada/salida de archivos y fechas.

## Build

### Requisitos Previos

- **Java Development Kit (JDK) 17 o superior**
- **Apache Maven 3.6.0 o superior**

### Instrucciones de Construcción

1. Clona el repositorio del proyecto:
   ```sh
   git clone https://github.com/tu-usuario/finances-tracker.git
   cd main

2. Uso del Proyecto:

   Ejecutar el jar en el IDE de su preferencia y saldrá un menú de Consola con múltiples opciones en donde podrá seleccionar una opción:
   Seleccione una opción:
   ```sh
   1. Gestionar categorías de cuenta
   2. Gestionar cuentas
   3. Gestionar categorías de gasto
   4. Gestionar categorías de ingreso
   5. Gestionar monedas
   6. Gestionar ingresos
   7. Gestionar gastos
   8. Procesar Transacciones
   0. Salir

Registro de categoría cuenta puede ser: "Ahorros", "Corriente", "Crédito", "Mancomunada"
Registro de cuentas puede ser: "Cuenta Sueldo", "Tarjeta de Crédito"
Registro de categoría gasto: "Alimentos", "Transporte", "Deudas"
Registro de categoría ingreso: "Sueldo", "Bono", "negocio"
Registro de monedas: "Dólares", "Pesos", "Soles"
Registro de ingresos: "Quincena", "Salario mensual", "Bono"
Registro de gastos: "Hipoteca", "Delivery", "Pasajes"
Registro de Transacciones: Aquí se relacionan los Ingresos, Gastos y Cuentas. En la opción de procesar se actualiza el saldo.

3. Base de datos
Al ejecutar el proyecto se crea una carpeta data donde se guardan los datos registrados en archivos json

4. Estructura del proyecto

personal-finances-utility/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── org/
│   │   │   │   ├── tijfuen/
│   │   │   │   │   ├── controller/
│   │   │   │   │   ├── model/
│   │   │   │   │   ├── service/
│   │   │   │   │   ├── util/
│   │   │   │   │   └── view/
│   │   └── resources/
│   │       └── data/
├── target/
├── pom.xml
└── README.md


