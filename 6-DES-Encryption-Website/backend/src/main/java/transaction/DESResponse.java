/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transaction;

/**
 *
 * @author luish
 */
public class DESResponse {
    private String newBmpFile;
    private String newFileName;
    
    public DESResponse(){
        
    }
    
    public DESResponse(String newBmpFile, String newFileName) {
        this.newBmpFile = newBmpFile;
        this.newFileName = newFileName;
    }

    public String getNewBmpFile() {
        return newBmpFile;
    }

    public String getNewFileName() {
        return newFileName;
    }
    
}
