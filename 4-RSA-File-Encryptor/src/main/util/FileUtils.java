/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author luish
 */
public class FileUtils {
    //GENERA EL NOMBRE DE SALIDA DEL ARCHIVO CIFRADO/DESCIFRADO
    public static Path createOutputPath(String rutaOriginal, String sufijo) {
        java.io.File archivo = new java.io.File(rutaOriginal);
        String directorio = archivo.getParent(); // Path absoluto de la carpeta donde esta el archivo
        String nombreCompleto = archivo.getName(); // Nombre con extensión (pato.bmp)

        // Separamos el nombre de la extensión
        int puntoIndex = nombreCompleto.lastIndexOf(".");
        if (puntoIndex == -1) {
            return Paths.get(rutaOriginal + sufijo); // Por si no tiene extension
        }

        String nombreSinExt = nombreCompleto.substring(0, puntoIndex);
        String extension = nombreCompleto.substring(puntoIndex); 

        //     [DIRECTORIO] +            /         +      nombre   + [SUFIJO] +   .txt
        return Paths.get(directorio + java.io.File.separator + nombreSinExt + sufijo + extension);
    }
    
}
