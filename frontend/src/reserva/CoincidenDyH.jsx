import React, { useState } from "react";
import { useLocation } from 'react-router-dom';
import { HashRouter, useNavigate } from 'react-router-dom';
import './CoincidenDyH.css'


const CoincidenDyH = () => {
    const [conflictos, setConflictos] = useState([]);
    const [errorVisible, setErrorVisible] = useState(false);
    const navigate = useNavigate();
    const [diasRegistrados, setDiasRegistrados] = useState(location.state?.diasRegistrados || []);
    const location = useLocation();

    const handleRegistrar = async () => {

        try {
            const response = await fetch("/ReservaEsporadica/verificar", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(diasRegistrados),
            });

            if (!response.ok) {
                const data = await response.json();
                setConflictos(data.conflictos); // Lista de conflictos
                setErrorVisible(true); // Muestra el modal
            } else {
                alert("Evento registrado exitosamente");
            }
        } catch (error) {
            console.error("Error al registrar el evento", error);
        }
    };

    return (
        <div className="CDYH-conteiner">
            <h2>Coinciden días y horarios</h2>
            <table className="CDYH-tabla">
                <thead className="CDYH-cabezaTabla">
                    <tr className="CDYH-tr">
                        <th className="CDYH-columnas">Días</th>
                        <th className="CDYH-columnas">Horas</th>
                        <th className="CDYH-columnas">Duración</th>
                        <th className="CDYH-columnas">Tipo</th>
                    </tr>
                </thead>
                <tbody className="CDYH-cuerpoTabla">
                    {conflictos.map((conflicto, index) => (
                        <tr className="CDYH-tr" key={index}>
                            <td className="CDYH-data">{conflicto.dia}</td>
                            <td className="CDYH-data">{conflicto.hora}</td>
                            <td className="CDYH-data">{conflicto.duracion} min</td>
                            <td className="CDYH-data">{conflicto.tipo}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <button className="CDYH-volver" onClick={navigate('/login/ReservaClaseE', { state: { diasRegistrados } })}>Volver</button>
        </div>
    );
}

export default CoincidenDyH;