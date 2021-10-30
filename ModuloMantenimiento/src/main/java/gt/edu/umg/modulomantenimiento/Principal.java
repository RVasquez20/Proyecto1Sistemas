/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.modulomantenimiento;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author ghostman
 */
public class Principal {
 static Scanner entrada=new Scanner(System.in);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        menuPrincipal();
    }

    private static void menuPrincipal() {
        int opc=0;
        System.out.println("*------------------*");
        System.out.println("*  Menu Principal  *");
        System.out.println("*------------------*");
        System.out.println("| 1)Ver Preguntas  |");
        System.out.println("|2)Agregar Pregunta|");
        System.out.println("|      3)Exit      |");
        System.out.println("*------------------*");
        opc=entrada.nextInt();
        entrada.nextLine();
        switch(opc){
            case 1:{
            try {
                verPreguntas();
            } catch (IOException ex) {
                System.out.println("Error -->"+ex.getMessage());
            }
                menuPrincipal();
                break;
            } 
            case 2:{
            try {
                agregarPregunta();
            } catch (IOException ex) {
                System.out.println("Error -->"+ex.getMessage());
            }
                menuPrincipal();
                break;
            }
            case 3:{
                System.exit(0);
                break;
            }
            default:{
                System.out.println("Opcion invalida elija nuevamente...");
            try {
                sleep(4);
            } catch (InterruptedException ex) {
                System.out.println("Error -->"+ex.getMessage());
            }
                break;
            }
        }
    }

    private static void verPreguntas() throws IOException {
        System.out.println("No.\t\t\tCategoria\t\t\tPregunta");
        System.out.println("---------------------------------------------------------------");
        try {
            String texto="";
            int numero=0;
            FileReader f = new FileReader("/home/ghostman/Escritorio/ProyectoiFinal/Preguntas.csv");
            BufferedReader b = new BufferedReader(f);
            while ((texto = b.readLine()) != null) {
                numero++;
                String[] result = texto.split(";");
                System.out.println(numero+"\t\t"+result[0]+"\t\t"+result[1]);
            }
            b.close();
            entrada.nextLine();
        } catch (FileNotFoundException ex) {
           System.out.println("Error -->"+ex.getMessage());
        }

    }

    private static void agregarPregunta() throws IOException {
        FileWriter escribir = new FileWriter("/home/ghostman/Escritorio/ProyectoiFinal/Preguntas.csv",true);
        String preguntaNueva="";
        int categoriaNueva=0;

        System.out.println("*------------------*");
        System.out.println("*     Categorias   *");
        System.out.println("*------------------*");
        System.out.println("|1)BASIC_COMMANDS  |");
        System.out.println("|2)SHELL_SCRIPTS   |");
        System.out.println("|3)SECURE_SHELL    |");
        System.out.println("|4)POSIX_SEMAPHORES|");
        System.out.println("|5)MAVEN           |");
        System.out.println("|6)JAVA_THREADS    |");
        System.out.println("|7)DOCKERS         |");
        System.out.println("*------------------*");
        categoriaNueva=entrada.nextInt();
                        entrada.nextLine();
                        //verificar si se puede con print
        System.out.println("Ingrese la Pregunta:");

        preguntaNueva=entrada.nextLine();
        switch(categoriaNueva){
            case 1:{
                 escribir.append("BASIC_COMMANDS;"+preguntaNueva+"\n");
                break;
            }
             case 2:{
                 escribir.append("SHELL_SCRIPTS;"+preguntaNueva+"\n");
                break;
            }
              case 3:{
                 escribir.append("SECURE_SHELL;"+preguntaNueva+"\n");
                break;
            }
               case 4:{
                 escribir.append("POSIX_SEMAPHORES;"+preguntaNueva+"\n");
                break;
            }
                case 5:{
                 escribir.append("MAVEN;"+preguntaNueva+"\n");
                break;
            }
                 case 6:{
                 escribir.append("JAVA_THREADS;"+preguntaNueva+"\n");
                break;
            }
                      case 7:{
                 escribir.append("DOCKERS;"+preguntaNueva+"\n");
                break;
            }
                      default:{
                          System.out.println("Opcion no disponible");
                          agregarPregunta();
                          break;
                      }
        }
       
        escribir.close();
        System.out.println("Pregunta Agregada Exitosamente :D");
    }

}
