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
        <div>
            <h2>Coinciden días y horarios</h2>
            <table>
                <thead>
                    <tr>
                        <th>Días</th>
                        <th>Horas</th>
                        <th>Duración</th>
                        <th>Tipo</th>
                    </tr>
                </thead>
                <tbody>
                    {conflictos.map((conflicto, index) => (
                        <tr key={index}>
                            <td>{conflicto.dia}</td>
                            <td>{conflicto.hora}</td>
                            <td>{conflicto.duracion} min</td>
                            <td>{conflicto.tipo}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <button onClick={navigate('/login/ReservaClaseE', { state: { diasRegistrados } })}>Volver</button>
        </div>
    );
}

export default CoincidenDyH;