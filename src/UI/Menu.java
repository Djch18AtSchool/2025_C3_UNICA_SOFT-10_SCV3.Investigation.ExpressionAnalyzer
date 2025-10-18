package UI;

import Services.ExpressionAnalyzer;

import java.util.List;
import java.util.Scanner;

import static Services.ExpressionAnalyzer.infixToPostfix;

public class Menu {
    static Scanner scanner = new Scanner(System.in);

    /**
     * Muestra un menú interactivo para que el usuario pueda probar
     * la funcionalidad de análisis de expresiones.
     */
    public static void menu() {
        boolean exit = false;

        System.out.println("=====================================================");
        System.out.println("Bienvenido al Analizador de Expresiones Aritméticas");
        System.out.println("Este programa utiliza una Pila (implementada desde cero)");
        System.out.println("para convertir expresiones de Infijo a Postfijo.");
        System.out.println("=====================================================");

        while (!exit) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Convertir expresión Infija a Postfija");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.println("\n--- Nueva Conversión ---");
                    System.out.println("REGLA: Ingrese la expresión con espacios entre cada");
                    System.out.println("número y operador. Ejemplo: ( 5 + 10 ) * 2");
                    System.out.print("Expresión Infija: ");
                    String infix = scanner.nextLine();

                    ExpressionAnalyzer.ConversionResult result = infixToPostfix(infix);

                    System.out.println("\n--- Resultado ---");
                    if (result.isSuccess()) {
                        System.out.println("Conversión Exitosa ✅");
                        System.out.println("Expresión Postfija: " + result.getResultString());
                    } else {
                        System.err.println("Conversión Fallida ❌");
                        System.err.println("Error: " + result.getResultString());
                    }

                    System.out.print("\n¿Desea ver el paso a paso de la conversión? (s/n): ");
                    String seeSteps = scanner.nextLine();
                    if (seeSteps.equalsIgnoreCase("s")) {
                        displayStepLog(result.getStepLog());
                    }
                    break;
                case "2":
                    exit = true;
                    System.out.println("\nSaliendo del programa. ¡Adiós!");
                    break;
                default:
                    System.out.println("\nOpción no válida. Por favor, intente de nuevo.");
            }
        }
        scanner.close();
    }

    /**
     * Muestra el "paso a paso" de la conversión en una tabla.
     * @param log La lista de pasos generada por infixToPostfix.
     */
    public static void displayStepLog(List<String> log) {
        System.out.println("\n--- PASO A PASO DE LA CONVERSIÓN ---");

        // Imprimir cabecera
        System.out.printf("%-10s | %-20s | %s%n",
                "TOKEN LEÍDO", "PILA DE OPERADORES", "SALIDA POSTFIJA");

        // Separador
        System.out.println(new String(new char[60]).replace('\0', '-'));

        for (String step : log) {
            System.out.println(step);
        }
    }
}
