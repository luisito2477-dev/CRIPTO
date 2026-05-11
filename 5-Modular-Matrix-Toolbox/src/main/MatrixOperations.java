/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author luish
 */
public class MatrixOperations {
    
    
    //Maximo Comun Divisor
    private static int mcd(int a, int b) {
        if (b == 0) {
            return a;
        }         
        return mcd(b, a % b); 
}
    
    //Algoritmo de euclidesExtendido
    public static int[] euclidesExtendido(int a, int b) {
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
    
    //determinante
    public static int calcDeterminante2x2(int v1, int v2, int v3, int v4){
        return (v1 * v2) - (v3 * v4);
    }
    
    public static int calcDeterminante3x3(int[][] matriz){
        return ((matriz[0][0] * 
                calcDeterminante2x2(matriz[1][1], matriz[2][2], matriz[2][1], matriz[1][2]))
                - (matriz[0][1] *
                calcDeterminante2x2(matriz[1][0], matriz[2][2], matriz[2][0], matriz[1][2]))
                + (matriz[0][2] *
                calcDeterminante2x2(matriz[1][0], matriz[2][1], matriz[2][0], matriz[1][1]))  
                );
        
    }
    
    public static int[][] adjunta2x2(int[][] m, int n) {
        int[][] adj = new int[2][2];
        adj[0][0] = (m[1][1] % n + n) % n;
        adj[1][1] = (m[0][0] % n + n) % n;
        adj[0][1] = (-m[0][1] % n + n) % n;
        adj[1][0] = (-m[1][0] % n + n) % n;
        return adj;
    }
    
    private static int obtenerCofactor(int[][] m, int fila, int col, int n) {
        int[][] submatriz = new int[2][2];
        int x = 0, y = 0;

        // Logica para saltar la fila y columna actual y armar la 2x2
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != fila && j != col) {
                    submatriz[x][y] = m[i][j];
                    y++;
                    if (y == 2) { x++; y = 0; }
                }
            }
        }

        // Determinante de la submatriz: (ad - bc)
        int det = (submatriz[0][0] * submatriz[1][1]) - (submatriz[0][1] * submatriz[1][0]);

        // Aplicar signo según posición (-1)^(i+j)
        if ((fila + col) % 2 != 0) det = -det;

        return (det % n + n) % n;
    }
    
    public static int[][] adjunta3x3(int[][] m, int n) {
        int[][] adj = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Calculamos el cofactor de (i, j) y lo guardamos transpuesto en (j, i)
                adj[j][i] = obtenerCofactor(m, i, j, n);
            }
        }
        return adj;
    }
    
    public static int[][] multEscalar(int[][] matriz, int escalar, int n){
        int filas = matriz.length;
        int columnas = matriz[1].length;
        int[][] matrizResultado = new int[filas][columnas];
        for(int i = 0; i < filas; i++){
            for(int j = 0; j < columnas; j++){
                matrizResultado[i][j] = ((matriz[i][j] * escalar) % n + n) % n;
            }
            
        }
        
        return matrizResultado;
    }
    
    //Funcion para obtener la matriz identidad
    public static int[][] identidad(int size) {
        int[][] I = new int[size][size];
        for (int i = 0; i < size; i++) {
            I[i][i] = 1;
        }
        return I;
    }
    
    //Funcion para comprobar que las matrices son iguales
    public static boolean sonIguales(int[][] A, int[][] B) {
        if (A.length != B.length || A[0].length != B[0].length) {
            return false;
        }

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                if (A[i][j] != B[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    //Funcion para verificar que la matriz inversa es igual que la matriz
    public static boolean verificarInversa(int[][] A, int[][] Ainv, int n) {
        int[][] resultado = multMatrices(A, Ainv, n);
        int[][] identidad = identidad(A.length);

        return sonIguales(resultado, identidad);
    }
    
    public static int[][] matrizInv2x2(int[][] matriz, int n){
        
        //Obtener determinante y le aplicamos modulo
        int det = (calcDeterminante2x2(matriz[0][0], matriz[1][1], matriz[1][0], matriz[0][1]) % n + n) % n;
        
        //verificamos que la matriz tenga inversa
        if (mcd(det, n) != 1) {
            throw new RuntimeException("La matriz no tiene inversa en módulo " + n);
        }
        
        //obtenemos determinante inverso
        int detInv = (euclidesExtendido(det, n)[1] % n + n) % n;
        
        
        //obtenemos matriz adjunta
        int [][] adj = adjunta2x2(matriz, n);
        
        //Aplicamos formula
        //A^-1 = det^-1 * Adj(A)
        int[][] matrizInv = multEscalar(adj, detInv, n);
        
        //Verificar Inversa con A * A^-1 = I (mod n)
        if (!verificarInversa(matriz, matrizInv, n)) {
            throw new RuntimeException("Error: la matriz inversa no es correcta.");
        }
        

        return matrizInv;
        
    }
    
    public static int[][] matrizInv3x3(int[][] matriz, int n){
        //Obtener determinante y le aplicamos modulo
        int det = (calcDeterminante3x3(matriz) % n + n) % n;
        
        //verificamos que la matriz tenga inversa
        if (mcd(det, n) != 1) {
            throw new RuntimeException("La matriz no tiene inversa en modulo " + n);
        }
        
        //obtenemos determinante inverso
        int detInv = (euclidesExtendido(det, n)[1] % n + n) % n;
        
        //obtenemos matriz adjunta
        int [][] adj = adjunta3x3(matriz, n);
        
        //Aplicamos formula
        //A^-1 = det^-1 * Adj(A)
        int[][] matrizInv = multEscalar(adj, detInv, n);
        
        //Verificar Inversa con A * A^-1 = I (mod n)
        if (!verificarInversa(matriz, matrizInv, n)) {
            throw new RuntimeException("Error: la matriz inversa no es correcta.");
        }
        
        
        return matrizInv;
        
    }
    
    //invMatriz
    public static int[][] calcMatrizInv(int[][] matriz, int n){
        
        int[][] matrizInv;
        
        if(matriz.length == 2){

            matrizInv = matrizInv2x2(matriz, n);
        } else {
            matrizInv = matrizInv3x3(matriz, n);
        }        
        return matrizInv;
    }
    
    
    
    //multMatriz
    public static int[][] multMatrices(int[][] A, int[][] B, int n) {
        // Obtener dimensiones
        int filasA = A.length;
        int columnasA = A[0].length;
        int filasB = B.length;
        int columnasB = B[0].length;

        // Validar si la multiplicación es posible
        if (columnasA != filasB) {
            throw new IllegalArgumentException("Dimensiones incompatibles: Columnas de A (" 
                + columnasA + ") deben coincidir con Filas de B (" + filasB + ").");
        }

        // La matriz resultante tendrá dimensiones: Filas de A x Columnas de B
        int[][] resultado = new int[filasA][columnasB];

        // Triple bucle anidado
        for (int i = 0; i < filasA; i++) { // Recorre filas de A
            for (int j = 0; j < columnasB; j++) { // Recorre columnas de B
                int suma = 0;
                for (int k = 0; k < columnasA; k++) { // Realiza el producto punto
                    suma += A[i][k] * B[k][j];
                }
                resultado[i][j] = (suma % n + n) % n;
            }
        }

        return resultado;
    }
    
    
    //Imprimir Matriz
    public static String matrizAString(int[][] matriz) {
        if (matriz == null || matriz.length == 0) return "Matriz vacía";

        StringBuilder sb = new StringBuilder();

        for (int[] fila : matriz) {
            sb.append("[ ");
            for (int valor : fila) {
                // %8.2f -> Reserva 8 espacios y muestra 2 decimales
                // Esto hace que las columnas se vean alineadas
                sb.append(String.format("%4d ", valor));
            }
            sb.append("]\n");
        }

        return sb.toString();
    }
    
}
