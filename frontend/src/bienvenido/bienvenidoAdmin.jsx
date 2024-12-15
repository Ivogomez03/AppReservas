import { useState, useEffect } from 'react'
import { HashRouter, useNavigate } from 'react-router-dom';
import './bienvenidoAdmin.css'
const BienvenidoAdmin = () => {
    const navigate = useNavigate();

    const goBack = () => {
        navigate("/"); // Navega hacia la página anterior
    };

    const goToBuscarBedel = () => {
        navigate('/login/bienvenidoAdmin/BuscarBedel');
    }
    const goToRegBedel = () => {
        navigate('/login/bienvenidoAdmin/RegistrarBedel');
    }
    return (
        <div className='conteiner-bienvenido-admin'>
            <div className='ventanaTransparente'>
                <button className="back-button" onClick={goBack}>
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        fill="currentColor"
                        width="32"
                        height="32"
                    >
                        <path
                            d="M15.41 16.59L10.83 12l4.58-4.59L14 6l-6 6 6 6z"
                        />
                    </svg>
                </button>
                <div className="seccion-bienvenido-admin">
                    <h1>Bienvenido Administrador</h1>
                </div>
                <div className='seccion-principal'>
                    <h2>¿Que desea realizar?</h2>
                    <div className='botones-principal'>
                        <button onClick={goToBuscarBedel}>
                            Buscar Bedel
                        </button>
                        <button onClick={goToRegBedel}>
                            Registrar Bedel
                        </button>
                    </div>

                </div>
            </div>

        </div>
    )
}
export default BienvenidoAdmin;