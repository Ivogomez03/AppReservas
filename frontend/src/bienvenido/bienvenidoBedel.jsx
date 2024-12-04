import { useState, useEffect } from 'react'
import { HashRouter, useNavigate } from 'react-router-dom';
import './bienvenidoBedel.css'
const BienvenidoBedel = () => {
    const navigate = useNavigate();

    const goBack = () => {
        navigate(-1); // Navega hacia la página anterior
    };

    const goToTipoReserva = () => {
        navigate('/login/bienvenidoBedel/TipoReserva');
    }
    const goToBuscarReservaDE = () => {
        navigate('/login/bienvenidoBedel/BuscarReservasDE');
    }
    const goToBuscarReservaPC = () => {
        navigate('/login/bienvenidoBedel/BuscarReservasPC');
    }
    const goToBuscarAulas = () => {
        navigate('/login/bienvenidoBedel/BuscarAulas');
    }
    return (
        <div className='conteiner-bienvenido-bedel'>
            <div className='ventanaTransparente-bienvenido-bedel'>
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


                <div className="seccion-bienvenido-bedel">
                    <h1>Bienvenido Bedel</h1>
                </div>
                <div className='seccion-principal-bienvenido-bedel'>
                    <h1>¿Que desea realizar?</h1>
                    <div className='botones-principal-bienvenido-bedel'>
                        <div className="botones-principal-arriba">
                            <button onClick={goToTipoReserva}>
                                Registrar Reserva
                            </button>
                            <button onClick={goToBuscarAulas}>
                                Buscar Aula
                            </button>
                        </div>
                        <div className="botones-principal-abajo">
                            <button onClick={goToBuscarReservaDE}>
                                Ver Reservas para un dia especifico
                            </button>
                            <button onClick={goToBuscarReservaPC}>
                                Ver Reservas para un curso
                            </button>
                        </div>
                    </div>

                </div>
            </div>

        </div>
    )
}

export default BienvenidoBedel;