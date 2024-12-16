/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Compilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author chris
 */
public class Analizador_Semantico {

    Stack<String> pilaOpersArit = new Stack<>();
    Stack<String> pilaAuxiliar = new Stack<>();
    Stack<String> pilaAux2 = new Stack<>();
    Stack<String> pilaCI = new Stack<>();

    String ExpPosF = "", temp, nume = "";
    boolean ban = false, errorSemantico = false;

    public Analizador_Semantico(String entrada, ArrayList<String> tiposDatos) {
        errorSemantico = false;
        System.out.println("Analisis Semantico---------------------------------------------------------------");
        //--------------
        // Extraer IDs de la cadena
        HashSet<String> idsEnCadena = extraerIDs(entrada);
        // Validar IDs

        if (verificarIDs(tiposDatos, idsEnCadena)) {
            // Validar operaciones en la expresión
            validarExpresion(tiposDatos, entrada);
           if(!errorSemantico)
           {
                char caracterActual, caracterAnterior;
            int j = 0;

            // Recorre la cadena carácter por carácter
            for (int i = 0; i < entrada.length(); i++) {
                // Guarda el carácter en la posición actual en la variable
                caracterActual = entrada.charAt(i);
                if (i >= 2) {
                    j++;
                    caracterAnterior = entrada.charAt(j);
                    System.out.println("Caracter Anterior: " + caracterAnterior);

                    if (!Character.isDigit(caracterAnterior) && Character.isDigit(caracterActual)) {
                        nume = nume + caracterActual;
                    }

                    if (Character.isDigit(caracterActual) && Character.isDigit(caracterAnterior)) {
                        nume = nume + caracterActual;
                    }

                    if (Character.isDigit(caracterAnterior) && !Character.isDigit(caracterActual)) {
                        pilaCI.push(nume);
                        System.out.println("Guardar: " + nume);
                        nume = "";
                    }

                }
                char elemento;
                // Imprime el carácter actual (opcional)

                System.out.println("Caracter actual evaluado: " + caracterActual);

                if (Character.isLetter(caracterActual)) {
                    System.out.println("-----------Op1: " + caracterActual);
                    ExpPosF = ExpPosF + String.valueOf(caracterActual);
                    pilaCI.push(String.valueOf(caracterActual));
                    System.out.println("CadenaExp: " + ExpPosF);
                } else if (Character.isDigit(caracterActual)) {
                    System.out.println("-----------Op1: " + caracterActual);
                    ExpPosF = ExpPosF + String.valueOf(caracterActual);
                    System.out.println("CadenaExp: " + ExpPosF);
                } else if (String.valueOf(caracterActual).equals("(")) {
                    System.out.println("-----------Op2: " + caracterActual);
                    pilaOpersArit.push(String.valueOf(caracterActual));
                } else if (String.valueOf(caracterActual).equals(")")) {
                    System.out.println("-----------Op3: " + caracterActual);
                    temp = "";
                    while (!pilaOpersArit.isEmpty()) {
                        elemento = pilaOpersArit.pop().charAt(0);
//                    System.out.println("elemento: " + elemento);
                        if (elemento == '(') {
                            break; // Salir del bucle si se encuentra '('
                        }
                        ExpPosF = ExpPosF + String.valueOf(elemento);
                        pilaCI.push(String.valueOf(elemento));
                        System.out.println("CadenaExp: " + ExpPosF);

                    }

                } else if (String.valueOf(caracterActual).equals("+") || String.valueOf(caracterActual).equals("-")
                        || String.valueOf(caracterActual).equals("*") || String.valueOf(caracterActual).equals("/")) {
                    System.out.println("-----------Op4: " + caracterActual);
                    for (String info : pilaOpersArit) {
                        System.out.println(info);
                    }

                    while (!pilaOpersArit.isEmpty()) {
//                    System.out.println("Comienzo...");
                        char ultimoPop = pilaOpersArit.pop().charAt(0);
                        System.out.println("Ultimo pop: " + ultimoPop);
                        if (compararPrioridad(caracterActual, ultimoPop) == 1) {
                            System.out.println("Caracter de Mayor o Igual Prioridad: " + caracterActual);
                            if (String.valueOf(ultimoPop).equals("/")) {
                                pilaAux2.push(String.valueOf(ultimoPop));
                                ban = true;
                            } else {
                                ExpPosF = ExpPosF + ultimoPop;
                                pilaCI.push(String.valueOf(ultimoPop));
                                System.out.println("CadenaExp: " + ExpPosF);
                            }

                        } else {
                            System.out.println("Caracter de menor prioridad: " + ultimoPop);
                            System.out.println("Auxiliar: " + pilaAuxiliar.push(String.valueOf(ultimoPop)));

                            for (String info : pilaAuxiliar) {
//                            System.out.println("ciclo Auxiliar: " + info);
                            }

                        }

                    }
                    while (!pilaAuxiliar.isEmpty()) {
                        System.out.println("pushPilaPrincipal: " + pilaOpersArit.push(pilaAuxiliar.pop()));

                    }

                    for (String info : pilaOpersArit) {
//                    System.out.println("ciclo pilaPrincipal: " + info);
                    }

                    pilaOpersArit.push(String.valueOf(caracterActual));

                } else if (String.valueOf(caracterActual).equals(";")) {
                    String popUltimo;
                    System.out.println("-----------Op5: " + caracterActual);
                    while (!pilaOpersArit.isEmpty()) {
                        popUltimo = pilaOpersArit.pop();
                        ExpPosF = ExpPosF + popUltimo;
                        System.out.println("CadenaExp: " + ExpPosF);
                        pilaCI.push(popUltimo);
                    }
                }

            }
            //---
            if (ban) {
                ExpPosF = ExpPosF + pilaAux2.pop();
                pilaCI.push("/");
            }
            //fin de todo
            System.out.println("Cadena Semantica: " + ExpPosF);
           }      
        } else {
            errorSemantico = true;
        }
    }

