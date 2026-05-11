/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main.app;

import main.ui.InterfazGrafica;

/**
 *
 * @author luish
 */
public class App{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
        java.awt.EventQueue.invokeLater(() -> {
        InterfazGrafica ventana = new InterfazGrafica();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        });
    }
    
}
