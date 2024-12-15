import React from 'react';
import './App.css'
import { HashRouter as Router, Route, Routes, useNavigate } from 'react-router-dom';
import Login from './login/Login'
import RegistrarBedel from './registrar/RegistrarBedel'
import BienvenidoBedel from './bienvenido/bienvenidoBedel'
import BienvenidoAdmin from './bienvenido/bienvenidoAdmin'
import TipoDeReserva from './registrar/TipoDeReserva'
import RegistrarReservaP from './registrar/RegistrarReservaP';
import ReservaClaseP from './reserva/ReservaClaseP';
import RegistrarReservaE from './registrar/RegistrarReservaE';
import ReservaClaseE from './reserva/ReservaClaseE';
import BuscarBedel from './buscar/BuscarBedel';
import ModificarBedel from './modificar/ModificarBedel';
import ListaBedeles from './buscar/ListaBedeles';
import CoincidenDyH from './reserva/CoincidenDyH';
import BuscarReservasDE from './buscar/BuscarReservasDE';
import BuscarReservasPC from './buscar/BuscarReservasPC';
import BuscarAulas from './buscar/BuscarAulas';
import ListaAulas from './buscar/ListaAulas';
import ModificarAulaMultimedio from './modificar/ModificarAulaMultimedio';
import ModificarAulaSRA from './modificar/ModificarAulaSRA';
import ModificarAulaInformatica from './modificar/ModificarAulaInformatica';
import EliminarBedel from './eliminar/EliminarBedel'
import ElegirAula from './registrar/ElegirAula/ElegirAula';
import ElegirAulaE from './registrar/ElegirAula/ElegirAulaE';
const App = () => {
    const navigate = useNavigate();

    const goToLogin = () => {
        navigate('/login');
    };

    return (
        <div className='conteiner-App'>
            <header>
                <h1>Bienvenido al Sistema de Gestión de Reservas</h1>
                <button className='boton-seccion' onClick={goToLogin}>Iniciar Sesión</button>

            </header>
            <section className='seccion-App'>
                <div className="seccion-App-cols">
                    <h2>Gestión de horarios</h2>
                    <img src="/schedule.png" alt="Gestion de horarios" />
                </div>
                <div className="seccion-App-cols">
                    <h2>Organización eficiente</h2>
                    <img src="/diagram.png" alt="Organización" />
                </div>
                <div className="seccion-App-cols">
                    <h2>Reserva de aula</h2>
                    <img src="/reserved.png" alt="Reserva" />
                </div>


            </section >
            <footer className='footer-App'>
                <div className="footer-col">
                    <h3>Acerca de</h3>
                    <p>Este sistema permite gestionar reservas de aulas de manera eficiente y rápida.</p>
                </div>
                <div className="footer-col">
                    <h3>Redes Sociales</h3>
                    <div className="footer-col-redes">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-linkedin" viewBox="0 0 16 16">
                            <path d="M0 1.146C0 .513.526 0 1.175 0h13.65C15.474 0 16 .513 16 1.146v13.708c0 .633-.526 1.146-1.175 1.146H1.175C.526 16 0 15.487 0 14.854zm4.943 12.248V6.169H2.542v7.225zm-1.2-8.212c.837 0 1.358-.554 1.358-1.248-.015-.709-.52-1.248-1.342-1.248S2.4 3.226 2.4 3.934c0 .694.521 1.248 1.327 1.248zm4.908 8.212V9.359c0-.216.016-.432.08-.586.173-.431.568-.878 1.232-.878.869 0 1.216.662 1.216 1.634v3.865h2.401V9.25c0-2.22-1.184-3.252-2.764-3.252-1.274 0-1.845.7-2.165 1.193v.025h-.016l.016-.025V6.169h-2.4c.03.678 0 7.225 0 7.225z" />
                        </svg>
                        <a href="#">LinkedIn Mario</a>
                    </div>
                    <div className="footer-col-redes">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-linkedin" viewBox="0 0 16 16">
                            <path d="M0 1.146C0 .513.526 0 1.175 0h13.65C15.474 0 16 .513 16 1.146v13.708c0 .633-.526 1.146-1.175 1.146H1.175C.526 16 0 15.487 0 14.854zm4.943 12.248V6.169H2.542v7.225zm-1.2-8.212c.837 0 1.358-.554 1.358-1.248-.015-.709-.52-1.248-1.342-1.248S2.4 3.226 2.4 3.934c0 .694.521 1.248 1.327 1.248zm4.908 8.212V9.359c0-.216.016-.432.08-.586.173-.431.568-.878 1.232-.878.869 0 1.216.662 1.216 1.634v3.865h2.401V9.25c0-2.22-1.184-3.252-2.764-3.252-1.274 0-1.845.7-2.165 1.193v.025h-.016l.016-.025V6.169h-2.4c.03.678 0 7.225 0 7.225z" />
                        </svg>
                        <a href="#">LinkedIn Ivo</a>
                    </div>
                    <div className="footer-col-redes">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-linkedin" viewBox="0 0 16 16">
                            <path d="M0 1.146C0 .513.526 0 1.175 0h13.65C15.474 0 16 .513 16 1.146v13.708c0 .633-.526 1.146-1.175 1.146H1.175C.526 16 0 15.487 0 14.854zm4.943 12.248V6.169H2.542v7.225zm-1.2-8.212c.837 0 1.358-.554 1.358-1.248-.015-.709-.52-1.248-1.342-1.248S2.4 3.226 2.4 3.934c0 .694.521 1.248 1.327 1.248zm4.908 8.212V9.359c0-.216.016-.432.08-.586.173-.431.568-.878 1.232-.878.869 0 1.216.662 1.216 1.634v3.865h2.401V9.25c0-2.22-1.184-3.252-2.764-3.252-1.274 0-1.845.7-2.165 1.193v.025h-.016l.016-.025V6.169h-2.4c.03.678 0 7.225 0 7.225z" />
                        </svg>
                        <a href="#">LinkedIn Tomi</a>
                    </div>
                    <div className="footer-col-redes">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-linkedin" viewBox="0 0 16 16">
                            <path d="M0 1.146C0 .513.526 0 1.175 0h13.65C15.474 0 16 .513 16 1.146v13.708c0 .633-.526 1.146-1.175 1.146H1.175C.526 16 0 15.487 0 14.854zm4.943 12.248V6.169H2.542v7.225zm-1.2-8.212c.837 0 1.358-.554 1.358-1.248-.015-.709-.52-1.248-1.342-1.248S2.4 3.226 2.4 3.934c0 .694.521 1.248 1.327 1.248zm4.908 8.212V9.359c0-.216.016-.432.08-.586.173-.431.568-.878 1.232-.878.869 0 1.216.662 1.216 1.634v3.865h2.401V9.25c0-2.22-1.184-3.252-2.764-3.252-1.274 0-1.845.7-2.165 1.193v.025h-.016l.016-.025V6.169h-2.4c.03.678 0 7.225 0 7.225z" />
                        </svg>
                        <a href="#">LinkedIn Migue</a>
                    </div>

                </div>
                <div className="footer-col">
                    <h3>Créditos</h3>
                    <p>Desarrollado por el equipo de Ingeniería UTN</p>
                    <p>&copy; 2024 Gestión de Reservas</p>
                </div>
            </footer>

        </div >
    );
}