    public int compararPrioridad(char simboloActual, char simboloPila) {
        // Obtener la prioridad de ambos símbolos
        int prioridadActual = obtenerPrioridad(simboloActual);
//        System.out.println("Ac: " + obtenerPrioridad(simboloActual));
        int prioridadPila = obtenerPrioridad(simboloPila);
//        System.out.println("Pila: " + obtenerPrioridad(simboloPila));
        // Comparar prioridades
        if (prioridadPila >= prioridadActual) {
            System.out.println("1");
            return 1; // simboloActual tiene mayor o igual prioridad
        } else {
            System.out.println("-1");
            return -1; // simboloActual tiene menor prioridad
        }
    }

    public int obtenerPrioridad(char operador) {
        return switch (operador) {
            case '+', '-' ->
                1; // Menor prioridad
            case '*', '/' ->
                2; // Mayor prioridad
            default ->
                -1; // Para '(' u otros caracteres no operacionales
        };
    }

    public static HashSet<String> extraerIDs(String expresion) {
        HashSet<String> ids = new HashSet<>();
        Pattern pattern = Pattern.compile("[a-zA-Z]+"); // Detecta solo letras
        Matcher matcher = pattern.matcher(expresion);

        while (matcher.find()) {
            ids.add(matcher.group());
        }

        return ids;
    }

    public boolean verificarIDs(ArrayList<String> array, HashSet<String> idsEnCadena) {
        // Crear un conjunto de IDs disponibles en el array
        HashSet<String> idsDisponibles = new HashSet<>();
        for (String elemento : array) {
            String[] partes = elemento.split("#");
            if (partes.length > 0) {
                idsDisponibles.add(partes[0]);
            }
        }

        // Verificar si todos los IDs de la cadena están en el array
        for (String id : idsEnCadena) {
            if (!idsDisponibles.contains(id)) {
                JOptionPane.showMessageDialog(null, "El ID '" + id + "' No esta inicializado...");
                return false;
            }
        }
        return true;
    }

