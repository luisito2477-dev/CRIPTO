/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util;

/**
 *
 * @author luish
 */
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class PemUtils {

    //FUNCION PARA GENERAR EL CONTENIDO DEL ARCHIVO PEM
    public static String toPem(String type, byte[] encodedKey) {
        String base64 = Base64.getEncoder().encodeToString(encodedKey);

        //Dividir en lineas de 64 caracteres (estandar de PEM)
        StringBuilder pem = new StringBuilder();
        pem.append("-----BEGIN ").append(type).append("-----\n");

        for (int i = 0; i < base64.length(); i += 64) {
            pem.append(base64, i, Math.min(i + 64, base64.length()));
            pem.append("\n");
        }

        pem.append("-----END ").append(type).append("-----");
        return pem.toString();
    }
    
    //FUNCION PARA LEER EL CONTENIDO DEL ARCHIVO
    public static String readTextFile(Path path) throws IOException {
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    //FUNCION PARA ESCRIBIR EN UN ARCHIVO
    public static void writeTextFile(Path path, String content) throws IOException {
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }

    
    //FUNCION PARA LIMPIAR Y OBTENER UNICAMENTE LA LLAVE DEL ARCHIVO PEM
    public static String cleanPem(String pem) {
        return pem
                .replaceAll("-----BEGIN [^-]+-----", "")
                .replaceAll("-----END [^-]+-----", "")
                .replaceAll("\\s", "");
    }
}