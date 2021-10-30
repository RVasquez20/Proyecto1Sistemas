/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.moduloevaluacionestudiante;

import static gt.edu.umg.moduloevaluacionestudiante.Principal.entrada;
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

    public hilo(Semaphore sem) {
        this.sem = sem;
    }

    @Override
    public void run() {
        while (true) {
            String texto = "",pregunta="";
            try {
                sem.acquire();
                //html,preguntas
                FileReader f = new FileReader("/home/ghostman/Escritorio/ProyectoiFinal/respuestas.csv");

                FileWriter escribir = new FileWriter("/home/ghostman/Escritorio/ProyectoiFinal/Reporte.html");
                escribir.write("<!DOCTYPE html>\n"
                        + "<html lang=\"en\">\n"
                        + "<head>\n"
                        + "  <title>Reporte</title>\n"
                        + "  <meta charset=\"utf-8\">\n"
                        + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                        + "  <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n"
                        + "  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>\n"
                        + "  <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "<div class=\"container\">\n"
                        + "<table class=\"table table-bordered\">\n"
                        + "    <thead>\n"
                        + "\n"
                        + "      <tr class=\"info\">\n"
                        + "        <td>Categoria</td>\n"
                        + "        <td>Pregunta</td>\n"
                        + "        <td>Respuesta</td>\n"
                        + "      </tr>\n"
                        + "      </thead>\n"
                        + "       <tbody>\n");

                BufferedReader b = new BufferedReader(f);
                while ((texto = b.readLine()) != null) {
                    String[] result = texto.split(";");
                    ///System.out.println(result[0]);
                    escribir.write("<tr><td>"+result[0]+"</td>");
                    
                    //busca en preguntas y retorna la respuesta
                    int numero=0;
                    FileReader fr = new FileReader("/home/ghostman/Escritorio/ProyectoiFinal/Preguntas.csv");
                    BufferedReader br = new BufferedReader(fr);
                     while ((pregunta = br.readLine()) != null) {
                         numero++;
                         if(numero==Integer.parseInt(result[1])){
                    String[] resultpregunta = pregunta.split(";");
                     escribir.write("<td>"+resultpregunta[1]+"</td>");
                     ///System.out.println(resultpregunta[1]);
                         }
                    
                     }
                     escribir.write("<td>"+result[2]+"</td></tr>");
                     //System.out.println(result[2]);
                    
                     fr.close();

                }
                escribir.write("</tbody>\n" +
"  </table>\n" +
"  </div>\n" +
"  </body>\n" +
"  </html>");
                escribir.flush();
                escribir.close();

               
                f.close();
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
