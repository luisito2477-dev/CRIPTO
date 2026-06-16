

export default function ImagePreview({ originalImg, processedImg }) {
  return (
    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
      
      {/* 1. CONTENEDOR: IMAGEN ORIGINAL */}
      <div className="flex flex-col items-center">
        <span className="text-xs font-semibold uppercase tracking-wider text-zinc-400 mb-2 self-start">
          Original Image (.bmp)
        </span>
        <div className="w-full aspect-square bg-zinc-950/60 border border-zinc-800 rounded-lg flex items-center justify-center overflow-hidden relative group p-2">
          {originalImg ? (
            <img 
              src={originalImg} 
              alt="Original BMP" 
              className="max-w-full max-h-full object-contain rounded-md"
            />
          ) : (
            <div className="text-center p-4">
              <p className="text-zinc-600 text-sm font-medium">No file uploaded</p>
              <p className="text-[10px] text-zinc-700 mt-1 font-mono">Waiting...</p>
            </div>
          )}
        </div>
      </div>

      {/* 2. CONTENEDOR: IMAGEN PROCESADA (RESULTADO) */}
      <div className="flex flex-col items-center">
        <span className="text-xs font-semibold uppercase tracking-wider text-purple-400 mb-2 self-start">
          DES Result
        </span>
        <div className="w-full aspect-square bg-zinc-950/60 border border-purple-500/10 rounded-lg flex items-center justify-center overflow-hidden relative p-2 shadow-[inset_0_0_20px_rgba(147,51,234,0.02)]">
          {processedImg ? (
            <img 
              src={processedImg} 
              alt="Procesada BMP" 
              className="max-w-full max-h-full object-contain rounded-md animate-fade-in border border-purple-500/20 shadow-[0_0_15px_rgba(147,51,234,0.1)]"
            />
          ) : (
            <div className="text-center p-4">
              <p className="text-zinc-600 text-sm font-medium">Waiting for the operation</p>
              <p className="text-[10px] text-purple-950 mt-1 font-mono uppercase tracking-wider animate-pulse">
                Waiting for the processed file...
              </p>
            </div>
          )}
        </div>
      </div>

    </div>
  );
}

