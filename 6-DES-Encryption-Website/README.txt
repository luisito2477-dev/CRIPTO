============================================================
       INSTRUCCIONES DE ARRANQUE - EQUIPO 5 (DES)
============================================================

REQUISITOS PREVIOS:
- Tener instalado Java 17 o superior.
- Tener instalado Node.js (v18 o v20).

------------------------------------------------------------
PASO 1: LEVANTAR EL BACKEND (JAVA)
------------------------------------------------------------
1. Abre tu IDE (NetBeans/IntelliJ) y carga la carpeta 'backend'.
2. Dale al botón de "Clean and Build" y luego "Run".
3. Alternativamente, en consola dentro de la carpeta 'backend':
   mvn clean package
   java -jar target/backend-1.0-SNAPSHOT.jar

El servidor debe escuchar en: http://localhost:8080

------------------------------------------------------------
PASO 2: LEVANTAR EL FRONTEND (REACT)
------------------------------------------------------------
1. Abre una terminal en la carpeta 'frontend'.
2. Ejecuta el comando para instalar librerías:
   pnpm install  (o "npm install" si no usas pnpm)
3. Arranca la interfaz visual:
   pnpm run dev  (o "npm run dev")

La página se abrirá en: http://localhost:5173
============================================================