import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./ElegirAula.css"; // Archivo CSS externo

const ElegirAula = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [indiceDia, setIndiceDia] = useState(0);

    // Datos de las aulas y la reserva
    const aulas = location.state?.aulas || [];
    const reserva = location.state?.reserva || {};
    const [diasSeleccionados, setDiasSeleccionados] = useState([]);

    const handleSiguiente = () => {
        if (!diasSeleccionados[indiceDia]) {
            alert("Debes seleccionar un aula antes de continuar.");
            return;
        }

        if (indiceDia < aulas.length - 1) {
            setIndiceDia(indiceDia + 1);
        } else {
            alert("Reserva completada para todos los días.");
            enviarDatos(); // Enviar los datos al backend
        }
    };
    const elegirAula = (aula) => {
        const diaSeleccionado = {
            aula: aula, // Objeto AulaDTO
            fechas: aulas[indiceDia].fechas, // CDU01FechaDTO
            dias: aulas[indiceDia].dias, // CDU01DiasDTO
        };

        setDiasSeleccionados((prev) => [...prev, diaSeleccionado]);
        alert(`Aula seleccionada: ${aula.ubicacion} para el día ${aulas[indiceDia].dias.dia}`);
    };
    const enviarDatos = async () => {
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
                navigate("/ruta-final");
            } else {
                const error = await response.json();
                alert("Error al guardar la reserva: " + error.message);
            }
        } catch (error) {
            console.error("Error al enviar los datos:", error);
            alert("Error de red.");
        }
    }
    return (
        <div className="lista-aulas-container">
            <button className="back-button" onClick={goBack}>
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 24 24"
                    fill="currentColor"
                    width="32"
                    height="32"
                >
                    <path d="M15.41 16.59L10.83 12l4.58-4.59L14 6l-6 6 6 6z" />
                </svg>
            </button>
            <h2>
                Día: {aulas[indiceDia].dias.dia} - Fecha: {aulas[indiceDia].fechas.fechaInicio}
            </h2>

            {aulas.length === 0 ? (
                <p className="mensaje-no-aulas">
                    NO HAY AULAS DISPONIBLES
                </p>
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
                        {aulas[indiceDia].aulas.map((aula, index) => (
                            <tr key={index}>
                                <td>{aula.ubicacion}</td>
                                <td>{aula.capacidad}</td>
                                <td>{aula.caracteristicas.join(", ")}</td>
                                <td>
                                    <button
                                        className="botonSeleccionar"
                                        onClick={() => elegirAula(aula)}
                                    >
                                        Seleccionar
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
            <button className="botonElegirAula" onClick={handleSiguiente}>Siguiente</button>
        </div>
    );
};

export default ElegirAula;
