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
    const isValidPassword = (password) => {
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
        return passwordRegex.test(password);
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

    const [placeholders, setPlaceholders] = useState({
        apellido: "Apellido",
        nombre: "Nombre",
        turnoDeTrabajo: "Turno de trabajo",
        idUsuario: "Identificador de usuario",
        contrasena: "Contraseña",
        confirmarContrasena: "Confirmar contraseña"
    });

    const [errors, setErrors] = useState({
        apellido: false,
        nombre: false,
        turnoDeTrabajo: false,
        idUsuario: false,
        contrasena: false,
        confirmarContrasena: false
    });
    const resetFormulario = () => {
        setForm({
            apellido: '',
            nombre: '',
            turnoDeTrabajo: '',
            idUsuario: '',
            idAdminCreador: '1',
            contrasena: '',
            confirmarContrasena: ''
        });
        setPlaceholders({
            apellido: "Apellido",
            nombre: "Nombre",
            turnoDeTrabajo: "Turno de trabajo",
            idUsuario: "Identificador de usuario",
            contrasena: "Contraseña",
            confirmarContrasena: "Confirmar contraseña"
        })
        setErrors({
            apellido: false,
            nombre: false,
            turnoDeTrabajo: false,
            idUsuario: false,
            contrasena: false,
            confirmarContrasena: false

        });
        setBackendErrors({
            idUsuario: '',
            contrasena: ''
        });
        setBackendMessage('');
    };
    useEffect(() => {
        if (resetForm) {
            resetForm.current = resetFormulario;
        }
    }, [resetForm]);

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
        // Actualizar el formulario y validar en tiempo real
        setForm((prevForm) => {
            const updatedForm = { ...prevForm, [name]: value };

            // Validar contraseñas en tiempo real
            setErrors((prevErrors) => {
                const updatedErrors = { ...prevErrors };

                if (name === "contrasena" || name === "confirmarContrasena") {
                    // Limpiar errores si las contraseñas coinciden
                    updatedErrors.contrasena = !isValidPassword(updatedForm.contrasena);
                    updatedErrors.confirmarContrasena = updatedForm.contrasena !== updatedForm.confirmarContrasena;
                }

                // Limpiar errores del campo actual
                updatedErrors[name] = false;

                return updatedErrors;
            });

            return updatedForm;
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const newErrors = { ...errors };

        // Validaciones locales
        if (!isValidPassword(form.contrasena)) {
            newErrors.contrasena = true;
            setPlaceholders(prev => ({ ...prev, contrasena: "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número." }));
        }
        if (!form.apellido || form.apellido.length > 50) {
            newErrors.apellido = true;
            setPlaceholders(prev => ({ ...prev, apellido: "Completa el apellido (máximo 50 caracteres)." }));
        }
        if (!form.nombre || form.nombre.length > 50) {
            newErrors.nombre = true;
            setPlaceholders(prev => ({ ...prev, nombre: "Completa el nombre (máximo 50 caracteres)." }));
        }
        if (!form.turnoDeTrabajo) {
            newErrors.turnoDeTrabajo = true;
        }
        if (!form.idUsuario || form.idUsuario.length > 10) {
            newErrors.idUsuario = true;
            setPlaceholders(prev => ({ ...prev, idUsuario: "Completa el identificador (máximo 10 caracteres)." }));
        }
        if (!form.contrasena) {
            newErrors.contrasena = true;
            setPlaceholders(prev => ({ ...prev, contrasena: "Completa la contraseña." }));
        }
        if (form.contrasena !== form.confirmarContrasena) {
            newErrors.confirmarContrasena = true;
            setPlaceholders(prev => ({ ...prev, confirmarContrasena: "Las contraseñas no coinciden." }));
            setForm(prev => ({ ...prev, confirmarContrasena: "" })); // Limpiar el campo
        }

        // Actualizar el estado de errores
        setErrors(newErrors);

        // Si hay errores, detener el envío
        if (Object.values(newErrors).some(error => error)) {
            return;
        }
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
                alert(errorMessage)
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
                    placeholder={placeholders.apellido}
                    value={form.apellido}
                    onChange={handleChange}
                    className={`inputModBedel ${errors.apellido ? 'input-error' : ''}`}
                /* Clase correcta */
                />
                <input
                    type="text"
                    name="nombre"
                    placeholder={placeholders.nombre}
                    value={form.nombre}
                    onChange={handleChange}
                    className={`inputModBedel ${errors.nombre ? 'input-error' : ''}`} /* Clase correcta */
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
                    placeholder={placeholders.idUsuario}
                    value={form.idUsuario}
                    onChange={handleChange}
                    className="inputModBedel" /* Clase correcta */
                    readOnly
                />

                <input
                    type="password"
                    name="contrasena"
                    placeholder={placeholders.contrasena}
                    value={form.contrasena}
                    onChange={handleChange}
                    className={`inputModBedel ${errors.contrasena ? 'input-error' : ''}`} /* Clase correcta */
                />

                <input
                    type="password"
                    name="confirmarContrasena"
                    placeholder={placeholders.confirmarContrasena}
                    value={form.confirmarContrasena}
                    onChange={handleChange}
                    className={`inputModBedel ${errors.confirmarContrasena ? 'input-error' : ''}`}/* Clase correcta */
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