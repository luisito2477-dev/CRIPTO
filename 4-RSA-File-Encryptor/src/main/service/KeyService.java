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
import main.exception.InvalidKeyFormatException;
import main.util.PemUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyService {

    //FUNCION PARA GENERAR EL PAR DE LLAVES (PUBLICA Y PRIVADA)
    public KeyPair generateKeyPair() throws CryptoException {
        try {
            
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048); //Las llaves seran de 2048 bits
            return keyGen.generateKeyPair();
        } catch (Exception e) {
            throw new CryptoException("Error generando las llaves RSA", e);
        }
    }

    //ARCHIVO PARA GENERAR Y GUARDAR LOS ARCHIVOS .PEM
    public void generateAndSavePemFiles() throws CryptoException {
        try {
            KeyPair pair = generateKeyPair();

            String publicKeyPem = PemUtils.toPem("PUBLIC KEY", pair.getPublic().getEncoded());
            String privateKeyPem = PemUtils.toPem("PRIVATE KEY", pair.getPrivate().getEncoded());
            
            //Las llaves/archivos.pem se guardaran en la carpeta keys
            Path dir = Paths.get("keys");
            Files.createDirectories(dir);

            PemUtils.writeTextFile(dir.resolve("public_key.pem"), publicKeyPem);
            PemUtils.writeTextFile(dir.resolve("private_key.pem"), privateKeyPem);

        } catch (Exception e) {
            throw new CryptoException("Error creando los archivos .pem.", e);
        }
    }

    //FUNCION PARA OBTENER Y CARGAR LA LLAVE PUBLICA DEL ARCHIVO .PEM
    public PublicKey loadPublicKeyFromPemFile(String pemContent) throws CryptoException {
        try {
            //Obtenemos la llave publica del texto del archivo PEM (aun esta en Base64)
            String clean = PemUtils.cleanPem(pemContent);
            
            //La decodificamos para convertirlo a bytes
            byte[] keyBytes = Base64.getDecoder().decode(clean);
            
            //formateamos los bytes a X509 (formato estandar de llaves publicas)
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePublic(spec);
        } catch (IllegalArgumentException e) {
            throw new InvalidKeyFormatException();
        } catch (Exception e) {
            throw new CryptoException("Error cargando la llave publica.", e);
        }
    }

    //FUNCION PARA OBTENER Y CARGAR LA LLAVE PRIVADA DEL ARCHIVO .PEM
    public PrivateKey loadPrivateKeyFromPemFile(String pemContent) throws CryptoException {
        try {
            //Obtenemos la llave privada del contenido del archivo .pem (aun esta en Base64)
            String clean = PemUtils.cleanPem(pemContent);
            
            //Lo decodificamos para obtener los bytes
            byte[] keyBytes = Base64.getDecoder().decode(clean);
            
            //Los bytes los pasamos a formato PKCS8 (estandar de llaves privadas)
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            //retornamos la llave privada
            return keyFactory.generatePrivate(spec);
        } catch (IllegalArgumentException e) {
            throw new InvalidKeyFormatException();
        } catch (Exception e) {
            throw new CryptoException("Error cargando la llave privada", e);
        }
    }
}
