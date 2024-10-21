import { useState } from 'react'
import './CancelarBedel.css';

const CancelarBedel = ({ mostrar }) => {


    return (
        <div className='container1' >
            <div className='Titulos'>
                <h1>Esta seguro de cancelar?</h1>
                <h3>Si cancela el procedimiento, perdera todo el progreso hasta ahora.</h3>
            </div>
            <div className='Botones'>
                <button className='BotonVolver' onClick={mostrar}>Volver</button>
                <button className='BotonCancelar'>Cancelar</button>
            </div>
        </div>
    )
}
export default CancelarBedel;