/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author luish
 */
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;


import exception.*;
public class CryptoService {
    
    private final Map<String, String> operationModes;
    
    public CryptoService(){
        this.operationModes = new HashMap<>();
        //anadimos los modos de operacion
        
        operationModes.put("ECB", "DES/ECB/PKCS5Padding");
        operationModes.put("CBC", "DES/CBC/PKCS5Padding");
        operationModes.put("CFB", "DES/CFB/PKCS5Padding");
        operationModes.put("OFB", "DES/OFB/PKCS5Padding");
        operationModes.put("CTR", "DES/CTR/NoPadding");
        
    }
    
    
    public byte[] DESOperation(byte[] bmpBytes, String mode, String action, String key, String initVector) throws CryptoException{
        try{
            // Obtener llave de 8 bytes
            byte[] keyBytes = key.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "DES");

            // Instanciar el modo (ejemplo: CBC)
            Cipher cipher = Cipher.getInstance(operationModes.get(mode));

            
            // Inicializar cipher
            if(!mode.equalsIgnoreCase("ECB")){
                
                // El IV (Vector de inicialización), obligatorio para CBC, CFB, OFB y CTR
                byte[] ivBytes = initVector.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                
                if(action.equalsIgnoreCase("ENCRYPT")){
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
                }else{
                    cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
                }          
            } else{

            
                if(action.equalsIgnoreCase("ENCRYPT")){
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                }else{
                    cipher.init(Cipher.DECRYPT_MODE, secretKey);
                    
                }
            }
            

            // A procesar
            byte[] resultado = cipher.doFinal(bmpBytes);
            
            return resultado;
        } catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException e){
            throw new CryptoException("There has been an error processing the file", e);
        } catch(InvalidKeyException e){
            throw new InvalidKeyFormatException();
        } catch(BadPaddingException e) {
            throw new CryptoException("Secret key is incorrect or data is corrupted. ", e);
        } catch(IllegalBlockSizeException e){
            throw new InvalidBMPException();
        }
        
    }
    
}
