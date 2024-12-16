/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Compilador;

import java.util.Stack;

/**
 *
 * @author chris
 */
public class codigoIntermedio {
    
    Stack<String> pilaPrincipal = new Stack<>();
    int v1, v2, res, contv=0;
    String procedimiento="";
    char caracter;
    
    public codigoIntermedio(String cadSemantico,Stack<String> pilaCI, String tablaS, String idS)
    {
       
        procedimiento=procedimiento +tablaS+"\n";
        
        Stack<String> pilaAux = new Stack<>();
        while(!pilaCI.isEmpty())
        {
            
            System.out.println("..."+pilaAux.push(pilaCI.pop()));
        }
        
        System.out.println("Codigo Intermedio");
        System.out.println("Contenido de la pila CI:");
        for (String item : pilaAux) {
            System.out.println(item);
        }
         String caracterActual;
        while(!pilaAux.isEmpty()) {
            
            caracterActual = pilaAux.pop();
             // Imprime el carácter actual (opcional)
            
            System.out.println("Caracter actual: " + caracterActual);
            
            
            //--
            if(caracterActual.matches("\\d+"))
            {
                
                pilaPrincipal.push(caracterActual);
                contv++;
                procedimiento=procedimiento+"V"+contv+": " + caracterActual+ "\n";
                System.out.println("V"+contv+": " + caracterActual);
//                procedimiento=procedimiento+"V"+contv+": " + caracterActual+ "\n";
                
            }else if(Character.isLetter(caracterActual.charAt(0)))
            {
             pilaPrincipal.push(caracterActual);
                contv++;
                procedimiento=procedimiento+"V"+contv+": " + caracterActual+ "\n";
                System.out.println("V"+contv+": " + caracterActual);
                System.out.println("letra");
            } 
            
            else if(caracterActual.equals("+"))
            {
                contv--;
                procedimiento=procedimiento+ "V"+contv+" = "+"V" +contv;
                contv++;
                procedimiento=procedimiento+" + "+"V"+contv+"\n";
                contv--;
                
                
            }else if(caracterActual.equals("-"))
            {
                 contv--;
                procedimiento=procedimiento+ "V"+contv+" = "+"V"+contv;
                contv++;
                procedimiento=procedimiento+" - "+"V"+contv+"\n";
                contv--;
            }else if(caracterActual.equals("*"))
            {
               
                 contv--;
                procedimiento=procedimiento+ "V"+contv+" = "+"V"+contv;
                contv++;
                procedimiento=procedimiento+" * "+"V"+contv+"\n";
                contv--;
            }else if(caracterActual.equals("/"))
            {
                
                contv--;
                procedimiento=procedimiento+ "V"+contv+" = "+"V"+contv;
                contv++;
                procedimiento=procedimiento+" / "+"V"+contv+"\n";
                contv--;
            }
            
        }
        procedimiento=procedimiento+idS+" = "+"V"+contv+"\n";
        
    }
    
    public String getProcedimiento()
    {
        return procedimiento;
    }
    
}
