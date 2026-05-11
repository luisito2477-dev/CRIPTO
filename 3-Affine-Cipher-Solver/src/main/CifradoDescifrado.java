/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author luish
 */
public class CifradoDescifrado {
    
    //Algoritmo de Euclides
    private static int mcd(int a, int b) {
    if (b == 0) {
        return a;
    }         
    return mcd(b, a % b); 
}
    
    private static boolean sonCoprimos(int a, int b){
        return mcd(a, b) == 1;
        
    }
    
    //Algoritmo de Euclides Extendido
    private static int[] euclidesExtendido(int a, int b) {
        if (b == 0) {
            return new int[] { a, 1, 0 }; // {MCD, x, y}
        }
        int[] valores = euclidesExtendido(b, a % b);
        int mcd = valores[0];
        int x1 = valores[1];
        int y1 = valores[2];

        int x = y1;
        int y = x1 - (a / b) * y1;

        return new int[] { mcd, x, y };
    }
    
    private static int obtenerInversoAditivo(int a, int n){
        a = a % n;

        if (a == 0) {
            return 0;
        } else {
            return n - a;
        }
    
    }
    
    public static String obtenerFunciones(int alpha, int beta, int n){
        if(!sonCoprimos(alpha, n)){
            return "Error: Los numeros alpha y n no son coprimos";
            
        }
        
        StringBuilder sb = new StringBuilder();
        
        //Funcion de cifrado
        sb.append("Funcion de cifrado: \n");
        sb.append(String.format("Ek: C = (%d*p + %d) mod %d. \n", alpha, beta, n));
        
        //Funcion de descifrado
        
        int alpha_inv_mult = euclidesExtendido(alpha, n)[1];
        
        //Nos aseguramos que alpha sea positivo
        if (alpha_inv_mult < 0) {
            alpha_inv_mult += n;
        }
        int beta_inv_adit = obtenerInversoAditivo(beta, n);
        int beta_final = (alpha_inv_mult * beta_inv_adit) % n;
        
        //Nos aseguramos que beta no sea negativo
        if (beta_final < 0) {
            beta_final += n;
        }
        
        sb.append("Funciones de Descifrado: \n");
        sb.append(String.format("Dk: p = (%d^-1)*[C + (-%d)] mod %d. \n", alpha, beta, n));
        sb.append(String.format("Dk: p = (%d*C + %d) mod %d. \n", alpha_inv_mult, beta_final, n));
    
        return sb.toString();
    }

    
}
