/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author luish
 */
public class InvalidBMPException extends CryptoException {
    
    public InvalidBMPException(){
        super("The .bmp file format is not valid.");
    }
    
    public InvalidBMPException(String message){
        super(message);
    }
}
