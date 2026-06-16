export default function Footer() {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="w-full border-t border-zinc-900 bg-zinc-950/80 backdrop-blur-sm text-zinc-500 text-xs py-4 mt-auto">
      <div className="max-w-[1400px] mx-auto px-6 flex flex-col sm:flex-row items-center justify-between gap-2 text-center sm:text-left">
        
        {/* Derechos de autor */}
        <div>
          <p>© {currentYear} DES File Encryptor. All rights reserved.</p>
        </div>

        {/* Firma de desarrollador */}
        <div className="flex items-center gap-1 font-medium tracking-wide">
          <span>Developed by</span>
          {/*
          <a 
            href="https://github.com/luisito2477" 
            target="_blank" 
            rel="noopener noreferrer"
            className="text-purple-400 hover:text-purple-300 transition-colors duration-200 hover:underline underline-offset-4 decoration-purple-500/40"
          >
            luisito2477-dev
          </a>
          */}
          Equipo 5
        </div>

      </div>
    </footer>
  );
}

