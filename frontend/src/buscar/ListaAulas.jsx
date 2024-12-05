import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { FaEdit, FaTrashAlt } from "react-icons/fa"; // Iconos para editar y eliminar
import "./ListaAulas.css"; // Archivo CSS externo

const ListaAulas = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const goBack = () => {
        navigate(-1); // Navega hacia la página anterior
    };

    // Obtenemos la lista de aulas desde location.state
    const aulas = location.state?.aulas || [];

    // Función para manejar la eliminación
    const handleEliminar = (idAula) => {
        navigate(`/login/bienvenidoBedel/BuscarAulas/EliminarAula/${idAula}`);
        console.log(`Eliminar aula con ID: ${idAula}`);
    };

    // Función para manejar la edición
    const handleModificar = (aula) => {
        if (aula.tipoAula === "Multimedio") {
            navigate(`/login/bienvenidoBedel/BuscarAulas/ListaAulas/ModificarAulaMultimedio`, { state: { aula: aula } });
        }
        else if (aula.tipoAula === "SinRecursosAdicionales") {
            navigate(`/login/bienvenidoBedel/BuscarAulas/ListaAulas/ModificarAulaSinRecursosAdicionales`, { state: { aula: aula } });
        }
        else {
            navigate(`/login/bienvenidoBedel/BuscarAulas/ListaAulas/ModificarAulaInformatica`, { state: { aula: aula } });
        }
    };

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
            <h2 className="titulo">Aulas disponibles</h2>

            {aulas.length === 0 ? (
                <p className="mensaje-no-aulas">
                    No existen aulas que cumplan con los criterios ingresados.
                </p>
            ) : (
                <table className="tabla-aulas">
                    <thead>
                        <tr>
                            <th>Número</th>
                            <th>Piso</th>
                            <th>Tipo</th>
                            <th>Capacidad</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {aulas.map((aula, index) => (
                            <tr key={index}>
                                <td>{aula.numeroDeAula}</td>
                                <td>{aula.piso}</td>
                                <td>{aula.tipoAula}</td>
                                <td>{aula.capacidad}</td>
                                <td>{aula.estado}</td>
                                <td>
                                    <button
                                        onClick={() => handleModificar(aula)}
                                        className="btn-accion btn-editar"
                                    >
                                        <FaEdit />
                                    </button>
                                    <button
                                        onClick={() => handleEliminar(aula.idAula)}
                                        className="btn-accion btn-eliminar"
                                    >
                                        <FaTrashAlt />
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default ListaAulas;
