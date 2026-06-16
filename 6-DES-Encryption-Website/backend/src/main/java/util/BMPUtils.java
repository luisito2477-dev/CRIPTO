package util;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author luish
 */

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
public class BMPUtils {
    
    public static Map<String, byte[]> splitBMPBytes(byte[] bmpFile){
        // Leer el offset real desde la posición 10 del archivo (Little Endian)
        int headerSize = ((bmpFile[13] & 0xFF) << 24) |
                     ((bmpFile[12] & 0xFF) << 16) |
                     ((bmpFile[11] & 0xFF) << 8)  |
                     (bmpFile[10] & 0xFF);

        System.out.println("El header real de este archivo mide: " + headerSize + " bytes.");
        
        byte[] header = Arrays.copyOfRange(bmpFile, 0, headerSize);
        byte[] contentBytes = Arrays.copyOfRange(bmpFile, headerSize, bmpFile.length);
        
        //Creamos HashMap con datos
        Map<String, byte[]> bmpBytesMap = new HashMap<>();
        bmpBytesMap.put("header", header);
        bmpBytesMap.put("contentBytes", contentBytes);
        
        return bmpBytesMap;
    }
    
    public static byte[] mergeBMPBytes(byte[] encryptedBytes, byte[] header){
        byte[] resultFile = new byte[header.length + encryptedBytes.length];
        System.arraycopy(header, 0, resultFile, 0, header.length);
        System.arraycopy(encryptedBytes, 0, resultFile, header.length, encryptedBytes.length);
    
        return resultFile;
    }
    
    
    public static byte[] base64ToByte(String encodedBase64){
        return java.util.Base64.getDecoder().decode(encodedBase64);
    }
    
    public static String byteToBase64(byte[] decodedBytes){
        return java.util.Base64.getEncoder().encodeToString(decodedBytes);
    }
    
}
