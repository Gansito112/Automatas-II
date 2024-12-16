package Compilador;

import java.util.Stack;

/**
 *
 * @author chris
 */
public class Analizador_SintacticoLR {                                                        //13 

    String cabecera1[] = {"id", "num", "int", "float", "char", ",", ";", "+", "-", "*", "/", "(", ")",
        "$", "P", "Tipo", "V", "A", "S", "E", "T", "F", "="};

    String cabecera2[] = {"Q0", "Q1", "Q2", "Q3", "Q4", "Q5", "Q6", "Q7", "Q8", "Q9", "Q10",
        "Q11", "Q12", "Q13", "Q14", "Q15", "Q16", "Q17", "Q18", "Q19", "Q20",
        "Q21", "Q22", "Q23", "Q24", "Q25", "Q26", "Q27", "Q28", "Q29", "Q30",
        "Q31", "Q32", "Q33", "Q34", "Q35", "Q36", "Q37"
    };

    String producciones[][] = {
        {"P", "Tipo", "id", "V"},
        {"P", "A"},
        {"Tipo", "int"},
        {"Tipo", "float"},
        {"Tipo", "char"},
        {"V", ",", "id", "V"},
        {"V", ";", "P"},
        {"A", "id", "=", "S", ";"},
        {"S", "+", "E"},
        {"S", "-", "E"},
        {"S", "E"},
        {"E", "E", "+", "T"},
        {"E", "E", "-", "T"},
        {"E", "T"},
        {"T", "T", "*", "F"},
        {"T", "T", "/", "F"},
        {"T", "F"},
        {"F", "(", "E", ")"},
        {"F", "id"},
        {"F", "num"}
    };

