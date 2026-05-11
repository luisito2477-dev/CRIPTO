/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

/**
 *
 * @author luish
 */


import main.exception.CryptoException;
import main.service.CryptoService;
import main.service.KeyService;
import main.util.PemUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;

public class CryptoController {

    private final KeyService keyService;
    private final CryptoService cryptoService;

    public CryptoController() {
        this.keyService = new KeyService();
        this.cryptoService = new CryptoService();
    }

    public void generateKeys() throws CryptoException {
        keyService.generateAndSavePemFiles();
    }

    public Path encrypt(String inputFile, String pemFile) throws CryptoException {
        String pemContent = readPem(pemFile);
        PublicKey publicKey = keyService.loadPublicKeyFromPemFile(pemContent);
        return cryptoService.encryptFile(Paths.get(inputFile), publicKey);
    }

    public Path decrypt(String inputFile, String pemFile) throws CryptoException {
        String pemContent = readPem(pemFile);
        PrivateKey privateKey = keyService.loadPrivateKeyFromPemFile(pemContent);
        return cryptoService.decryptFile(Paths.get(inputFile), privateKey);
    }

    private String readPem(String pemFile) throws CryptoException {
        try {
            return PemUtils.readTextFile(Paths.get(pemFile));
        } catch (Exception e) {
            throw new CryptoException("Error leyendo el archivo .pem.", e);
        }
    }
}
