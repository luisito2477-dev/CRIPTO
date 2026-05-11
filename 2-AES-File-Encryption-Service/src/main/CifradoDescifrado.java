/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author luish
 */


//
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Arrays;
import java.lang.Exception;

/**
 *
 * @author luish
 */
public class CifradoDescifrado {
    
    private static SecretKeySpec crearLlave(String clave) {
        try {
            byte[] cadena = clave.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1"); 
            cadena = sha.digest(cadena);  //hasheo de la contrasena (tamano 20 bytes)
            cadena = Arrays.copyOf(cadena, 16); //Recortamos la contrasena ya que AES, solo acepta llaves de 16, 24 y 32 bytes
            return new SecretKeySpec(cadena, "AES");
        } catch (Exception e) {
            return null;
        }
    }
    
    private static void procesarArchivo(String rutaOriginal, String rutaDestino, String password, int modo) throws Exception {
        try {
            SecretKeySpec llave = crearLlave(password);
            Cipher cipher = Cipher.getInstance("AES");
            
            if (modo == 1) {
                cipher.init(Cipher.ENCRYPT_MODE, llave);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, llave);
            }

            
            byte[] inputBytes = Files.readAllBytes(Paths.get(rutaOriginal));
            byte[] outputBytes = cipher.doFinal(inputBytes);

            
            Files.write(Paths.get(rutaDestino), outputBytes);
            
        } catch (javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException e) {
            //Error de llave incorrecta
            throw new Exception("LLAVE_INCORRECTA");
        } catch (Exception e) {
            // Cualquier otro error 
            throw new Exception("ERROR_GENERAL: " + e.getMessage());
        }
    }
    
    private static String generarNombreSalida(String rutaOriginal, String sufijo) {
        java.io.File archivo = new java.io.File(rutaOriginal);
        String directorio = archivo.getParent(); // Path absoluto de la carpeta donde esta el archivo
        String nombreCompleto = archivo.getName(); // Nombre con extensión (pato.bmp)

        // Separamos el nombre de la extensión
        int puntoIndex = nombreCompleto.lastIndexOf(".");
        if (puntoIndex == -1) return rutaOriginal + sufijo; // Por si no tiene extension

        String nombreSinExt = nombreCompleto.substring(0, puntoIndex);
        String extension = nombreCompleto.substring(puntoIndex); 

        //     [DIRECTORIO] +            /         +      nombre   + [SUFIJO] +   .txt
        return directorio + java.io.File.separator + nombreSinExt + sufijo + extension;
    }
    
    public static void cifrarArchivo(String rutaOrigen, String password) throws Exception{
        
        String rutaDestino = generarNombreSalida(rutaOrigen, "_e");
        procesarArchivo(rutaOrigen, rutaDestino, password, 1);
    }
    
    
    public static void descifrarArchivo(String rutaOrigen, String password) throws Exception{
        //Logica para obtener ruta destino
        String rutaDestino = generarNombreSalida(rutaOrigen, "_d");
        procesarArchivo(rutaOrigen, rutaDestino, password, 2);
        
    }  
}
