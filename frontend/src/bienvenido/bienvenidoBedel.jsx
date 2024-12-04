import { useState, useEffect } from 'react'
import { HashRouter, useNavigate } from 'react-router-dom';
import './bienvenidoBedel.css'
const BienvenidoBedel = () => {
    const navigate = useNavigate();

    const goToTipoReserva = () => {
        navigate('/login/bienvenidoBedel/TipoReserva');
    }
    const goToBuscarReservaDE = () => {
        navigate('/login/bienvenidoBedel/BuscarReservaDE');
    }
    const goToBuscarReservaPC = () => {
        navigate('/login/bienvenidoBedel/BuscarReservaPC');
    }
    return (
        <div className='conteiner-bienvenido-bedel'>
            <div className='ventanaTransparente-bienvenido-bedel'>
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
                            <button>
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