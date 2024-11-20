import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom';
import './bienvenidoAdmin.css'
const BienvenidoAdmin = () => {
    return (
        <div className='conteiner-bienvenido-admin'>
            <div className='ventanaTransparente'>
                <div className="seccion-bienvenido-admin">
                    <h1>Bienvenido Administrador</h1>
                </div>
                <div className='seccion-principal'>
                    <h2>¿Que desea realizar?</h2>
                    <div className='botones-principal'>
                        <button>
                            Buscar Bedel
                        </button>
                        <button>
                            Registrar Bedel
                        </button>
                    </div>

                </div>
            </div>

        </div>
    )
}
export default BienvenidoAdmin;