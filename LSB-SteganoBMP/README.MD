# LSB-Steganography-BMP 🖼️🔐

### *Ocultamiento de información en imágenes mediante Bit Menos Significativo (LSB)*

Este proyecto es una herramienta de **Esteganografía**, desarrollada en Java, que permite ocultar mensajes de texto dentro de imágenes en formato `.bmp`. A diferencia de la criptografía tradicional que solo cifra el mensaje, la esteganografía oculta la existencia misma del mensaje al integrarlo en los datos de color de una imagen.

---

## 📝 Descripción

El software utiliza la técnica de **Bit Menos Significativo (LSB)**. Esta técnica aprovecha que los cambios en el bit de menor peso de un byte de color (R, G o B) son imperceptibles para el ojo humano. El programa descompone cada carácter del texto en bits y los reparte a lo largo de los píxeles de la imagen.

## 🚀 Características

* **Manipulación de Bits:** Uso de operaciones a nivel de bit (`AND`, `OR`, desplazamientos) para una inserción precisa.
* **Integridad del Formato:** Respeta el encabezado de 54 bytes del estándar BMP para evitar la corrupción del archivo.
* **Detección de Final:** Emplea un carácter centinela (`\0`) para que el extractor sepa exactamente dónde termina el mensaje oculto.
* **Interfaz Gráfica (GUI):** Ventana intuitiva construida con Java Swing que facilita la selección de archivos y la ejecución de procesos.

## 🛠️ Requisitos

* **Lenguaje:** Java 8 o superior.
* **Entorno:** NetBeans IDE, VS Code con extensiones de Java o IntelliJ IDEA.
* **Librerías:** `javax.swing`, `java.io`, `java.util`.

## 📖 Funcionamiento Técnico

### 1. Inserción (Modo Hide)
1. Se lee el archivo `.txt` y se convierte cada carácter a su valor ASCII.
2. Cada valor ASCII se descompone en **8 bits**.
3. Se lee la imagen `.bmp` byte por byte (después del encabezado).
4. El bit menos significativo de cada byte de imagen se reemplaza por un bit del mensaje.
5. Se genera un nuevo archivo `.bmp` con la información oculta.



### 2. Extracción (Modo Show)
1. El programa recorre los bytes de la imagen procesada.
2. Extrae el último bit de cada byte y los agrupa de 8 en 8.
3. Reconstruye el carácter original a partir de esos 8 bits.
4. El proceso continúa hasta que se detecta el carácter nulo (`\0`), indicando el final del mensaje.

## 🖥️ Guía de Uso

1. **Ejecutar el programa:** Corre la clase `Practica1.java`.
2. **Para Ocultar:**
   - Selecciona el modo **Hide**.
   - Elige una imagen base `.bmp`.
   - Elige el archivo `.txt` con tus contraseñas o mensaje.
   - Presiona "Ejecutar Cifrado". El resultado se guardará en tu carpeta de Documentos.
3. **Para Extraer:**
   - Selecciona el modo **Show**.
   - Elige la imagen modificada.
   - Presiona "Ejecutar Descifrado". Se generará un archivo de texto con el mensaje recuperado.

---

## 🏗️ Estructura del Proyecto

* `CifradoDescifrado.java`: Contiene la lógica matemática de manipulación de bits y flujo de archivos.
* `InterfazGrafica.java`: Define la ventana de usuario y gestiona los eventos de los botones.
* `Main.java`: Clase principal que inicializa la interfaz en el hilo de eventos de Swing.

---
> **Desarrollado por:** luisito2477-dev 
> **Institución:** Escuela Superior de Cómputo (ESCOM) - IPN  
> **Materia:** Introducción a la Criptografía