const MainApp = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<App />} />
                <Route path="/login" element={<Login />} />
                <Route path="/login/bienvenidoAdmin/RegistrarBedel" element={<RegistrarBedel />} />
                <Route path="/login/bienvenidoAdmin/BuscarBedel" element={<BuscarBedel />} />
                <Route path="/login/bienvenidoAdmin/BuscarBedel/ModificarBedel" element={<ModificarBedel />} />
                <Route path="/login/bienvenidoAdmin/BuscarBedel/EliminarBedel" element={<EliminarBedel />} />
                <Route path="/login/bienvenidoAdmin/BuscarBedel/ListaBedeles" element={<ListaBedeles />} />
                <Route path="/login/bienvenidoAdmin" element={<BienvenidoAdmin />} />
                <Route path="/login/bienvenidoBedel" element={<BienvenidoBedel />} />
                <Route path="/login/bienvenidoBedel/TipoReserva" element={<TipoDeReserva />} />
                <Route path="/login/bienvenidoBedel/BuscarReservasDE" element={<BuscarReservasDE />} />
                <Route path="/login/bienvenidoBedel/BuscarReservasPC" element={<BuscarReservasPC />} />
                <Route path="/login/bienvenidoBedel/BuscarAulas" element={<BuscarAulas />} />
                <Route path="/login/bienvenidoBedel/BuscarAulas/ListaAulas" element={<ListaAulas />} />
                <Route path="/login/bienvenidoBedel/BuscarAulas/ListaAulas/ModificarAulaMultimedio" element={<ModificarAulaMultimedio />} />
                <Route path="/login/bienvenidoBedel/BuscarAulas/ListaAulas/ModificarAulaSinRecursosAdicionales" element={<ModificarAulaSRA />} />
                <Route path="/login/bienvenidoBedel/BuscarAulas/ListaAulas/ModificarAulaInformatica" element={<ModificarAulaInformatica />} />
                <Route path="/login/RegistrarReservaPeriodica" element={<RegistrarReservaP />} />
                <Route path="/login/ReservaClaseP" element={<ReservaClaseP />} />
                <Route path="/login/RegistrarReservaEsporadica" element={<RegistrarReservaE />} />
                <Route path="/login/ReservaClaseE" element={<ReservaClaseE />} />
                <Route path="/login/CoincidenDyH" element={<CoincidenDyH />} />
                <Route path="/login/RegistrarReservaPeriodica/ElegirAula" element={<ElegirAula />} />
                <Route path="/login/RegistrarReservaEsporadica/ElegirAulaE" element={<ElegirAulaE />} />
            </Routes>
        </Router>
    );
}
export default MainApp;