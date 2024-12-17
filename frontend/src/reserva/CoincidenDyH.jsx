import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from 'react-router-dom';
import './CoincidenDyH.css';

const CoincidenDyH = () => {
    const location = useLocation();
    const navigate = useNavigate();

    // Datos de las aulas y reserva desde la navegación
    const data = location.state?.data?.data || {};
    console.log("Lo que llega a coinciden es: ", data)
    const aulas = data.aulas || [];
    const reserva = data.reserva || {};

    // Estado para manejar conflictos detectados
    const [conflictos, setConflictos] = useState([]);

    useEffect(() => {
        // Procesar conflictos al cargar el componente
        const conflictosDetectados = [];
        aulas.forEach((grupo) => {
            grupo.aulas.forEach((aula) => {
                if (aula.reservaSuperpuesta && aula.horarioSuperpuesto) {
                    conflictosDetectados.push({
                        numeroDeAula: aula.numeroDeAula,
                        piso: aula.piso,
                        tipoPizarron: aula.tipoPizarron,
                        capacidad: aula.capacidad,
                        diaSemana: aula.horarioSuperpuesto.diaSemana, // Add this field
                        inicioSolapamiento: aula.horarioSuperpuesto.horaInicio,
                        finSolapamiento: aula.horarioSuperpuesto.horaFin,
                        reservaSuperpuesta: aula.reservaSuperpuesta,
                    });
                }
            });
        });

        // Ordenar conflictos por la duración del solapamiento
        const conflictosOrdenados = conflictosDetectados.sort((a, b) => {
            const duracionA = new Date(`1970-01-01T${a.finSolapamiento}`) - new Date(`1970-01-01T${a.inicioSolapamiento}`);
            const duracionB = new Date(`1970-01-01T${b.finSolapamiento}`) - new Date(`1970-01-01T${b.inicioSolapamiento}`);
            return duracionA - duracionB;
        });

        setConflictos(conflictosOrdenados);
    }, [aulas]);

    const handleVolver = () => navigate(-1);

    return (
        <div className="CDYH-conteiner">
            <h2>Coinciden días y horarios</h2>
            {conflictos.length === 0 ? (
                <p>No hay conflictos detectados.</p>
            ) : (
                <table className="CDYH-tabla">
                    <thead className="CDYH-cabezaTabla">
                        <tr>
                            <th>Aula</th>
                            <th>Piso</th>
                            <th>Capacidad</th>
                            <th>Día</th> {/* Add this column */}
                            <th>Inicio Conflicto</th>
                            <th>Fin Conflicto</th>
                            <th>Profesor</th>
                            <th>Cátedra</th>
                            <th>Correo</th>
                        </tr>
                    </thead>
                    <tbody className="CDYH-cuerpoTabla">
                        {conflictos.map((conflicto, index) => (
                            <tr key={index}>
                                <td>{conflicto.numeroDeAula}</td>
                                <td>{conflicto.piso}</td>
                                <td>{conflicto.capacidad}</td>
                                <td>{conflicto.diaSemana}</td> {/* Add this field */}
                                <td>{conflicto.inicioSolapamiento}</td>
                                <td>{conflicto.finSolapamiento}</td>
                                <td>{`${conflicto.reservaSuperpuesta.apellidoProfesor}, ${conflicto.reservaSuperpuesta.nombreProfesor}`}</td>
                                <td>{conflicto.reservaSuperpuesta.nombreCatedra}</td>
                                <td>{conflicto.reservaSuperpuesta.correo}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
            <button className="CDYH-volver" onClick={handleVolver}>
                Volver
            </button>
        </div>
    );
};

export default CoincidenDyH;
