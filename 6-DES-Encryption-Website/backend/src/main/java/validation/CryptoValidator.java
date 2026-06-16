/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validation;

/**
 *
 * @author luish
 */

import exception.CryptoException;
import java.nio.charset.StandardCharsets;

public class CryptoValidator {

    public static void validateRequest(String key, String mode, String initVector) throws CryptoException {
        // 1. Validar la Llave
        if (key == null) {
            throw new CryptoException("The key cannot be empty");
        }
        
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length != 8) {
            throw new CryptoException("The DES key must be 8 bytes length. Your key is " + keyBytes.length + " bytes).");
        }

        // 2. Validar el Modo de Operación
        if (mode == null || mode.trim().isEmpty()) {
            throw new CryptoException("Operation Mode is necessary");
        }
        
        String modeUpper = mode.toUpperCase();
        if (!modeUpper.equals("ECB") && !modeUpper.equals("CBC") && 
            !modeUpper.equals("CFB") && !modeUpper.equals("OFB") && !modeUpper.equals("CTR")) {
            throw new CryptoException("Operation mode not supported: " + mode);
        }

        // 3. Validar el Vector de Inicialización (IV) si NO es ECB
        if (!modeUpper.equals("ECB")) {
            if (initVector == null) {
                throw new CryptoException("The Initializing Vector is not necessary for " + modeUpper + ".");
            }
            
            byte[] ivBytes = initVector.getBytes(StandardCharsets.UTF_8);
            if (ivBytes.length != 8) {
                throw new CryptoException("the Initializing Vector (IV) must be 8 bytes (your IV is " + ivBytes.length + " bytes).");
            }
        }
    }
}
