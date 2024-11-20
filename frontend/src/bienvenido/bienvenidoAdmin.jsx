import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom';
const BienvenidoAdmin = () => {
    return (
        <div>
            <div className="seccion-bienvenido">
                <h1>Bienvenido Administrador</h1>
            </div>
            <div>
                <h1>¿Que desea realizar?</h1>
                <div>
                    <button>
                        Buscar Bedel
                    </button>
                    <button>
                        Registrar Bedel
                    </button>
                </div>

            </div>
        </div>
    )
}
export default BienvenidoAdmin;