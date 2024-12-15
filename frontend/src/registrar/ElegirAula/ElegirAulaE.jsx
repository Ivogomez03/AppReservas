import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./ElegirAula.css";

const ElegirAulaE = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [indiceFecha, setIndiceDia] = useState(0);
    const goBack = () => navigate(-1);

    // Datos de las aulas y la reserva
    const data = location.state.data.data || {};
    console.log(data)
    const aulas = data.aulas || []; // Arreglo de aulas, contiene 3 objetos con su propio array de aulas
    console.log(aulas)
    const reserva = data.reserva || {}; // Datos de la reserva

    // Comprobamos que el objeto data y sus propiedades existen
    if (!data || aulas.length === 0) {
        return <p>No hay datos disponibles.</p>;
    }

    const [diasSeleccionados, setDiasSeleccionados] = useState([]);

    // Manejador para avanzar al siguiente día
    const handleSiguiente = () => {
        if (!diasSeleccionados[indiceFecha]) {
            alert("Debes seleccionar un aula antes de continuar.");
            return;
        }

        if (indiceFecha < aulas.length - 1) {
            setIndiceDia(indiceFecha + 1);
        } else {
            alert("Reserva completada para todos los días.");
            enviarDatos(); // Enviar los datos al backend
        }
    };

    // Manejador para seleccionar un aula
    const elegirAula = (aula) => {
        const diaSeleccionado = {
            aula: aula, // Objeto AulaDTO
            dias: aulas[indiceFecha].dias, // Día correspondiente a este aula
            fechas: aulas[indiceFecha].fechas // Fechas correspondientes a este aula
        };

        setDiasSeleccionados((prev) => [...prev, diaSeleccionado]);
        alert(`Aula seleccionada: ${aula.numeroDeAula} para la fecha ${aulas[indiceFecha].fechas.fecha}`);
    };

    // Enviar los datos al backend
    const enviarDatos = async () => {
        console.log("El dto enviado es: ", {
            reservaYAula: diasSeleccionados, // Lista de CDU01ReservaYAulaFinal
            reserva: reserva, // ReservaDTO
        });

        try {
            const response = await fetch("/reserva/guardar", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    reservaYAula: diasSeleccionados, // Lista de CDU01ReservaYAulaFinal
                    reserva: reserva, // ReservaDTO
                }),
            });

            if (response.ok) {
                alert("Reserva registrada exitosamente.");
                navigate("/login/bienvenidoBedel/TipoReserva");
            } else {
                const error = await response.json();
                alert("Error al guardar la reserva: " + error.message);
            }
        } catch (error) {
            console.error("Error al enviar los datos:", error);
            alert("Error de red.");
        }
    };

    return (
        <div className="lista-aulas-container">
            <button className="back-button" onClick={goBack}>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" width="32" height="32">
                    <path d="M15.41 16.59L10.83 12l4.58-4.59L14 6l-6 6 6 6z" />
                </svg>
            </button>

            <h2>
                {aulas[indiceFecha]?.fechas ? "Día: " + aulas[indiceFecha].fechas.fecha : null} -
                {aulas[indiceFecha]?.fechas ? " Hora: " + aulas[indiceFecha].fechas.horaInicio : null}
            </h2>

            {aulas.length === 0 ? (
                <p className="mensaje-no-aulas">NO HAY AULAS DISPONIBLES</p>
            ) : (
                <table className="tabla-aulas">
                    <thead>
                        <tr>
                            <th>Ubicación aula</th>
                            <th>Capacidad</th>
                            <th>Características</th>
                        </tr>
                    </thead>
                    <tbody>
                        {aulas[indiceFecha]?.aulas.map((aula, index) => (
                            <tr key={index}>
                                <td>{aula.numeroDeAula}</td>
                                <td>{aula.capacidad}</td>
                                <td>{aula.caracteristicas}</td>
                                <td>
                                    <button className="botonSeleccionar" onClick={() => elegirAula(aula)}>
                                        Seleccionar
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}

            <button className="botonSiguiente" onClick={handleSiguiente}>Siguiente</button>
        </div>
    );
};

export default ElegirAulaE;
