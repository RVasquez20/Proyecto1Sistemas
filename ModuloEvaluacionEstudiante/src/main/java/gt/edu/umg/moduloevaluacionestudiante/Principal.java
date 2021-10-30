/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.moduloevaluacionestudiante;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ghostman
 */
public class Principal {

    static Scanner entrada = new Scanner(System.in);
    private List<Integer> Indices = new ArrayList<>();
    static Semaphore sem;

    /**
     * @param args the command line arguments no static todo en clases
     */
    public static void main(String[] args) {
        sem = new Semaphore(1);
        Principal n1 = new Principal();
        hilo h1=new hilo(sem);
        h1.start();
        n1.menuPrincipal();
        
        
        try {
            h1.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
/**
 * 
 */
    private void menuPrincipal() {
        int opc = 0;
        System.out.println("*------------------*");
        System.out.println("*  Menu Principal  *");
        System.out.println("*------------------*");
        System.out.println("|1)BASIC_COMMANDS  |");
        System.out.println("|2)SHELL_SCRIPTS   |");
        System.out.println("|3)SECURE_SHELL    |");
        System.out.println("|4)POSIX_SEMAPHORES|");
        System.out.println("|5)MAVEN           |");
        System.out.println("|6)JAVA_THREADS    |");
        System.out.println("|7)DOCKERS         |");
        System.out.println("|      8)Exit      |");
        System.out.println("*------------------*");
        opc = entrada.nextInt();
        entrada.nextLine();
        switch (opc) {
            // sdfsdfsdfsdf
            case 1: {
                busquedaDeIndices("BASIC_COMMANDS");
                mostrarPreguntas("BASIC_COMMANDS");
                menuPrincipal();
                break;
            }
            case 2: {
busquedaDeIndices("DOCKERS");
                mostrarPreguntas("DOCKERS");
                menuPrincipal();
                break;
            }
            case 8: {
                System.exit(0);
                break;
            }
            default: {
                System.out.println("Opcion invalida elija nuevamente...");
                try {
                    sleep(4);
                } catch (InterruptedException ex) {
                    System.out.println("Error -->" + ex.getMessage());
                }
                break;
            }
        }
    }

    private void busquedaDeIndices(String Categoria) {
        try {
            String texto = "";
            int numero = 0;
            //url final
            FileReader f = new FileReader("/home/ghostman/Escritorio/ProyectoiFinal/Preguntas.csv");
            BufferedReader b = new BufferedReader(f);
            while ((texto = b.readLine()) != null) {
                numero++;
                String[] result = texto.split(";");
                if (result[0].equals(Categoria)) {
                    Indices.add(numero);
                }
            }
            b.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error -->" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error -->" + ex.getMessage());
        }
    }

    private void mostrarPreguntas(String Categoria) {
        ///hashmap,linkedlist,map
        Hashtable<Integer, Integer> indicesAleatorios = new Hashtable<>();

        while (indicesAleatorios.size() != 3) {
            Random rand = new Random();
            Integer randomInt = Indices.get(rand.nextInt(Indices.size()));
            indicesAleatorios.put(randomInt, randomInt);
        }

        System.out.println(indicesAleatorios);
        for (Map.Entry<Integer, Integer> elemento : indicesAleatorios.entrySet()) {

            String Respuesta = "";
            try {
                //todo esto en un objeto
                String texto = "";
                int numero = 0;
                FileReader f = new FileReader("/home/ghostman/Escritorio/ProyectoiFinal/Preguntas.csv");
               // FileWriter escribir = new FileWriter("/home/ghostman/Escritorio/ProyectoiFinal/respuestas.csv", true);
                BufferedReader b = new BufferedReader(f);
                
                while ((texto = b.readLine()) != null) {
                    numero++;
                    String[] result = texto.split(";");
                    if (numero == elemento.getKey()) {
                        System.out.println(Categoria);
                        System.out.println("------------------------");
                        System.out.println("Pregunta:" + result[1]);
                        System.out.println("Respuesta:");
                        Respuesta = entrada.nextLine();
                        
                        try {
                            //abrir el archivo escribir y cerrar
                            //aquire
                            sem.acquire();
                            FileWriter escribir = new FileWriter("/home/ghostman/Escritorio/ProyectoiFinal/respuestas.csv", true);
                            escribir.append(Categoria + ";" + numero + ";" + Respuesta + "\n");
                            escribir.flush();
                            escribir.close();
                            sem.release();
                            //release
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                //escribir.flush();//
                //escribir.close();
                b.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Error -->" + ex.getMessage());
            } catch (IOException ex) {
                System.out.println("Error -->" + ex.getMessage());
            }
        }
       
    }

}
