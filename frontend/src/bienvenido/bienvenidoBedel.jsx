import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom';
const BienvenidoBedel = () => {
    return (
        <div>
            <div className="seccion-bienvenido">
                <h1>Bienvenido Bedel</h1>
            </div>
            <div>
                <h1>¿Que desea realizar?</h1>
                <div>
                    <button>
                        Registrar Reserva
                    </button>
                    <button>
                        Buscar Aula
                    </button>
                    <button>
                        Ver Reservas para un dia especifico
                    </button>
                    <button>
                        Ver Reservas para un curso
                    </button>
                </div>

            </div>
        </div>
    )
}

export default BienvenidoBedel;