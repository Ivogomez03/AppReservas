import { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './EliminarBedel.css';
import CancelarBedel from './../cancelar/CancelarBedel';

const EliminarBedel = ({ resetForm }) => {
    const navigate = useNavigate();
    const goBack = (e) => {
        e.preventDefault();
        navigate(-1); // Navega hacia la página anterior
    };


    const location = useLocation();

    const bedel = location.state?.bedel;
    console.log(bedel);
    const [form, setForm] = useState({
        apellido: '',
        nombre: '',
        turnoDeTrabajo: '',
        idUsuario: ''
    });

    const [backendErrors, setBackendErrors] = useState({
        idUsuario: '',
        contrasena: ''
    });

    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        if (bedel) {
            setForm({
                apellido: bedel.apellido || '',
                nombre: bedel.nombre || '',
                turnoDeTrabajo: bedel.turnoDeTrabajo || '',
                idUsuario: bedel.idUsuario || ''
            });
        }
    }, [bedel]);



    const handleSubmit = async (e) => {
        e.preventDefault();

        // Validaciones y lógica de envío del formulario...

        try {
            const response = await fetch(`/bedel/CU15`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(form),
            });

            if (!response.ok) {
                // Procesar mensaje de error del backend
                const errorMessage = await response.text();
                setBackendErrors({ general: errorMessage });
                console.error('Error del servidor:', errorMessage);
                return;
            }

            const result = await response.text(); // Mensaje de éxito
            console.log('Éxito:', result);
            alert(result); // O redirigir si es necesario
            navigate("/login/bienvenidoAdmin/BuscarBedel");

        } catch (error) {
            console.error('Error al enviar el formulario:', error);
            setBackendErrors({ general: 'Ocurrió un error inesperado. Por favor, inténtelo de nuevo más tarde.' });
        }
    };

    return (
        <div className="conteinerEliminarBedel">
            <form onSubmit={handleSubmit} className="formularioEliminarBedel">
                <button className="back-button" onClick={goBack} type="button">

                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        fill="currentColor"
                        width="32"
                        height="32"
                    >
                        <path
                            d="M15.41 16.59L10.83 12l4.58-4.59L14 6l-6 6 6 6z"
                        />
                    </svg>
                </button>
                <h2>Eliminar Bedel</h2>

                <input
                    type="text"
                    name="apellido"
                    placeholder="Apellido"
                    value={"Apellido: " + form.apellido}
                    readOnly
                    className="inputEliminarBedel" /* Clase correcta */
                />
                <input
                    type="text"
                    name="nombre"
                    placeholder="Nombre"
                    value={"Nombre: " + form.nombre}
                    readOnly
                    className="inputEliminarBedel" /* Clase correcta */
                />
                <input
                    type="text"
                    name="turnoDeTrabajo"
                    placeholder="Turno de Trabajo"
                    value={"Turno de trabajo: " + form.turnoDeTrabajo}
                    readOnly
                    className="inputEliminarBedel" /* Clase correcta */
                />




                <div className="BotonesEliminarBedel">
                    <button type="submit" className="botonEliminarBedel">Eliminar</button>
                    <button
                        type="button"
                        onClick={() => setShowModal(true)}
                        className="botonCancelar"
                    >
                        Cancelar
                    </button>
                </div>
            </form>
            {showModal && (
                <CancelarBedel
                    onCancel={() => setShowModal(false)}
                    onConfirm={() => navigate('/login/bienvenidoAdmin/BuscarBedel')}
                />
            )}
        </div>
    );
};

export default EliminarBedel;