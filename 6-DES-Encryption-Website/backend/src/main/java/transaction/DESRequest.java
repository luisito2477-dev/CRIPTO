/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transaction;

/**
 *
 * @author luish
 */
public class DESRequest {
    private String key;
    private String iV; //initializing Vector
    private String bmpFileBase64; //base64
    private String mode;
    private String action;
    private String fileName;
    
    public DESRequest(){
        
    }

    public DESRequest(String key, String vectorInitialize, String bmpFile, String mode, String action, String fileName) {
        this.key = key;
        this.iV = vectorInitialize;
        this.bmpFileBase64 = bmpFile;
        this.mode = mode;
        this.action = action;
        this.fileName = fileName;
    }

    public String getKey() {
        return key;
    }

    public String getIV() {
        return iV;
    }

    public String getBmpFileBase64() {
        return bmpFileBase64;
    }

    public String getMode() {
        return mode;
    }

    public String getAction() {
        return action;
    }

    public String getFileName() {
        return fileName;
    }
    
    
}
