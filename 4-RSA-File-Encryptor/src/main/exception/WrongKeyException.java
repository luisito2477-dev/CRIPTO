/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.exception;

/**
 *
 * @author luish
 */

public class WrongKeyException extends CryptoException {
    public WrongKeyException() {
        super("La llave es incorrecta o no coincide con el archivo");
    }
}
