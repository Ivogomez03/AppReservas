import React from 'react';
import './App.css'
import { BrowserRouter as Router, Route, Routes, useNavigate } from 'react-router-dom';
import Login from './login/Login'
const App = () => {
    const navigate = useNavigate();

    const goToLogin = () => {
        navigate('/login');
    };

    return (
        <div className='conteiner-App'>
            <header>
                <h1>Bienvenido al Sistema de Gestión de Reservas</h1>

            </header>
            <section className='seccion-App'>
                <p>Este sistema permite gestionar reservas de aulas de forma rápida y eficiente.</p>
                <div>
                    <img src="./src/assets/person.svg" />
                    <h3>Inicie sesión para acceder a nuevas funcionalidades</h3>
                    <button className='boton-seccion' onClick={goToLogin}>Iniciar Sesión</button>
                </div>

            </section>
            <footer>
                <h3>Conocenos en nuestras redes sociales:</h3>
            </footer>
        </div>
    );
}

const MainApp = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<App />} />
                <Route path="/login" element={<Login />} />
            </Routes>
        </Router>
    );
}
export default MainApp;