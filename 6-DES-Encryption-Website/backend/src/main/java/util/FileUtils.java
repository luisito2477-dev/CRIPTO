/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author luish
 */
public class FileUtils {
    
    
    public static String createNewFileName(String fileName, String mode, String action) {
        
        
        int indexDot = fileName.lastIndexOf(".");
        String nombreSinExt = fileName.substring(0, indexDot);
        String suffix = "_" + action.substring(0, 1).toLowerCase() + mode.toUpperCase();
        String extension = fileName.substring(indexDot); 
        
        return nombreSinExt + suffix + extension;

        
    }
    
}
