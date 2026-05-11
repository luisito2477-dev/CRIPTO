# RSA-File-Encryptor 🔑📁

### *Criptografía Asimétrica: RSA-2048 y Estándares PKCS#8 / X.509*

Este proyecto implementa un sistema de cifrado y descifrado de archivos utilizando el algoritmo **RSA**. A diferencia de los métodos simétricos, este software permite la generación de un par de llaves (Pública y Privada), permitiendo que la seguridad del mensaje no dependa de compartir una única clave secreta.

---

## 📝 Descripción

La aplicación utiliza la arquitectura **RSA (Rivest-Shamir-Adleman)** para proteger la integridad y confidencialidad de archivos de texto. El flujo de trabajo separa claramente las responsabilidades:
1.  **Generación de Llaves:** Crea un par asimétrico de 2048 bits.
2.  **Cifrado:** Utiliza la llave pública (`.pem`) para transformar el archivo.
3.  **Descifrado:** Utiliza la llave privada (`.pem`) correspondiente para recuperar la información original.

## 🚀 Características Técnicas

*   **Algoritmo RSA 2048 bits:** Proporciona un nivel de seguridad robusto contra ataques de fuerza bruta actuales.
*   **Gestión de Archivos PEM:** Soporte nativo para leer y escribir llaves en formato Base64 con encabezados estándar.
*   **Formatos Estándar:** 
    *   **X.509:** Para la exportación/importación de llaves públicas.
    *   **PKCS#8:** Para el manejo seguro de llaves privadas.
*   **Arquitectura Modular:** Separación en capas (Service, Controller, UI, Utils) para facilitar el mantenimiento.
*   **Manejo de Excepciones Personalizado:** Sistema de alertas para llaves incorrectas o archivos corruptos.

## 🛠️ Tecnologías Utilizadas

*   **Java Cryptography Architecture (JCA):** Uso de `KeyPairGenerator`, `Cipher` y `KeyFactory`.
*   **Java Swing:** Interfaz gráfica intuitiva con paneles dinámicos para cifrado/descifrado.
*   **Java NIO:** Gestión eficiente de rutas y archivos para lectura/escritura de bytes.

---

## 📖 Conceptos de Seguridad Implementados



### 1. Criptografía Asimétrica
El sistema garantiza que cualquier persona con la **Llave Pública** pueda cifrar un archivo, pero solo el poseedor de la **Llave Privada** puede descifrarlo.

### 2. Estándar PEM (Privacy Enhanced Mail)
Las llaves se almacenan en archivos `.pem`. El sistema procesa estas cadenas eliminando los encabezados `-----BEGIN...-----` y decodificando el contenido de Base64 a bytes binarios para que el motor de Java pueda reconstruir la llave matemática.

### 3. Relleno (Padding)
Se utiliza el esquema de relleno por defecto de RSA para manejar bloques de datos y fortalecer la seguridad contra ataques estadísticos.

---

## 🖥️ Guía de Operación

1.  **Generar Llaves:** Al iniciar, pulsa "Generar Llaves". Esto creará una carpeta `keys/` con `public_key.pem` y `private_key.pem`.
2.  **Modo Cifrado:**
    *   Selecciona el archivo `.txt` de origen.
    *   Carga la llave pública.
    *   Ejecuta. Se generará un archivo con sufijo `_e.txt`.
3.  **Modo Descifrado:**
    *   Selecciona el archivo cifrado (`_e.txt`).
    *   Carga la llave privada.
    *   Ejecuta. Se recuperará el contenido en un archivo con sufijo `_d.txt`.

## 📂 Estructura del Proyecto

*   **`main.service`:** Lógica de cifrado (`CryptoService`) y gestión de llaves (`KeyService`).
*   **`main.controller`:** Orquestador entre la interfaz y los servicios.
*   **`main.util`:** Herramientas de formateo PEM y manipulación de nombres de archivos.
*   **`main.exception`:** Definiciones de errores de seguridad específicos.

---
> **Equipo de Desarrollo:** luisito2477-dev 
> **Materia** Introduccion a la Criptografia