    public void validarExpresion(ArrayList<String> variables, String expresion) {

        // Mapa para guardar los tipos de cada variable
        Map<String, String> mapaTipos = new HashMap<>();
        for (String var : variables) {
            String[] partes = var.split("#");
            if (partes.length == 2) {
                mapaTipos.put(partes[0], partes[1]); // id -> tipo
            }
        }

        // Extraer las operaciones y validar tipos
        Pattern pattern = Pattern.compile("[a-zA-Z]+|[+\\-*/()]");
        Matcher matcher = pattern.matcher(expresion);

        Stack<String> operandos = new Stack<>();
        Stack<String> operadores = new Stack<>();

        while (matcher.find()) {
            String token = matcher.group();

            if (mapaTipos.containsKey(token)) {
                // Si el token es una variable, obtenemos su tipo
                operandos.push(mapaTipos.get(token));
            } else if (isOperator(token)) {
                // Si es un operador, lo apilamos
                while (!operadores.isEmpty() && precedence(operadores.peek()) >= precedence(token)) {
                    if (!evaluarOperacion(operandos, operadores.pop())) {
                        return; // Operación inválida
                    }
                }
                operadores.push(token);
            } else if (token.equals("(")) {
                operadores.push(token);
            } else if (token.equals(")")) {
                // Procesar hasta que encontremos el paréntesis de apertura
                while (!operadores.isEmpty() && !operadores.peek().equals("(")) {
                    if (!evaluarOperacion(operandos, operadores.pop())) {
                        return; // Operación inválida
                    }
                }
                if (operadores.isEmpty() || !operadores.pop().equals("(")) {
                    System.out.println("Error: Paréntesis desbalanceados.");
                    return;
                }
            }
        }

        // Procesar operaciones restantes
        while (!operadores.isEmpty()) {
            if (!evaluarOperacion(operandos, operadores.pop())) {
                return; // Operación inválida
            }
        }

        // Verificar que solo quede un resultado final
        if (operandos.size() == 1) {
            System.out.println("La expresión es válida.");
        } else {
            System.out.println("Error: La expresión es inválida.");
        }
    }

    public boolean evaluarOperacion(Stack<String> operandos, String operador) {
        if (operandos.size() < 2) {
            errorSemantico = true;
            JOptionPane.showMessageDialog(null, "Error: Operación inválida. Faltan operandos para el operador '" + operador + "'.");
            return false;
        }

        String tipo2 = operandos.pop(); // Operando derecho
        String tipo1 = operandos.pop(); // Operando izquierdo

        // Validar tipos para el operador
        String resultadoTipo = validarOperacion(tipo1, tipo2, operador);
        if (resultadoTipo == null) {
            errorSemantico = true;
            JOptionPane.showMessageDialog(null, "Error: Operación inválida entre tipos '" + tipo1 + "' y '" + tipo2 + "' con operador '" + operador + "'.");
            return false;
        }
        // El resultado de la operación se considera como un nuevo tipo
        operandos.push(resultadoTipo);
        return true;
    }

    public static boolean isOperator(String token) {
        return "+-*/".contains(token);
    }

    public static int precedence(String operador) {
        switch (operador) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 0;
        }
    }

    public static String validarOperacion(String tipo1, String tipo2, String operador) {
        // Reglas de validación de tipos
        if (tipo1.equals("int") && tipo2.equals("int")) {
            return "int";
        }
        if (tipo1.equals("float") || tipo2.equals("float")) {
            return "float"; // float tiene prioridad
        }
        if (tipo1.equals("char") && tipo2.equals("char") && operador.equals("+")) {
            return "char"; // Concatenación de chars
        }
        return null; // Operación inválida
    }

    public String getCadSemantica() {
        return ExpPosF;
    }

    public Stack<String> getPilaCI() {

        return pilaCI;
    }

    public boolean getErrorSemantico() {
        return errorSemantico;
    }

}
