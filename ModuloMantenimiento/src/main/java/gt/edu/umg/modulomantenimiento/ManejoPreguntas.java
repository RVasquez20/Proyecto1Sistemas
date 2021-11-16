/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.modulomantenimiento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


/**
 *
 * @author ghostman
 */
public class ManejoPreguntas {

    public Scanner entrada = new Scanner(System.in);
    private final String rutaArchivoPreguntas = "/usr/src/Archivos/Preguntas.csv";

    public ManejoPreguntas() {
    }

    /**
     * Funcion de menu principal, aca es el menu principal en el cual estan las
     * opciones y si no se selecciona una disponible espera 4 segundos y retorna
     * al menu principal
     */
    public void menuPrincipal() {
        int opc = 0;
        System.out.println("*------------------*");
        System.out.println("*  Menu Principal  *");
        System.out.println("*------------------*");
        System.out.println("| 1)Ver Preguntas  |");
        System.out.println("|2)Agregar Pregunta|");
        System.out.println("|      3)Exit      |");
        System.out.println("*------------------*");
        opc = entrada.nextInt();
        entrada.nextLine();
        switch (opc) {
            //Opcion para ver todas las preguntas disponibles junto a su categoria
            case 1: {
                verPreguntas();
                menuPrincipal();
                break;
            }
            //Opcion para agregar una pregunta nueva
            case 2: {
                agregarPregunta();
                menuPrincipal();
                break;
            }
            //Salir
            case 3: {
                System.exit(0);
                break;
            }
            //Recursividad al menu principal
            default: {
                try {
                    System.out.println("Opcion invalida");
                    Thread.sleep(4000);
                    menuPrincipal();
                } catch (InterruptedException ex) {
                    System.out.println("Error " + ex.getMessage());
                }
                break;
            }
        }
    }

    /**
     * Funcion que permite ver un listado ennumerado de las preguntas
     * disponibles junto a su categoria
     */
    public void verPreguntas() {
        if (verificarArchivo()) {
            try {
                System.out.println("No.\t\t\tCategoria\t\t\tPregunta");
                System.out.println("---------------------------------------------------------------");
                String textoLeido = "";
                int numero = 0;
                FileReader lecturaArchivoPreguntas = new FileReader(rutaArchivoPreguntas);
                BufferedReader bufferArchivoPreguntas = new BufferedReader(lecturaArchivoPreguntas);
                while ((textoLeido = bufferArchivoPreguntas.readLine()) != null) {
                    numero++;
                    String[] tokensResultantes = textoLeido.split(";");
                    System.out.println(numero + "\t\t" + tokensResultantes[0] + "\t\t" + tokensResultantes[1]);
                }
                bufferArchivoPreguntas.close();
                lecturaArchivoPreguntas.close();
                entrada.nextLine();
            } catch (FileNotFoundException ex) {
                System.out.println("Error ->>" + ex.getMessage());
            } catch (IOException ex) {
                System.out.println("Error ->>" + ex.getMessage());
            }
        } else {
            System.out.println("Archivo no existente, agrege una pregunta para generarlo...");
        }

    }

    /**
     * Funcion que sirve para verificar si el archivo existe o no, si existe hace
     * un
     * @return true, de lo contrario false
     */
    public boolean verificarArchivo() {
        File archivo = new File(rutaArchivoPreguntas);
        if (!archivo.exists()) {
            return false;
        }
        return true;
    }

    /**
     * Funcion la cual su utilidad es crear el archivo si en dado caso no
     * existiera esto se comprueba con la funcion verificarArchivo
     */
    public void crearArchivo() {
        File archivo = new File(rutaArchivoPreguntas);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException ex) {
                System.out.println("Error ->>" + ex.getMessage());
            }
        }

    }
/**
 * Funcion utilizada para ingresar una pregunta nueva segun la categoria seleccionada
 * al archivo respectivo
 */
    public void agregarPregunta() {
        try {
            if (!verificarArchivo()) {
                crearArchivo();
            }
            FileWriter escribirPreguntaNueva = new FileWriter(rutaArchivoPreguntas, true);
            String preguntaNueva = "";
            int categoriaNueva = 0;
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
            categoriaNueva = entrada.nextInt();
            entrada.nextLine();
            System.out.println("Ingrese la Pregunta:");
            preguntaNueva = entrada.nextLine();
            switch (categoriaNueva) {
                case 1: {
                    escribirPreguntaNueva.append("BASIC_COMMANDS;" + preguntaNueva + "\n");
                    break;
                }
                case 2: {
                    escribirPreguntaNueva.append("SHELL_SCRIPTS;" + preguntaNueva + "\n");
                    break;
                }
                case 3: {
                    escribirPreguntaNueva.append("SECURE_SHELL;" + preguntaNueva + "\n");
                    break;
                }
                case 4: {
                    escribirPreguntaNueva.append("POSIX_SEMAPHORES;" + preguntaNueva + "\n");
                    break;
                }
                case 5: {
                    escribirPreguntaNueva.append("MAVEN;" + preguntaNueva + "\n");
                    break;
                }
                case 6: {
                    escribirPreguntaNueva.append("JAVA_THREADS;" + preguntaNueva + "\n");
                    break;
                }
                case 7: {
                    escribirPreguntaNueva.append("DOCKERS;" + preguntaNueva + "\n");
                    break;
                }
                default: {
                    System.out.println("Opcion no disponible");
                    agregarPregunta();
                    break;
                }
            }
            escribirPreguntaNueva.flush();
            escribirPreguntaNueva.close();
            System.out.println("Pregunta Agregada Exitosamente :D");
        } catch (IOException ex) {
            System.out.println("Error ->>" + ex.getMessage());
        } 
    }
}
