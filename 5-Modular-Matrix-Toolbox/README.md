# Modular-Matrix-Toolbox 🧮🔢

### *Operaciones Matriciales Modulares y Algoritmos de Euclides*

Este proyecto es una herramienta matemática diseñada para realizar operaciones de álgebra lineal dentro de un cuerpo finito (Aritmética Modular). Es capaz de calcular determinantes, adjuntas e inversas de matrices de 2x2 y 3x3, validando la existencia de la inversa mediante el Máximo Común Divisor (MCD).

---

## 📝 Descripción

La aplicación resuelve problemas de inversión de matrices bajo un módulo $n$. Para que una matriz tenga inversa en un módulo determinado, su determinante debe ser coprimo con $n$, es decir, $mcd(det, n) = 1$. Este software automatiza esa verificación y el proceso de cálculo posterior.

## 🚀 Características Técnicas

*   **Algoritmo de Euclides Extendido:** Implementado para encontrar el inverso multiplicativo modular del determinante.
*   **Soporte Dimensional:** Maneja matrices cuadradas de **2x2** y **3x3**.
*   **Operaciones Modulares:**
    *   Suma y Multiplicación de matrices.
    *   Cálculo de la Matriz Adjunta (Transpuesta de la matriz de cofactores).
    *   Multiplicación por un escalar modular.
*   **Validación de Inversa:** Implementa la comprobación $A \cdot A^{-1} \equiv I \pmod n$.
*   **Interfaz Dinámica:** La GUI de Java Swing adapta los campos de texto según la dimensión seleccionada (2x2 o 3x3).

## 🛠️ Tecnologías Utilizadas

*   **Java SE:** Lógica central basada en arreglos bidimensionales (`int[][]`).
*   **Java Swing:** Interfaz de usuario con validación de entrada en tiempo real.
*   **Aritmética Modular:** Implementación de residuos no negativos para todos los cálculos.

---

## 📖 Conceptos Matemáticos Aplicados

### 1. Inverso Modular
Para hallar la matriz inversa $A^{-1}$ en módulo $n$, se utiliza la fórmula:
$$A^{-1} = (det(A))^{-1} \cdot Adj(A) \pmod n$$
Donde $(det(A))^{-1}$ es el inverso modular del determinante calculado con el Algoritmo de Euclides Extendido.

### 2. Algoritmo de Euclides Extendido
No basta con saber que el MCD es 1; necesitamos encontrar los coeficientes $x$ e $y$ tales que:
$$a \cdot x + n \cdot y = mcd(a, n)$$
El valor de $x$ representa el inverso modular que buscamos.

### 3. Matriz Adjunta
En el caso de 3x3, el sistema calcula los cofactores mediante submatrices de 2x2, aplica el signo $(-1)^{i+j}$ y transpone el resultado para obtener la adjunta correcta.

---

## 🖥️ Guía de Operación

1.  **Selección de Dimensión:** Escoge entre el modo 2x2 o 3x3 (los campos innecesarios se deshabilitarán automáticamente).
2.  **Configuración del Módulo:** Ingresa el valor de **n** (por ejemplo, 26 para el alfabeto inglés o un número primo para mayor estabilidad).
3.  **Entrada de Datos:** Llena las matrices A y/o B.
4.  **Acciones:**
    *   **Matriz Inversa:** Calcula $A^{-1}$ o $B^{-1}$ (si el determinante lo permite).
    *   **A * B / B * A:** Realiza el producto punto modular. El orden importa ya que la multiplicación de matrices no es conmutativa.

## 📂 Estructura del Proyecto

*   **`MatrixOperations.java`:** La clase principal de lógica matemática (puros métodos estáticos).
*   **`InterfazGrafica.java`:** Maneja la captura de datos, el formateo de la matriz a `String` para visualización y el control de los `JTextField`.
*   **`Main.java`:** Punto de entrada que lanza la interfaz.

---
> **Equipo de Desarrollo:** luisito2477-dev
> **Materia**: Introduccion a la Criptografia 
