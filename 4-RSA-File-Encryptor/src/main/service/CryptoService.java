/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

/**
 *
 * @author luish
 */

import main.exception.CryptoException;
import main.exception.WrongKeyException;
import main.util.FileUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;

public class CryptoService {

    public Path encryptFile(Path inputPath, PublicKey publicKey) throws CryptoException {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] inputBytes = Files.readAllBytes(inputPath);
            byte[] outputBytes = cipher.doFinal(inputBytes);

            Path outputPath = FileUtils.createOutputPath(inputPath.toString(), "_e");
            Files.write(outputPath, outputBytes);

            return outputPath;

        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new WrongKeyException();
        } catch (Exception e) {
            throw new CryptoException("Error cifrando archivo.", e);
        }
    }

    public Path decryptFile(Path inputPath, PrivateKey privateKey) throws CryptoException {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            
            byte[] inputBytes = Files.readAllBytes(inputPath);
            byte[] outputBytes = cipher.doFinal(inputBytes);

            Path outputPath = FileUtils.createOutputPath(inputPath.toString(), "_d");
            Files.write(outputPath, outputBytes);

            return outputPath;

        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new WrongKeyException();
        } catch (Exception e) {
            throw new CryptoException("Error descifrando archivo.", e);
        }
    }
}
