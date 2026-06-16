import { useState } from 'react';

/*
public class DESRequest
    private String key;
    private String iV;
    private String bmpFileBase64; //base64
    private String mode;
    private String action;
    private String fileName;

*/

export default function CryptoForm({ setOriginalImg, setProcessedImg }) {

    const BACKEND_URL = "http://localhost:8080/api/bmpdes";

    /**
    States
     */
    const [ bmpFile, setBmpFile ] = useState(null);
    const [ bmpFileBase64, setBmpFileBase64 ] = useState(null);
    const [ fileName, setFileName ] = useState(null);
    const [ key, setKey ] = useState("");
    const [ iV, setIv ] = useState("");
    const [mode, setMode ] = useState("ECB");
    const [action, setAction ] = useState("ENCRYPT");
    const [ loading, setLoading ] = useState(false);
    const [ error, setError ] = useState(null);

    /*
    handleFileChange
    */
    const handleFileChange = async (e) => {
        const file = e.target.files[0];

        setBmpFile(file);

        //GenerarPreview
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => setOriginalImg(reader.result);
        
        //Convertimos a base64
        const base64 = await toBase64(file);

        setBmpFileBase64(base64);

        //Obtenemos nombre
        const name = file.name;
        
        setFileName(name);
        
    }


    /*
    handleSubmit
    */
    const handleSubmit = async(e) => {
        try{
            //evitar que la pagina se recargue
            e.preventDefault();
            setError(null);
            setLoading(true);

            const payload = {
                key,
                iV,
                bmpFileBase64,
                mode,
                action,
                fileName
            }

            const response = await fetch(BACKEND_URL, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(payload),
            });

            const data = await response.json();
            /*Lo que deberia llegar 
                public class DESResponse {
                    private String newBmpFile;
                    private String newFileName;
            */

            if (!response.ok) {
                // Si el backend manda 400 o 500, atrapamos el mensaje del CryptoValidator
                throw new Error(data.error || "An unexpected error occurred in the server. ");
            }

            // Le concatenamos el prefijo para que el tag <img> de React lo pueda renderizar 
            const processedPreviewUrl = `data:image/bmp;base64,${data.newBmpFile}`;

            setProcessedImg(processedPreviewUrl);

            downloadBmpFile(data.newBmpFile, data.newFileName);

        } catch(err){
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }

    /*
    toBase64
    */
    const toBase64 = (file) => {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();

            reader.readAsDataURL(file);

            reader.onload = () => resolve(reader.result.split(',')[1]);
            reader.onerror = reject;
        });
    };


    /*
    downloadBmpFile
    */
    const downloadBmpFile = (base64Puro, nombreArchivo) => {
        // Convertir el Base64 que mando Java a un archivo binario (Blob)
        const byteCharacters = atob(base64Puro);

        const byteNumbers = new Array(byteCharacters.length);
        for (let i = 0; i < byteCharacters.length; i++) {
            byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);
        const blob = new Blob([byteArray], { type: 'image/bmp' });

        // Crear un enlace (anchor tag <a>) oculto en el navegador
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = nombreArchivo; // El nombre con el formato que dio el Java

        // Simular el clic del usuario para forzar la descarga y luego destruir el enlace
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    };

  return (
    <form  onSubmit={handleSubmit}  className="space-y-5">
      
      {/* 1. INPUT DE ARCHIVO .BMP */}
      <div>
        <label className="block text-xs font-semibold uppercase tracking-wider text-purple-300 mb-2">
          Select Image (.bmp)
        </label>
        <div className="relative group">
          <input
            type="file"
            accept=".bmp"
            onChange={handleFileChange} 
            required
            className="w-full text-sm text-zinc-400 file:mr-4 file:py-2 file:px-4 file:rounded-lg file:border-0 file:text-xs file:font-semibold file:bg-purple-900/30 file:text-purple-300 hover:file:bg-purple-900/50 file:transition-colors bg-zinc-950/60 border border-zinc-800 rounded-lg p-2 focus:outline-none focus:border-purple-500/50"
          />
        </div>
      </div>

      {/* 2. SELECTORES DE MODO Y ACCIÓN (GRID DE 2 COLUMNAS) */}
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
        {/* Selector de Modo */}
        <div>
          <label className="block text-xs font-semibold uppercase tracking-wider text-purple-300 mb-2">
            Operation Mode
          </label>
          <select
            value={mode} 
            onChange={(e) => setMode(e.target.value)} 
            className="w-full bg-zinc-950/60 border border-zinc-800 text-zinc-100 rounded-lg p-2.5 text-sm focus:outline-none focus:border-purple-500"
          >
            <option value="ECB">ECB (Electronic Codebook)</option>
            <option value="CBC">CBC (Cipher Block Chaining)</option>
            <option value="CFB">CFB (Cipher Feedback)</option>
            <option value="OFB">OFB (Output Feedback)</option>
            <option value="CTR">CTR (Counter Mode)</option>
          </select>
        </div>

        {/* Selector de Acción */}
        <div>
          <label className="block text-xs font-semibold uppercase tracking-wider text-purple-300 mb-2">
            Action to Perform
          </label>
          <select
            value={action} 
            onChange={(e) => setAction(e.target.value)} 
            className="w-full bg-zinc-950/60 border border-zinc-800 text-zinc-100 rounded-lg p-2.5 text-sm focus:outline-none focus:border-purple-500"
          >
            <option value="ENCRYPT">🔒 Encrypt</option>
            <option value="DECRYPT">🔓 Decrypt</option>
          </select>
        </div>
      </div>

      {/* 3. INPUTS DE LLAVE E IV (SE OCULTA EL IV SI ES ECB) */}
      <div className="space-y-4">
        {/* Llave */}
        <div>
          <label className="block text-xs font-semibold uppercase tracking-wider text-purple-300 mb-1">
            Secret Key (DES Key)
          </label>
          <span className="text-[10px] text-zinc-500 block mb-1.5">It must be 8 characters/bytes long.</span>
          <input
            type="text"
            value={key} 
            onChange={(e) => setKey(e.target.value)} 
            maxLength={8}
            placeholder="Example: 12345678"
            required
            className="w-full bg-zinc-950/60 border border-zinc-800 text-zinc-100 placeholder-zinc-600 rounded-lg p-2.5 text-sm font-mono focus:outline-none focus:border-purple-500 tracking-widest"
          />
        </div>

        {/* Vector de Inicialización (IV) */}
        <div>
          <label className="block text-xs font-semibold uppercase tracking-wider text-purple-300 mb-1">
            Initialization Vector (IV)
          </label>
          <span className="text-[10px] text-zinc-500 block mb-1.5">Required for CBC, CFB, OFB and CTR (8 bytes)</span>
          <input
            type="text"
            value={iV} 
            onChange={(e) => setIv(e.target.value)} 
            maxLength={8}
            placeholder="Example: abcdefgh"
            required={mode !== 'ECB'} 
            disabled={mode === 'ECB'} 
            className="w-full bg-zinc-950/60 border border-zinc-800 text-zinc-100 placeholder-zinc-600 rounded-lg p-2.5 text-sm font-mono focus:outline-none focus:border-purple-500 tracking-widest disabled:opacity-30 disabled:cursor-not-allowed"
          />
        </div>
      </div>

      {/* 4. ALERTA DE ERROR (Se muestra si el backend responde HTTP 400/500 o si falla tu validación) */}
        {error && ( 
        <div className="bg-red-950/30 border border-red-500/30 text-red-400 p-3 rounded-lg text-xs flex items-center gap-2">
          <span>⚠️</span>
          <span>{ error } </span>
        </div>
        )}

      {/* 5. BOTÓN DE ENVÍO */}
      <button
        type="submit"
        disabled={loading}
        className="w-full bg-purple-600 hover:bg-purple-500 text-white font-semibold rounded-lg p-3 text-sm transition-all duration-200 shadow-[0_0_20px_rgba(147,51,234,0.2)] hover:shadow-[0_0_25px_rgba(147,51,234,0.4)] active:scale-[0.98] disabled:opacity-50 disabled:pointer-events-none flex items-center justify-center gap-2"
      >
        {loading ? 'Processing DES...' : 'Execute Operation'} 
      </button>

    </form>
  );
}