    String tabla[][] = { //

        {"Q7", "", "Q4", "Q5", "Q6", "", "", "", "", "", "", "", "", "", "Q1", "Q2", "", "Q3", "", "", "", "", ""}, //0
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "P0", "", "", "", "", "", "", "", "", ""},//1
        {"Q8", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},//2
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "P2", "", "", "", "", "", "", "", "", ""},//3
        {"P3", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},//4
        {"P4", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},//5
        {"P5", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},//6
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "Q9"},//7
        {"", "", "", "", "", "Q11", "Q12", "", "", "", "", "", "", "", "", "", "Q10", "", "", "", "", "", ""},//8
        {"Q20", "Q21", "", "", "", "", "", "Q14", "Q15", "", "", "Q19", "", "", "", "", "", "", "Q13", "Q16", "Q17", "Q18", ""},//9
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "P1", "", "", "", "", "", "", "", "", ""},//10
        {"Q22", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},//11
        {"Q7", "", "Q4", "Q5", "Q6", "", "", "", "", "", "", "", "", "", "Q23", "Q2", "", "Q3", "", "", "", "", ""},//12
        {"", "", "", "", "", "", "Q24", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},//13
        {"Q20", "Q21", "", "", "", "", "", "", "", "", "", "Q19", "", "", "", "", "", "", "", "Q25", "Q17", "Q18", ""},//14
        {"Q20", "Q21", "", "", "", "", "", "", "", "", "", "Q19", "", "", "", "", "", "", "", "Q26", "Q17", "Q18", ""},//15
        {"", "", "", "", "", "", "P11", "Q27", "Q28", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},//16
        {"", "", "", "", "", "", "P14", "P14", "P14", "Q29", "Q30", "", "P14", "", "", "", "", "", "", "", "", "", ""},//17
        {"", "", "", "", "", "", "P17", "P17", "P17", "P17", "P17", "", "P17", "", "", "", "", "", "", "", "", "", ""},//18
        {"Q20", "Q21", "", "", "", "", "", "", "", "", "", "Q19", "", "", "", "", "", "", "", "Q31", "Q17", "Q18", ""},//19
        {"", "", "", "", "", "", "P19", "P19", "P19", "P19", "P19", "", "P19", "", "", "", "", "", "", "", "", "", ""},//20
        {"", "", "", "", "", "", "P20", "P20", "P20", "P20", "P20", "", "P20", "", "", "", "", "", "", "", "", "", ""},//21
        {"", "", "", "", "", "Q11", "Q12", "", "", "", "", "", "", "", "", "", "Q32", "", "", "", "", "", ""},//22
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "P7", "", "", "", "", "", "", "", "", ""},//23
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "P8", "", "", "", "", "", "", "", "", ""},//24
        {"", "", "", "", "", "", "", "Q27", "Q28", "", "", "", "", "P9", "", "", "", "", "", "", "", "", ""},//25
        {"", "", "", "", "", "", "", "Q27", "Q28", "", "", "", "", "P10", "", "", "", "", "", "", "", "", ""},//26
        {"Q20", "Q21", "", "", "", "", "", "", "", "", "", "Q19", "", "", "", "", "", "", "", "", "Q33", "Q18", ""},//27
        {"Q20", "Q21", "", "", "", "", "", "", "", "", "", "Q19", "", "", "", "", "", "", "", "", "Q34", "Q18", ""},//28
        {"Q20", "Q21", "", "", "", "", "", "", "", "", "", "Q19", "", "", "", "", "", "", "", "", "", "Q35", ""},//29
        {"Q20", "Q21", "", "", "", "", "", "", "", "", "", "Q19", "", "", "", "", "", "", "", "", "", "Q36", ""},//30
        {"", "", "", "", "", "", "", "Q27", "Q28", "", "", "", "Q37", "", "", "", "", "", "", "", "", "", ""},//31
        {"", "", "", "", "", "", "", "", "", "", "", "", "", "P6", "", "", "", "", "", "", "", "", ""},//32
        {"", "", "", "", "", "", "P12", "P12", "P12", "Q29", "Q30", "", "P12", "", "", "", "", "", "", "", "", "", ""},//33
        {"", "", "", "", "", "", "P13", "P13", "P13", "Q29", "Q30", "", "P13", "", "", "", "", "", "", "", "", "", ""},//34
        {"", "", "", "", "", "", "P15", "P15", "P15", "P15", "P15", "", "P15", "", "", "", "", "", "", "", "", "", ""},//35
        {"", "", "", "", "", "", "P16", "P16", "P16", "P16", "P16", "", "P16", "", "", "", "", "", "", "", "", "", ""},//36
        {"", "", "", "", "", "", "P18", "P18", "P18", "P18", "P18", "", "P18", "", "", "", "", "", "", "", "", "", ""},//37
    };
    Stack<String> pilaEstados = new Stack<>();
    int estadoAct, error=1;
    String accion, resultadoF="";

    public Analizador_SintacticoLR(String entrada) {

        //Inicio analisis
        entrada = entrada + "$#"; // Añade un símbolo de fin de cadena
        System.out.println("Entrada: " + entrada);
        estadoAct = 0;
        pilaEstados.push("$"); // Estado inicial
        pilaEstados.push("0"); // Estado inicial

        // Imprimir el contenido de la pila
        System.out.println("Contenido de la pila:");
        for (String elemento : pilaEstados) {
            System.out.println(elemento);
        }
        String[] simbolos = entrada.split("#");
        String simboloAct, produccion;
        int posProduc;

        int i = 0, z = 0;
        // Recorrer toda la cadena de simbolos
        while (!pilaEstados.empty()) {

            simboloAct = simbolos[z];
            z++;
            resultadoF=resultadoF+"Estado Act Q:" + estadoAct + " Simbolo: " + simboloAct+"\n";
            System.out.println("Estado Act Q:" + estadoAct + " Simbolo: " + simboloAct);

            //Buscar la ACCION en la tabla 
            int posSimbolo = buscarCaracter(simboloAct);
            System.out.println("Posicion Estado(FILA): Q" + estadoAct + ", Simbolo(" + simboloAct + ") en columna: " + buscarCaracter(simboloAct));
            if (posSimbolo != -1) {

                accion = tabla[estadoAct][posSimbolo];
                if (accion.equals("")) {
                     resultadoF=resultadoF+"Se esperaba un: "+ obtenerElementosEnPosiciones(cabecera1,obtenerPosicionesNoVaciasComoCadena(tabla, estadoAct))+"\n Cadena no aceptada...\n";
                    System.out.println("Se esperaba un: "+ obtenerElementosEnPosiciones(cabecera1,obtenerPosicionesNoVaciasComoCadena(tabla, estadoAct)));
                    System.out.println("Cadena no aceptada...: " + accion);
                    error=-1;
                    break;
                }
                System.out.println("Accion: " + accion);

                //Checar si es desplazamiento(Q) o reducción(P)
                if (accion.startsWith("P")) {
                    if(accion.equals("P0"))
                    {
                        resultadoF=resultadoF+"Cadena Aceptada \n";
                        System.out.println("Cadena Aceptada");
                        break;
                    }
                    StringBuilder cadenaProduc = new StringBuilder();
                    
                    posProduc = Integer.parseInt(accion.substring(1));
                    produccion = producciones[posProduc - 1][0];

                    for (int j = producciones[posProduc - 1].length - 1; j >= 0; j--) {
                        cadenaProduc.append(producciones[posProduc - 1][j]); // Añadir el elemento
                        if (j > 0) {
                            cadenaProduc.append("#"); // Añadir un espacio entre los elementos
                        }
                    }
                     // Convertir StringBuilder a String
                    String simbolosProduc = cadenaProduc.toString();
                    System.out.println("Produccion: " + simbolosProduc);
                    System.out.println("Simbolo Produccion: " + produccion);

                    String[] simbolosP = simbolosProduc.split("#");
                    // Imprimir todos los elementos del arreglo
                    System.out.println("Contenido de simbolosP:");
                    for (String simbolo : simbolosP) {
                        System.out.println(simbolo);
                    }

                    boolean ban = true;
                    String pop;
                    while (ban) {

                        pop = pilaEstados.pop();
                        // Imprimir el contenido de la pila
                        System.out.println("Contenido de la pila:");
                        for (String elemento : pilaEstados) {
                            System.out.println(elemento);
                        }
                        if (pop.equals(simbolosP[simbolosP.length-2])) {
                            
                            ban = false;
                            estadoAct = Integer.parseInt(pilaEstados.peek());
                        }
                    }
                    pilaEstados.push(produccion);
                    //
                    //Buscar la ACCION en la tabla 
                    posSimbolo = buscarCaracter(produccion);
                    if (posSimbolo != -1) {
                        accion = tabla[estadoAct][posSimbolo];
                        System.out.println("EstaAct: " + estadoAct + "PosSimbolo: " + posSimbolo);
                        if (accion.equals("")) {
                            //
                            resultadoF=resultadoF+"Se esperaba un: "+ obtenerElementosEnPosiciones(cabecera1,obtenerPosicionesNoVaciasComoCadena(tabla, estadoAct))+"\"Cadena no aceptada...\n";
                            System.out.println("Se esperaba un: "+ obtenerElementosEnPosiciones(cabecera1,obtenerPosicionesNoVaciasComoCadena(tabla, estadoAct)));
                            System.out.println("Cadena no aceptada...: " + accion);
                            error=-1;
                            System.out.println("Accion: " + accion);
                            break;
                        }
                        pilaEstados.push(accion.substring(1));
                        // Imprimir el contenido de la pila
                        System.out.println("Contenido de la pila:");
                        for (String elemento : pilaEstados) {
                            System.out.println(elemento);
                            //
                            estadoAct = Integer.parseInt(accion.substring(1));
                        }
                    }
                    z--;
                    i++;

                } else if (accion.startsWith("Q")) { 
                        //obtener el estado de la accion
                        estadoAct = Integer.parseInt(accion.substring(1));
                        pilaEstados.add(simboloAct);
                        pilaEstados.add(String.valueOf(estadoAct));
                        
                        i++;
                }
            } else {
                System.out.println("Error simbolo no encontrado...");
                error=-1;
            }
            // Imprimir el contenido de la pila
                        System.out.println("Contenido de la pila:");
                        for (String elemento : pilaEstados) {
                            System.out.println(elemento);
                        }
            
        }
    }
    //Método para obtener la posición del caracter
    public int buscarCaracter(String simbolo) {
        for (int i = 0; i < cabecera1.length; i++) {
            if (cabecera1[i].equals(simbolo)) {
                return i; // Devuelve la posición donde se encontró
            }
        }
        return -1; // Si no se encontró, devuelve -1
    }
    
    public static String obtenerPosicionesNoVaciasComoCadena(String[][] matriz, int fila) {
    StringBuilder posicionesNoVacias = new StringBuilder();

    // Verificar si la fila es válida
    if (fila >= 0 && fila < matriz.length) {
        for (int i = 0; i < matriz[fila].length; i++) {
            String elemento = matriz[fila][i];
            if (elemento != null && !elemento.isEmpty()) {
                if (posicionesNoVacias.length() > 0) {
                    posicionesNoVacias.append(", "); // Agregar un delimitador
                }
                posicionesNoVacias.append(i); // Guardar la posición
            }
        }
    } else {
        System.out.println("Índice de fila fuera de rango.");
    }

    return posicionesNoVacias.toString();
}
    
 public static String obtenerElementosEnPosiciones(String[] arreglo, String posiciones) {
    StringBuilder elementos = new StringBuilder();
    
    // Convertir la cadena de posiciones en un arreglo de enteros
    String[] posicionesArray = posiciones.split(",\\s*"); // Dividir por coma y eliminar espacios
    for (String posicionStr : posicionesArray) {
        int posicion = Integer.parseInt(posicionStr.trim()); // Convertir cada posición a entero
        
        // Verificar si la posición es válida
        if (posicion >= 0 && posicion < arreglo.length) {
            if (elementos.length() > 0) {
                elementos.append(", "); // Agregar un delimitador
            }
            elementos.append(arreglo[posicion]); // Agregar el elemento del arreglo en la posición
        }
    }
    
    return elementos.toString();
}
 
 public int getErrorSintactico()
 {
     return error;
 }
 
 public String getAnalisisSintactico()
 {
     return resultadoF;
 }

}
