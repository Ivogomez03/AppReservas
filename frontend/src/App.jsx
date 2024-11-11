import React from 'react';
import './App.css'
import { BrowserRouter as Router, Route, Routes, useNavigate } from 'react-router-dom';

const App = () => {
    const navigate = useNavigate();

    const goToLogin = () => {
        navigate('/login');
    };

    return (
        <div>
            <h1>Bienvenido al Sistema de Gestión de Reservas</h1>
            <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                <h3>Inicie sesión para continuar</h3>
                <button onClick={goToLogin}>Iniciar Sesión</button>
            </div>
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