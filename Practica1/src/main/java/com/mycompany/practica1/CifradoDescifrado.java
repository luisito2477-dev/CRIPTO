/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author luish
 */
public class CifradoDescifrado {
    //HIDE
    public static ArrayList<String> obtenerContrasenas(String nombreArchivo){
        File archivo = new File(nombreArchivo); 
        ArrayList<String> listaContrasenas = new ArrayList<String>();
        try(Scanner lector = new Scanner(archivo)){
            while(lector.hasNextLine()){
                
                String contrasena = lector.nextLine() + "\n";
                listaContrasenas.add(contrasena);
                
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: No se encontró el archivo .txt");
        }
        
        return listaContrasenas;
        
    }
    
    //HIDE
    public static int obtenerBitFinal(int caracter, int j){
        int mascara = 128;
        int bitPosicion = caracter & (mascara >> j);
        int bitFinal = bitPosicion >> (7 - j);
        
        return bitFinal;
    }
    
    //HIDE
    public static int obtenerBitColorFinal(int color, int bit){
        int mascara = 254;
        
        // Limpiamos el colorsito con una mascarilla
        int color_limp = color & mascara;
        
        // Hacemos ORsito con el bit
        int colorFinal = color_limp | bit;
        
        return colorFinal;
          
    }
    
    
    //SHOW
    public static int obtenerBitInicial(int color, int i){
        
        int mascara = 1; //00000001
        
        int miBit = color & mascara;
                                
        int bitRecorrido = miBit << (7 - i);
                    
        return bitRecorrido; 
        
    }
    
    //SHOW 
    public static void escribirArchivo(String archivo, StringBuilder texto){
       try(PrintWriter escritor = new PrintWriter(new FileWriter(archivo))){
           
           escritor.print(texto);
           
           System.out.println("Archivo de texto creado exitosamente. ");
       } catch (IOException e) {
            System.out.println("Error al escribir el archivo de texto: " + e.getMessage());
        }
    }
       
    public static void crearImagen(String archivoBMP, String archivoTexto, String nuevoArchivoBMP){
        
        //Obtener Contrasenas del archivo
        ArrayList<String> contrasenas = obtenerContrasenas(archivoTexto);
        
        if (contrasenas.isEmpty()){
            System.out.println("No hay contrasenas en el texto");
            return;
        }
        //Caracter bandera
        contrasenas.add("\0");
                
        try(FileInputStream fis = new FileInputStream(archivoBMP);
            FileOutputStream fos = new FileOutputStream(nuevoArchivoBMP)){
                
                byte[] header = new byte[54];
                
                fis.read(header);
                
                
                fos.write(header);
                
                // Obteniendo Contrasenas
                buclePrincipal: for(String contrasena: contrasenas){
                    //Obteniendo caracteres de cada contrasena
                    for(int i = 0; i < contrasena.length(); i++){

                        int caracter = (int) contrasena.charAt(i);
          
                        //Obteniendo bits de cada caracter
                        for(int j = 0; j < 8; j++){

                                // Obteniendo cada bit de la caracter
                                int bitCaracter = obtenerBitFinal(caracter, j);
                                
                                // Obtenemos el binario del RGB
                                int color = fis.read();
                                
                                if(color == -1){
                                    System.out.println("Error: No hay suficiente espacio para guardar las contrasenas en la imagen");
                                    break buclePrincipal;
                                }
                                
                                // Insertamos el bit del caracter en el binario del color
                                int colorModificado = obtenerBitColorFinal(color, bitCaracter); 
                                
                                // Insertamos el nuevo color en el nuevo.bmp
                                fos.write(colorModificado);
                        }
                    }            
                }  
                
                //Copiamos el resto del .bmp
                int byteRestante;
                while ((byteRestante = fis.read()) != -1) {
                    fos.write(byteRestante);
                }
                
                System.out.println("Nueva Imagen .bmp creada. ");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
} 
    
    public static void crearArchivoTexto(String archivoBMP, String archivoTexto){
                  
        try(FileInputStream fis = new FileInputStream(archivoBMP)){
                
                //Skipeamos header
                fis.skip(54);
                
                StringBuilder textoContrasena = new StringBuilder();
                
                boolean banderaEncontrada = false;
                buclePrincipal: while(!banderaEncontrada){
                      
                    int caracterASCII = 0;
                    for(int i = 0; i < 8; i++){
                        //obtener bit color
                        int color = fis.read();
                        
                        //Si se llego al final del archivo .bmp, rompemos el bucle
                        if(color == -1){
                            System.out.println("No se encontraron contrasenas");
                            textoContrasena.delete(0, textoContrasena.length());
                            break buclePrincipal;
                        }
                                
                        int bitInicial = obtenerBitInicial(color, i);
                    
                        //acumular 8 bits
                        caracterASCII = caracterASCII | bitInicial;    
                    }
                    
                    char caracterFormado = (char) caracterASCII;
                    
                    if(caracterFormado == '\0'){
                        banderaEncontrada = true;
                    } else{
                       textoContrasena.append(caracterFormado); 
                    }
                                                  
                }
                
                if(textoContrasena.length() == 0){
                    System.out.println("No se encontro ninguna contrasena en la imagen");
                } else{
                    // Creamos archivo
                    escribirArchivo(archivoTexto, textoContrasena); 
                }
                           
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }       
    }
    
}
