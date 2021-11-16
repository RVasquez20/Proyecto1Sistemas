/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.moduloevaluacionestudiante;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ghostman
 */
public class Principal {

    public static void main(String[] args) {
        Semaphore sem = new Semaphore(1);
        
        hilo h1=new hilo(sem);
        h1.start();
        Evaluacion formulario=new Evaluacion(sem);
        if(!formulario.verificarArchivo()){
            formulario.crearArchivo();
        }
        formulario.menuPrincipal();
        
        
        try {
            h1.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
