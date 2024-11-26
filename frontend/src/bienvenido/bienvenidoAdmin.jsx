import { useState, useEffect } from 'react'
import { HashRouter, useNavigate } from 'react-router-dom';
import './bienvenidoAdmin.css'
const BienvenidoAdmin = () => {
    const navigate = useNavigate();

    const goToBuscarBedel = () => {
        navigate('/login/bienvenidoAdmin/BuscarBedel');
    }
    const goToRegBedel = () => {
        navigate('/login/bienvenidoAdmin/RegistrarBedel');
    }
    return (
        <div className='conteiner-bienvenido-admin'>
            <div className='ventanaTransparente'>
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