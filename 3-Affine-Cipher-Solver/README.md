# Affine-Cipher-Solver 🧮🔐

### *Fundamentos Matemáticos: Cifrado Afín y Aritmética Modular*

Esta herramienta educativa en Java permite calcular las funciones matemáticas necesarias para implementar un **Cifrado Afín**. El sistema no solo genera la función de cifrado, sino que resuelve el reto matemático de encontrar la función de descifrado utilizando el **Algoritmo de Euclides Extendido** para hallar inversos multiplicativos modulares.

---

## 📝 Descripción

El cifrado afín es un tipo de cifrado por sustitución monoalfabética donde cada letra se transforma mediante la función:
> **C = (α * p + β) mod n**

Para que este cifrado sea reversible (descifrable), el coeficiente **α** debe ser coprimo con el tamaño del alfabeto **n**. Esta aplicación valida dichas condiciones matemáticas y calcula automáticamente la función inversa:
> **p = α⁻¹ * (C - β) mod n**

## 🚀 Características

* **Validación de Coprimo:** Implementa el Algoritmo de Euclides para asegurar que el Máximo Común Divisor (MCD) entre $\alpha$ y $n$ sea 1.
* **Euclides Extendido:** Calcula el inverso multiplicativo modular ($\alpha^{-1}$) necesario para el descifrado.
* **Inverso Aditivo:** Maneja correctamente el desplazamiento $\beta$ en el dominio modular.
* **Interfaz Swing:** Diseño intuitivo para ingresar los parámetros $\alpha$, $\beta$ y $n$ (módulo).
* **Salida Formateada:** Muestra paso a paso la construcción de la función de descifrado.

## 🛠️ Requisitos

* **Lenguaje:** Java 8 o superior.
* **IDE:** NetBeans (soporta archivos `.form` para la GUI), IntelliJ o Eclipse.
* **Conocimientos Requeridos:** Aritmética modular y teoría de números básica.

## 📖 Conceptos Matemáticos Aplicados

### 1. Algoritmo de Euclides
Se utiliza para verificar la condición de existencia del inverso. Si $mcd(\alpha, n) \neq 1$, el programa bloquea la operación para evitar cifrados no reversibles.

### 2. Algoritmo de Euclides Extendido
Es el núcleo del sistema. Permite encontrar los coeficientes $x$ e $y$ tales que:
> $\alpha \cdot x + n \cdot y = mcd(\alpha, n)$

Donde $x$ representa el inverso multiplicativo de $\alpha$ en el módulo $n$.

### 3. Normalización de Resultados
El software garantiza que todos los coeficientes resultantes de la función de descifrado sean positivos dentro del rango $[0, n-1]$, facilitando su aplicación directa en el alfabeto.

## 🖥️ Guía de Uso

1. **Definir Parámetros:**
   - **$\alpha$:** Coeficiente multiplicativo (debe ser coprimo con $n$).
   - **$\beta$:** Desplazamiento o constante aditiva.
   - **$n$:** Tamaño del alfabeto (ej. 26 para el alfabeto inglés, 256 para ASCII extendido).
2. **Calcular:** Presionar "Obtener Funciones".
3. **Resultado:** Se desplegará una ventana con la función de cifrado $E_k$ y la función de descifrado $D_k$ simplificada.

---

## 🏗️ Estructura del Código

* `CifradoDescifrado.java`: Contiene la lógica pesada de los algoritmos de Euclides, validación de coprimatilidad y cálculo de inversos.
* `InterfazGrafica.java`: Maneja la entrada del usuario, la validación de tipos de datos y la presentación de resultados.
* `Main.java`: Punto de entrada que inicializa la interfaz gráfica.

---
> **Equipo de Desarrollo:** luisito2477-dev
> **Materia:** Introducción a la Criptografía.
