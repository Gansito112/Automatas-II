package Compilador;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analizador_Lexico {

    String tokensSintactico = "", cadenaSemantico = "", tablaS = "", idS = "";

    Stack<String> numero = new Stack<>();
    ArrayList<String> errores = new ArrayList<>();
    ArrayList<String> TiposDatos = new ArrayList<>();
    ArrayList<String> tokensNoreconocidos = new ArrayList<>();

    //Solo acepta como maximo 20 id´s
    String[][] datos = new String[20][2];

    public String MostrarAnalisis(String codFuente) {
        String devolverAnalisis = "", palabraReservada, id, operadoresAritmeticos,
                operadoresAsignacion, num, parentesis, puntoComa, punto, coma,
                palabraNoreconocida;
        int lineNumber = 1;
        String prevToken = "", prevTokenSem = "", prevTipoDato = "";
        boolean banSemantico = false, banSemantico1 = false;

        // Variable para llevar el seguimiento de la posición de inserción
        int indice = 0;

        Pattern patronBusq = Pattern.compile(
                "\\b(int|char|float)\\b"
                + "|([a-zA-Z]+)"
                + "|([+\\-*/%])"
                + "|([=]+)"
                + "|(-?[0-9]+)"
                + "|([()])"
                + "|(;)"
                + "|(\\.)"
                + "|(,)"
                + "|([@#$&])"
        );

        Matcher buscarCoincidencia = patronBusq.matcher(codFuente);

        while (buscarCoincidencia.find()) {

            palabraReservada = buscarCoincidencia.group(1);
            if (palabraReservada != null) {
                devolverAnalisis += palabraReservada + "   ";
                tokensSintactico = tokensSintactico + palabraReservada + "#";
                prevToken = palabraReservada;
                prevTokenSem = palabraReservada;

                if (palabraReservada.equals("int")) {
                    prevTipoDato = palabraReservada;
                }else if(palabraReservada.equals("char")) {
                    prevTipoDato = palabraReservada;
                }else if(palabraReservada.equals("float"))
                    prevTipoDato=palabraReservada;

            } else {
                id = buscarCoincidencia.group(2);
                if (id != null) {
                    devolverAnalisis += "id  ";
                    tokensSintactico = tokensSintactico + "id" + "#";

                    //Guardar id´s para comprobar si estan repetidos
                    if (!prevTokenSem.equals(";") && !prevTokenSem.equals("(") && !prevTokenSem.equals(")")
                            && !prevTokenSem.equals("=") && !prevTokenSem.equals("*") && !prevTokenSem.equals("/")
                            && !prevTokenSem.equals("+") && !prevTokenSem.equals("-")) {
                        if (indice < 20) {
                            agregarRegistro(datos, id, prevToken, indice++);
                            TiposDatos.add(id + "#" + prevTipoDato);
                        } else {
                            System.out.println("No hay espacio disponible en el arreglo para más registros.");
                        }
                    }

                    if (prevTokenSem.equals(";")) {
                        banSemantico = true;
                        idS = id;
                    }

                    prevTokenSem = id;

                    // Intentamos agregar más registros (podría fallar si el arreglo está lleno)
                    if (banSemantico1 == true) {
                        cadenaSemantico = cadenaSemantico + id;
                    }
                } else {
                    operadoresAritmeticos = buscarCoincidencia.group(3);
                    if (operadoresAritmeticos != null) {
                        prevTokenSem = operadoresAritmeticos;
                        devolverAnalisis += operadoresAritmeticos + "  ";
                        tokensSintactico = tokensSintactico + operadoresAritmeticos + "#";
                        if (banSemantico1 == true) {
                            cadenaSemantico = cadenaSemantico + operadoresAritmeticos;
                        }
                    } else {
                        operadoresAsignacion = buscarCoincidencia.group(4);
                        if (operadoresAsignacion != null) {

                            if (banSemantico == true && operadoresAsignacion.equals("=")) {
                                banSemantico1 = true;

                            }
                            prevTokenSem = operadoresAsignacion;
                            devolverAnalisis += operadoresAsignacion + "  ";
                            tokensSintactico = tokensSintactico + operadoresAsignacion + "#";
                        } else {
                            num = buscarCoincidencia.group(5);
                            if (num != null) {

                                numero.push(num);
                                prevTokenSem = num;
                                devolverAnalisis += "num  ";
                                tokensSintactico = tokensSintactico + "num" + "#";
                                if (banSemantico1 == true) {
                                    cadenaSemantico = cadenaSemantico + num;
                                }
                            } else {
                                parentesis = buscarCoincidencia.group(6);
                                if (parentesis != null) {
                                    prevTokenSem = parentesis;
                                    devolverAnalisis += parentesis + "  ";
                                    tokensSintactico = tokensSintactico + parentesis + "#";
                                    if (banSemantico1 == true) {
                                        cadenaSemantico = cadenaSemantico + parentesis;
                                    }
                                } else {
                                    puntoComa = buscarCoincidencia.group(7);
                                    if (puntoComa != null) {
                                        prevTokenSem = puntoComa;
                                        devolverAnalisis += puntoComa + "  ";
                                        tokensSintactico = tokensSintactico + puntoComa + "#";
                                        if (banSemantico1 == true) {
                                            cadenaSemantico = cadenaSemantico + puntoComa;
                                        }
                                    } else {
                                        punto = buscarCoincidencia.group(8);
                                        if (punto != null) {
                                            prevTokenSem = punto;
                                            devolverAnalisis += punto + "  ";
                                            tokensSintactico = tokensSintactico + punto + "#";
                                        } else {
                                            coma = buscarCoincidencia.group(9);
                                            if (coma != null) {
                                                prevTokenSem = coma;
                                                devolverAnalisis += coma + "  ";
                                                tokensSintactico = tokensSintactico + coma + "#";
                                            } else {
                                                palabraNoreconocida = buscarCoincidencia.group(10);
                                                if (palabraNoreconocida != null) {

                                                    errores.add("Error lexico en la linea " + lineNumber + ": " + palabraNoreconocida);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // Incrementar el contador de líneas
            if (buscarCoincidencia.end() < codFuente.length() && codFuente.charAt(buscarCoincidencia.end()) == '\n') {
                lineNumber++;
                devolverAnalisis += "\n";
            }
           
        }

//        // Imprimir los valores almacenados en el arreglo
//        for (int i = 0; i < 20; i++) {
//            if (datos[i][0] != null) { // Solo imprime filas no vacías
//                System.out.println(i + ": ID: " + datos[i][0] + ", Tipo: " + datos[i][1]);
//                tablaS = tablaS + datos[i][1] + " " + datos[i][0] + ";" + "\n";
//            }
//        }
//
//         //contenido del array de tokens
            for (int i = 0; i < TiposDatos.size(); i++) {
                System.out.println(TiposDatos.get(i));
            }
            
        return devolverAnalisis;
    }

    // Método para agregar un registro si hay espacio
    public static void agregarRegistro(String[][] datos, String id, String tipo, int indice) {
        if (indice < datos.length) { // Verificar si hay espacio en el arreglo
            datos[indice][0] = id;   // Insertar id
            datos[indice][1] = tipo; // Insertar tipo
        } else {
            System.out.println("Arreglo lleno. No se puede agregar el id: " + id);
        }
    }

    // Método para verificar duplicados en la columna de ID
    public static int verificarDuplicados(String[][] datos) {
        for (int i = 0; i < datos.length; i++) {
            if (datos[i][0] != null) { // Verificar si el ID no es nulo
                for (int j = i + 1; j < datos.length; j++) {
                    if (datos[j][0] != null && datos[i][0].equals(datos[j][0])) {
                        return -1; // Si se encuentra un duplicado, retorna -1
                    }
                }
            }
        }
        return 0; // Retorna 0 si no hay duplicados
    }

    public String getTokens() {
        return tokensSintactico;
    }

    public ArrayList<String> getErroresLexico() {
        return errores;
    }

    public int getRepetido() {
        return verificarDuplicados(datos);
    }

    public String getCadenaSemantico() {
        return cadenaSemantico;
    }

    public String getTablaS() {
        return tablaS;
    }

    public String getidS() {
        return idS;
    }

    public ArrayList<String> getTiposDatos() {
        return TiposDatos;
    }

    //Al final no se ocupo...para el codigo intermedio
    public Stack<Integer> getNumerosPila() {
        Stack<Integer> numeroAux = new Stack<>();
        while (!numero.isEmpty()) {
            numeroAux.push(Integer.parseInt(numero.pop()));
        }
        return numeroAux;
    }

}
