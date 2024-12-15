import { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './ModificarBedel.css';
import CancelarBedel from './../cancelar/CancelarBedel';

const ModificarBedel = ({ resetForm }) => {
    const navigate = useNavigate();
    const goBack = (e) => {
        e.preventDefault()
        navigate("/login/bienvenidoAdmin/BuscarBedel"); // Navega hacia la página anterior
    };

    const onlyLetters = (value) => /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]*$/.test(value);
    const onlyNumbers = (str) => /^[0-9]*$/.test(str);

    const location = useLocation();

    const bedel = location.state?.bedel;
    console.log(bedel);
    const [form, setForm] = useState({
        apellido: '',
        nombre: '',
        turnoDeTrabajo: '',
        idUsuario: '',
        idAdminCreador: '1',
        contrasena: '',
        confirmarContrasena: ''
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
                idUsuario: bedel.idUsuario || '',
                idAdminCreador: bedel.idAdminCreador || '1',
                contrasena: '',
                confirmarContrasena: '',
            });
        }
    }, [bedel]);


    const handleChange = (e) => {
        const { name, value } = e.target;

        // Validar que ciertos campos acepten solo letras
        if (["nombre", "apellido"].includes(name)) {
            if (!onlyLetters(value)) {
                return; // Salir si el valor contiene caracteres no permitidos
            }
        }

        if (["idUsuario"].includes(name) && !onlyNumbers(value)) {
            if (!onlyNumbers(value)) {
                return;
            }
        }
        console.log({ ...form })

        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Validaciones y lógica de envío del formulario...

        try {
            const response = await fetch(`/bedel/CU14/modificarBedel`, {
                method: 'PUT',
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

        } catch (error) {
            console.error('Error al enviar el formulario:', error);
            setBackendErrors({ general: 'Ocurrió un error inesperado. Por favor, inténtelo de nuevo más tarde.' });
        }
    };

    return (
        <div className="conteiner-mod-bedel">
            <div className="panel-izquierdo">
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
                <h1>Por favor</h1>
                <h2>Ingrese los datos solicitados</h2>
            </div>
            <form onSubmit={handleSubmit} className="formulario">
                <h2>Modificar Bedel</h2>

                <input
                    type="text"
                    name="apellido"
                    placeholder="Apellido"
                    value={form.apellido}
                    onChange={handleChange}
                    className="inputModBedel" /* Clase correcta */
                />
                <input
                    type="text"
                    name="nombre"
                    placeholder="Nombre"
                    value={form.nombre}
                    onChange={handleChange}
                    className="inputModBedel" /* Clase correcta */
                />
                <select
                    name="turnoDeTrabajo"
                    value={form.turnoDeTrabajo}
                    onChange={handleChange}
                    className="selectModBedel" /* Clase correcta */
                >
                    <option value="Mañana">Mañana</option>
                    <option value="Tarde">Tarde</option>
                    <option value="Noche">Noche</option>
                </select>

                <input
                    type="text"
                    name="idUsuario"
                    placeholder="Identificador de usuario"
                    value={form.idUsuario}
                    onChange={handleChange}
                    className="inputModBedel" /* Clase correcta */
                    readOnly
                />

                <input
                    type="password"
                    name="contrasena"
                    placeholder="Contraseña"
                    value={form.contrasena}
                    onChange={handleChange}
                    className="inputModBedel" /* Clase correcta */
                />

                <input
                    type="password"
                    name="confirmarContrasena"
                    placeholder="Confirmar contraseña"
                    value={form.confirmarContrasena}
                    onChange={handleChange}
                    className="inputModBedel" /* Clase correcta */
                />

                <div className="BotonesBedel">
                    <button type="submit" className="botonModBedel">Modificar</button>
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

export default ModificarBedel;