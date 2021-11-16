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
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ghostman
 */
public class hilo extends Thread {

    public Semaphore sem;
    public final String rutaPreguntas = "/usr/src/Archivos/Preguntas.csv";
    public final String rutaRespuestas = "/usr/src/Archivos/respuestas.csv";
    public final String rutaReporte = "/usr/local/apache2/htdocs/Proyecto/Respuestas.html";

    public hilo(Semaphore sem) {
        this.sem = sem;
    }

    /**
     * Este metodo run permite desde que inicia el programa leer el archivo de
     * respuestas primero verifica el estado del semaforo, si es posible pasa, y
     * lo coloca en espera y generar un reporte html con los datos del mismo. Ya
     * que el archivo de respuestas cuenta con el numero de linea en el cual
     * esta la pregunta este metodo tambien lee el archivo de preguntas para
     * poder identificar la correcta segun el numero de linea, al terminar
     * libera el semaforo y espera 5 segundos para ejecutarse nuevamente.
     */
    @Override
    public void run() {
        while (true) {
            String texto = "", pregunta = "";
            try {
                sem.acquire();
                FileReader lecturaRespuestas = new FileReader(rutaRespuestas);

                FileWriter escribir = new FileWriter(rutaReporte);
                escribir.write("<!DOCTYPE html>\n"
                        + "<html lang=\"en\">\n"
                        + "<head>\n"
                        + "  <title>Reporte de Respuestas</title>\n"
                        + "  <title>Reporte</title>\n"
                        + "  <meta charset=\"utf-8\">\n"
                        + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                        + "  <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">\n"
                        + "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM\" crossorigin=\"anonymous\"></script>\n"
                        + "<script src=\"https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js\" integrity=\"sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p\" crossorigin=\"anonymous\"></script>\n"
                        + "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js\" integrity=\"sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF\" crossorigin=\"anonymous\"></script>"
                        + "</head>\n"
                        + "<body>\n"
                        + "<div class=\"container-fluid p-5 bg-primary text-white text-center\">\n"
                        + "  <h1>Reporte de Respuestas</h1>\n"
                        + "</div>\n"
                        + "<br><br><br>\n"
                        + "<div class=\"container\">\n"
                        + "<table class=\"table table-dark table-hover\">\n"
                        + "    <thead>\n"
                        + "\n"
                        + "      <tr>\n"
                        + "        <td>Categoria</td>\n"
                        + "        <td>Pregunta</td>\n"
                        + "        <td>Respuesta</td>\n"
                        + "      </tr>\n"
                        + "      </thead>\n"
                        + "       <tbody>\n");

                BufferedReader bufferLectura = new BufferedReader(lecturaRespuestas);
                while ((texto = bufferLectura.readLine()) != null) {
                    String[] result = texto.split(";");
                    escribir.write("<tr><td>" + result[0] + "</td>");

                    int numero = 0;
                    FileReader lecturaPreguntas = new FileReader(rutaPreguntas);
                    BufferedReader bufferPreguntas = new BufferedReader(lecturaPreguntas);
                    while ((pregunta = bufferPreguntas.readLine()) != null) {
                        numero++;
                        if (numero == Integer.parseInt(result[1])) {
                            String[] resultpregunta = pregunta.split(";");
                            escribir.write("<td>" + resultpregunta[1] + "</td>");
                        }

                    }
                    escribir.write("<td>" + result[2] + "</td></tr>");

                    lecturaPreguntas.close();

                }
                escribir.write("</tbody>\n"
                        + "  </table>\n"
                        + "  </div>\n"
                        + "  </body>\n"
                        + "  </html>");
                escribir.flush();
                escribir.close();

                lecturaRespuestas.close();
                sem.release();
                sleep(5000);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(hilo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(hilo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(hilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
