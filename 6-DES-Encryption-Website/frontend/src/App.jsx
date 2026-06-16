import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Header from "./components/Header";
import MainPage from "./pages/MainPage";
import Footer from "./components/Footer"

function App() {
  return (
    <Router>
      {/* Contenedor principal con fondo oscuro zinc y selección en morado */}
      <div className="min-h-screen bg-zinc-950 text-zinc-100 font-sans antialiased selection:bg-purple-600 selection:text-white flex flex-col relative overflow-hidden">
        
        {/* Un destello sutil morado de fondo para que se vea tipo Dashboard Premium */}
        <div className="absolute top-0 left-1/2 -translate-x-1/2 w-[500px] h-[500px] bg-purple-900/10 blur-[120px] rounded-full pointer-events-none" />

        <Header />
        
        <main className="flex-grow p-6 z-10">
          <div className="max-w-[1400px] mx-auto w-full">
            <Routes>

              <Route path="/" element={<Navigate to="/main" replace />} />
              <Route path="/main" element={<MainPage />} />
              
            </Routes>
          </div>
        </main>

        <Footer />                 
      </div>
    </Router>
  );
}

export default App;