# AES-File-Encryption-Service 🔐📂

### *Protección de archivos mediante el Estándar de Cifrado Avanzado (AES)*

Esta aplicación, desarrollada en Java, implementa un sistema de **criptografía simétrica** para proteger la confidencialidad de archivos de texto. Utiliza el algoritmo **AES (Advanced Encryption Standard)**, el estándar de cifrado utilizado globalmente por gobiernos y organizaciones de seguridad para proteger datos sensibles.

---

## 📝 Descripción

El software permite cifrar y descifrar archivos `.txt` mediante una contraseña definida por el usuario. El proceso transforma el contenido del archivo en un bloque de datos ilegibles para cualquier persona que no posea la clave correcta. Una característica destacada es el manejo robusto de errores, diferenciando entre fallos generales y el uso de llaves incorrectas.

## 🚀 Características

* **Cifrado AES:** Implementación del algoritmo Rijndael para un cifrado de bloque seguro.
* **Derivación de Llave:** Uso de **SHA-1** para generar un hash de la contraseña del usuario, asegurando que la llave final cumpla con el tamaño requerido por AES (128 bits / 16 bytes).
* **Manejo de Archivos:** Lectura y escritura binaria mediante `java.nio.file`, lo que garantiza eficiencia en el procesamiento.
* **Seguridad de Interfaz:** Uso de componentes `JPasswordField` para evitar que las contraseñas sean visibles en pantalla durante la captura.
* **Nomenclatura Automática:** Genera archivos de salida con sufijos (`_e` para cifrado, `_d` para descifrado) respetando la ruta y extensión original.

## 🛠️ Requisitos

* **Lenguaje:** Java 8 o superior.
* **IDE:** NetBeans, IntelliJ IDEA o VS Code.
* **Librerías:** `javax.crypto`, `java.security`, `java.nio`.

## 📖 Funcionamiento Técnico

### 1. Preparación de la Llave (Key Setup)
Dado que AES requiere llaves de longitudes específicas (16, 24 o 32 bytes) y el usuario puede ingresar cualquier texto, el programa:
1. Toma la contraseña en UTF-8.
2. Genera un hash único mediante **SHA-1**.
3. Recorta el resultado a los primeros **16 bytes** para obtener una llave de 128 bits válida.

### 2. Proceso de Cifrado / Descifrado
Utiliza la clase `Cipher` de Java en los siguientes modos:
* **ENCRYPT_MODE:** Transforma el texto plano en bytes cifrados.
* **DECRYPT_MODE:** Realiza el proceso inverso. Si la llave no coincide con la firma del archivo, el programa lanza una excepción de tipo `BadPaddingException`, la cual es capturada para informar al usuario.

## 🖥️ Guía de Uso

1. **Ejecutar:** Inicia la aplicación desde `Main.java`.
2. **Para Cifrar (Encrypt):**
   - Selecciona un archivo `.txt`.
   - Ingresa una contraseña segura en el campo "LLAVE".
   - Presiona "Ejecutar Cifrado". Se creará un archivo llamado `nombre_e.txt`.
3. **Para Descifrar (Decrypt):**
   - Selecciona el archivo cifrado.
   - Ingresa la misma contraseña.
   - Presiona "Ejecutar Descifrado". Se generará `nombre_d.txt` con el contenido original.

---

## 🏗️ Estructura del Proyecto

* `CifradoDescifrado.java`: Contiene la lógica de seguridad, generación de llaves y manipulación de bytes.
* `InterfazGrafica.java`: JFrame que gestiona la vista y el control de errores mediante diálogos de usuario.
* `Main.java`: Launcher oficial del proyecto.

---
> **Equipo de Desarrollo:** luisito2477-dev  
> **Materia:** Introducción a la Criptografía.
