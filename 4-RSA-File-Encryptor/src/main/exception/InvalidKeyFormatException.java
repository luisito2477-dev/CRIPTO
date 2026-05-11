/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.exception;

/**
 *
 * @author luish
 */
public class InvalidKeyFormatException extends CryptoException {
    public InvalidKeyFormatException() {
        super("El formato del archivo .pem no es valido");
    }
}
