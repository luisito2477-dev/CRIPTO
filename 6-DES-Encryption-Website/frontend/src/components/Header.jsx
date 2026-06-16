import { useState, useEffect } from 'react';

export default function Header() {
  // Estado para controlar si el backend está vivo o muerto
  const [isOnline, setIsOnline] = useState(true); 
  const STATUS_URL = "http://localhost:8080/api/status";

  useEffect(() => {
    // Función que valida el estado del servidor
    const checkServerStatus = async () => {
        try {
            const response = await fetch(STATUS_URL, { method: "GET" });
            if (response.ok) {
            setIsOnline(true);
            } else {
            setIsOnline(false);
            }
        } catch (error) {
            // Si el fetch falla (puerto cerrado, error de red), cae aquí de inmediato
            setIsOnline(false);
        }
        };

        // 1. Ejecutar la primera validación al montar el componente
        checkServerStatus();

        // 2. Crear un intervalo para que cheque el estado cada 5 segundos (5000 ms)
        const interval = setInterval(checkServerStatus, 5000);

        // 3. Limpieza del intervalo si el usuario se va de la página para evitar fugas de memoria
        return () => clearInterval(interval);
    }, []);

  return (
    <header className="w-full border-b border-zinc-800/80 bg-zinc-900/40 backdrop-blur-md z-50">
      <div className="max-w-[1400px] mx-auto px-6 h-16 flex items-center justify-between">
        
        {/* Parte Izquierda: Nombre de la práctica */}
        <div className="flex items-center gap-3">
          <div className="bg-purple-900/30 text-purple-400 p-2 rounded-lg border border-purple-500/20 shadow-[0_0_15px_rgba(147,51,234,0.1)]">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={2} stroke="currentColor" className="w-5 h-5">
              <path strokeLinecap="round" strokeLinejoin="round" d="M15.75 5.25a3 3 0 0 1 3 3m3 0a6 6 0 1 1-7.029 5.912c-.563-.097-1.159.026-1.563.43L10.5 17.25H8.25v2.25H6v2.25H2.25v-2.818c0-.597.237-1.17.659-1.591l6.499-6.499c.404-.404.527-1 .43-1.563A6 6 0 1 1 21.75 8.25Z" />
            </svg>
          </div>
          <div>
            <h1 className="text-lg font-bold tracking-tight bg-gradient-to-r from-zinc-100 via-purple-200 to-purple-400 bg-clip-text text-transparent">
              DES File Encryptor
            </h1>
            <p className="text-[10px] text-zinc-500 uppercase tracking-widest hidden sm:block">
              Equipo 5 / Testigos de AMLO
            </p>
          </div>
        </div>

        {/* Parte Derecha: Estado de la conexion DINaMICO */}
        <div 
          className={`flex items-center gap-2 border px-3 py-1.5 rounded-full shadow-sm transition-all duration-300 ${
            isOnline 
              ? 'bg-purple-950/20 border-purple-500/20 text-purple-300 shadow-[0_0_10px_rgba(147,51,234,0.05)]' 
              : 'bg-red-950/20 border-red-500/30 text-red-400 shadow-[0_0_10px_rgba(239,68,68,0.05)]'
          }`}
        >
          {/* Canica de red: Cambia color y animación según el estado */}
          <span className="relative flex h-2 w-2">
            {isOnline && (
              <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-purple-400 opacity-75"></span>
            )}
            <span className={`relative inline-flex rounded-full h-2 w-2 ${isOnline ? 'bg-purple-500' : 'bg-red-500'}`}></span>
          </span>
          
          <span className="text-xs font-semibold tracking-wider uppercase font-mono">
            {isOnline ? 'Online' : 'Offline'}
          </span>
        </div>

      </div>
    </header>
  );
}
