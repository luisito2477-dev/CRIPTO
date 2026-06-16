/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import service.CryptoService;
import transaction.*;
import com.google.gson.Gson;
import exception.CryptoException;
import java.util.Map;
import util.BMPUtils;
import util.FileUtils;
import validation.CryptoValidator;
/**
 *
 * @author luish
 */
public class CryptoController {
    
    private final CryptoService cryptoService;
    
    public CryptoController(){
        this.cryptoService = new CryptoService();
    }
    
    public DESResponse encrypt_decrypt(DESRequest desRequest) throws CryptoException{
        
        CryptoValidator.validateRequest(desRequest.getKey(), desRequest.getMode(), desRequest.getIV());
        
        //obtenemos contenido archivo bmp
        //Pasamos de base64 a byte
        byte[] bmpFile = BMPUtils.base64ToByte(desRequest.getBmpFileBase64());
        
        //separamos header de contenido
        Map<String, byte[]> bmpBytesMap = BMPUtils.splitBMPBytes(bmpFile);
        
        //ciframos/desciframos contenido
        byte[] transformedBytes = cryptoService.DESOperation(
                bmpBytesMap.get("contentBytes"), 
                desRequest.getMode(), 
                desRequest.getAction(),
                desRequest.getKey(),
                desRequest.getIV()
        );
        
        //juntamos header y contenido
        byte[] transformedFile = BMPUtils.mergeBMPBytes(transformedBytes, bmpBytesMap.get("header"));
        
        //obtenemos nombre del nuevo archivo .bmp
        String newFileName = FileUtils.createNewFileName(
                desRequest.getFileName(), 
                desRequest.getMode(), 
                desRequest.getAction()
        );
        
        // convertimos bmp a base64
        String fileBase64 = BMPUtils.byteToBase64(transformedFile);
                
        //Formamos Response
        return new DESResponse(fileBase64, newFileName);

    }


}
