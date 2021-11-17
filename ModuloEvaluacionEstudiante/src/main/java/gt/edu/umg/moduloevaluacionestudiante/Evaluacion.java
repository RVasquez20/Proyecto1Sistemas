/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.moduloevaluacionestudiante;

import java.io.BufferedReader;
import java.io.File;
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
public class Evaluacion {

    private List<Integer> indices = new ArrayList<>();
    public final String rutaPreguntas = "/usr/src/Archivos/Preguntas.csv";
    public final String rutaRespuestas = "/usr/src/Archivos/respuestas.csv";
    public final String rutaReporte = "/usr/local/apache2/htdocs/Proyecto/Respuestas.html";
    Scanner entrada = new Scanner(System.in);
    public Semaphore sem;

    public Evaluacion(Semaphore sem) {
        this.sem = sem;
    }

    /**
     * La funcion menuPrincipal permite al estudiante seleccionar la categoria
     * que desea,para posteriormente solucionar cada pregunta planteada y al
     * finalizar la evaluacion retorna al menu principal
     */
    public void menuPrincipal() {
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
            //Este caso es para la evaluacion de la categoria BASIC_COMMANDS
            case 1: {
                busquedaDeIndices("BASIC_COMMANDS");
                mostrarPreguntas("BASIC_COMMANDS");
                menuPrincipal();
                break;
            }
            //Este caso es para la evaluacion de la categoria SHELL_SCRIPTS
            case 2: {
                busquedaDeIndices("SHELL_SCRIPTS");
                mostrarPreguntas("SHELL_SCRIPTS");
                menuPrincipal();
                break;
            }
            //Este caso es para la evaluacion de la categoria SECURE_SHELL
            case 3: {
                busquedaDeIndices("SECURE_SHELL");
                mostrarPreguntas("SECURE_SHELL");
                menuPrincipal();
                break;
            }
            //Este caso es para la evaluacion de la categoria POSIX_SEMAPHORES
            case 4: {
                busquedaDeIndices("POSIX_SEMAPHORES");
                mostrarPreguntas("POSIX_SEMAPHORES");
                menuPrincipal();
                break;
            }
            //Este caso es para la evaluacion de la categoria MAVEN
            case 5: {
                busquedaDeIndices("MAVEN");
                mostrarPreguntas("MAVEN");
                menuPrincipal();
                break;
            }
            //Este caso es para la evaluacion de la categoria JAVA_THREADS
            case 6: {
                busquedaDeIndices("JAVA_THREADS");
                mostrarPreguntas("JAVA_THREADS");
                menuPrincipal();
                break;
            }
            //Este caso es para la evaluacion de la categoria DOCKERS
            case 7: {
                busquedaDeIndices("DOCKERS");
                mostrarPreguntas("DOCKERS");
                menuPrincipal();
                break;
            }
            //Este caso es para salir
            case 8: {
                System.exit(0);
                break;
            }
            default: {
                System.out.println("Opcion invalida elija nuevamente...");
                try {
                    sleep(4);
                    menuPrincipal();
                } catch (InterruptedException ex) {
                    System.out.println("Error -->" + ex.getMessage());
                }
                break;
            }
        }
    }

    /**
     * Esta funcion recibe el parametro:
     *
     * @param categoria de tipo String, el cual como su nombre lo indica es la
     * categoria seleccionada por el estudiante, en esta funcion se busca el
     * numero de linea o indice , en el cual estan ubicadas todas las preguntas
     * con dicha categoria en el archivo de Preguntas.
     */
    public void busquedaDeIndices(String categoria) {
        try {
            indices.clear();
            String texto = "";
            int numero = 0;
            FileReader archivoPreguntas = new FileReader(rutaPreguntas);
            BufferedReader bufferPreguntas = new BufferedReader(archivoPreguntas);
            while ((texto = bufferPreguntas.readLine()) != null) {
                numero++;
                String[] result = texto.split(";");
                if (result[0].equals(categoria)) {
                    indices.add(numero);
                }
            }
            bufferPreguntas.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error -->" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error -->" + ex.getMessage());
        }
    }

    /**
     * Esta funcion recibe como parametro un String llamado categoria.
     *
     * @param categoria El cual es la categoria que el estudiante selecciono con
     * aterioridad, el fin de esta funcion es elegir 3 numeros aleatorios de el
     * conjunto de numeros los cuales identifican en que linea esta la pregunta
     * con la categoria respectiva. Posterior a la eleccion de los 3 numeros
     * aleatorios(sin repetidos) itera sobre cada uno de los selectos para
     * mostrarlos al usuario en la funcion llenadoDePreguntas.
     */
    public void mostrarPreguntas(String categoria) {
        Hashtable<Integer, Integer> indicesAleatorios = new Hashtable<>();

        while (indicesAleatorios.size() != 3) {
            Random rand = new Random();
            Integer randomInt = indices.get(rand.nextInt(indices.size()));
            indicesAleatorios.put(randomInt, randomInt);
        }
        for (Map.Entry<Integer, Integer> elemento : indicesAleatorios.entrySet()) {

            llenadoDePreguntas(elemento, categoria);
        }

    }

    /**
     * En esta funcion se reciben como parametros una variable de tipo int
     * (elemento) y una variable de tipo String (categoria).
     *
     * @param elemento
     * @param categoria El parametro elemento indica la linea en donde esta la
     * pregunta a realizar, el parametro categoria indica la categoria
     * seleccionada por el usuario. Esta funcion lee en el archivo de Preguntas
     * hasta encontrar el numero de linea enviada como parametro, posteriormente
     * hace un split de la linea y muestra al usuario la categoria, la pregunta
     * y queda en espera de su respuesta. En el momento en que el usuario
     * ingrese su respuesta se verifica el estado del semaforo y si esta libre
     * pasa , lo pone en espera, y escribe en el archivo de respuestas , los
     * datos respectivos con su formato apropiado. al terminar libera el
     * semaforo.
     */
    private void llenadoDePreguntas(Map.Entry<Integer, Integer> elemento, String categoria) {
        String respuesta = "";
        try {
            String texto = "";
            int numero = 0;
            FileReader archivoPreguntas = new FileReader(rutaPreguntas);
            BufferedReader bufferPreguntas = new BufferedReader(archivoPreguntas);

            while ((texto = bufferPreguntas.readLine()) != null) {
                numero++;
                String[] resultadoSplits = texto.split(";");
                if (numero == elemento.getKey()) {
                    System.out.println(categoria);
                    System.out.println("------------------------");
                    System.out.println("Pregunta:" + resultadoSplits[1]);
                    System.out.println("Respuesta:");
                    respuesta = entrada.nextLine();

                    try {
                        sem.acquire();
                        FileWriter escribir = new FileWriter(rutaRespuestas, true);
                        escribir.append(categoria + ";" + numero + ";" + respuesta + "\n");
                        escribir.flush();
                        escribir.close();
                        sem.release();
                    } catch (InterruptedException ex) {
                        System.out.println("Error -->" + ex.getMessage());
                    }
                }
            }
            bufferPreguntas.close();
            archivoPreguntas.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error -->" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error -->" + ex.getMessage());
        }
    }

    /**
     * Funcion que sirve para verificar si el archivo del reporte existe o no.
     *
     * @return true, de lo contrario false
     */
    public boolean verificarArchivo() {
        File archivo = new File(rutaReporte);
        if (!archivo.exists()) {
            return false;
        }
        return true;
    }

    /**
     * Funcion la cual su utilidad es crear el archivo de reporte si en dado
     * caso no existiera esto se comprueba con la funcion verificarArchivo
     */
    public void crearArchivo() {
        File archivo = new File(rutaReporte);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException ex) {
                System.out.println("Error ->>" + ex.getMessage());
            }
        }

    }
}
