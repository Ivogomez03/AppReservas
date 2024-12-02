import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { FaEdit, FaTrashAlt } from "react-icons/fa"; // Iconos para editar y eliminar
import "./ListaBedeles.css"; // Archivo CSS externo

const ListaBedeles = () => {
    const location = useLocation();
    const navigate = useNavigate();

    // Obtenemos la lista de bedeles desde location.state
    const bedeles = location.state?.bedeles || [];

    // Función para manejar la eliminación
    const handleEliminar = (idUsuario) => {
        navigate(`/login/bienvenidoAdmin/BuscarBedel/EliminarBedel/${idUsuario}`);
        console.log(`Eliminar bedel con ID: ${idUsuario}`);
    };

    // Función para manejar la edición
    const handleModificar = (bedel) => {
        navigate(`/login/bienvenidoAdmin/BuscarBedel/ModificarBedel`, { state: { bedel: bedel } });
    };

    return (
        <div className="lista-bedeles-container">
            <h2 className="titulo">Bedeles seleccionados</h2>
            <table className="tabla-bedeles">
                <thead>
                    <tr>
                        <th>Apellido</th>
                        <th>Nombre</th>
                        <th>Turno</th>
                        <th>ID Usuario</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {bedeles.map((bedel, index) => (
                        <tr key={index}>
                            <td>{bedel.apellido}</td>
                            <td>{bedel.nombre}</td>
                            <td>{bedel.turnoDeTrabajo}</td>
                            <td>{bedel.idUsuario}</td>
                            <td>
                                <button
                                    onClick={() => handleModificar(bedel)}
                                    className="btn-accion btn-editar"
                                >
                                    <FaEdit />
                                </button>
                                <button
                                    onClick={() => handleEliminar(bedel.idUsuario)}
                                    className="btn-accion btn-eliminar"
                                >
                                    <FaTrashAlt />
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ListaBedeles;
