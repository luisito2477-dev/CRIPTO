/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author luish
 */
public class InvalidKeyFormatException extends CryptoException {
    public InvalidKeyFormatException() {
        super("The key format is not valid.");
    }
    
    public InvalidKeyFormatException(String message){
        super(message);
    }
}
