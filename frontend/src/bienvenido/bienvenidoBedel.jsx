import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom';
import './bienvenidoBedel.css'
const BienvenidoBedel = () => {
    return (
        <div className='conteiner-bienvenido-bedel'>
            <div className='ventanaTransparenteBedel'>
                <div className="seccion-bienvenido-bedel">
                    <h1>Bienvenido Bedel</h1>
                </div>
                <div className='seccion-principal'>
                    <h1>¿Que desea realizar?</h1>
                    <div className='botones-principal'>
                        <div className="botones-principal-arriba">
                            <button>
                                Registrar Reserva
                            </button>
                            <button>
                                Buscar Aula
                            </button>
                        </div>
                        <div className="botones-principal-abajo">
                            <button>
                                Ver Reservas para un dia especifico
                            </button>
                            <button>
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