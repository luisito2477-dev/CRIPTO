
import { useState } from 'react';
import CryptoForm from '../components/CryptoForm';
import ImagePreview from '../components/ImagePreview';

function MainPage() {
  // Estados que sirven de puente entre la columna izquierda y la derecha
  const [originalImg, setOriginalImg] = useState(null);
  const [processedImg, setProcessedImg] = useState(null);

  return (
    <div className="grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
      
      {/* Columna Izquierda: Panel de Control (Ocupa 5 de 12 columnas en pantallas grandes) */}
      <div className="lg:col-span-5 bg-zinc-900/50 border border-zinc-800 p-6 rounded-xl backdrop-blur-sm shadow-[0_4px_30px_rgba(0,0,0,0.4)]">
        <h2 className="text-xl font-bold text-purple-400 mb-4 flex items-center gap-2">
          <span className="text-lg">🎛️</span> DES Control Panel
        </h2>
        
        {/* Le inyectamos las funciones que actualizan los estados del papá */}
        <CryptoForm 
          setOriginalImg={setOriginalImg} 
          setProcessedImg={setProcessedImg} 
        />
      </div>

      {/* Columna Derecha: Visor de Bloques / Imágenes (Ocupa 7 de 12 columnas) */}
      <div className="lg:col-span-7 bg-zinc-900/50 border border-zinc-800 p-6 rounded-xl backdrop-blur-sm h-full shadow-[0_4px_30px_rgba(0,0,0,0.4)]">
        <h2 className="text-xl font-bold text-purple-400 mb-4 flex items-center gap-2">
          <span className="text-lg">🖼️</span> Image Monitor
        </h2>
        
        {/* Le pasamos los strings Base64 con prefijo para que los renderice */}
        <ImagePreview 
          originalImg={originalImg} 
          processedImg={processedImg} 
        />
      </div>

    </div>
  );
}

export default MainPage;