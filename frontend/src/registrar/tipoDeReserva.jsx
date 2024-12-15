import { useState, useEffect } from 'react'
import { HashRouter, useNavigate } from 'react-router-dom';
import './TipoDeReserva.css'

const TipoDeReserva = () => {
    const navigate = useNavigate();

    const goBack = () => {
        navigate("/login/bienvenidoBedel"); // Navega hacia la página anterior
    };

    const goToRegReservaPeriodica = (tipoReserva) => {
        navigate('/login/RegistrarReservaPeriodica', { state: { tipoReserva } });
    }
    const goToRegReservaEsporadica = () => {
        navigate('/login/RegistrarReservaEsporadica');
    }
    return (
        <div className='conteiner-principal-tipo-reserva'>
            <div className="seccion-bienvenida-tipo-reserva">
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