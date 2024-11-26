import { useState, useEffect } from 'react'
import { HashRouter, useNavigate } from 'react-router-dom';
import './TipoDeReserva.css'

const TipoDeReserva = () => {
    const navigate = useNavigate();

    const goToRegReservaPeriodica = (tipoReserva) => {
        navigate('/login/RegistrarReservaPeriodica', { state: { tipoReserva } });
    }
    const goToRegReservaEsporadica = () => {
        navigate('/login/RegistrarReservaEsporadica');
    }
    return (
        <div className='conteiner-principal-tipo-reserva'>
            <div className="seccion-bienvenida-tipo-reserva">
                <h1>Tipo de reserva</h1>
                <p>Seleccione el tipo de reserva</p>

            </div>
            <div className="conteiner-tipo-reserva">
                <h1>Período</h1>
                <div className="botones-periodo-tipo-reserva">
                    <button className='button-tipo-reserva' onClick={() => goToRegReservaPeriodica("Primer cuatrimestre")}>
                        Primer cuatrimestre
                    </button>
                    <button className='button-tipo-reserva' onClick={() => goToRegReservaPeriodica("Segundo cuatrimestre")}>
                        Segundo cuatrimestre
                    </button>
                    <button className='button-tipo-reserva' onClick={() => goToRegReservaPeriodica("Anual")}>
                        Anual
                    </button>
                </div>
                <h1>Esporádicas</h1>
                <div className="botones-esporadica-tipo-reserva">
                    <button className='button-tipo-reserva' onClick={() => goToRegReservaEsporadica()}>Esporádica</button>
                </div>

            </div>
        </div>
    )
}

export default TipoDeReserva;