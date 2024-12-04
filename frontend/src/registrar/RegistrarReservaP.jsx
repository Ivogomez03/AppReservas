import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { HashRouter, useNavigate } from 'react-router-dom';
import Select from 'react-select';
import './RegistrarReservaP.css'


import CancelarBedel from './../cancelar/CancelarBedel'
const RegistrarReservaP = ({ resetForm }) => {
    

    const navigate = useNavigate();
    const goBack = () => {
        navigate(-1); // Navega hacia la página anterior
      };

    const location = useLocation();
    const [diasRegistrados, setDiasRegistrados] = useState(location.state?.diasRegistrados || []);

    const [tipoReserva, setTipoReserva] = useState(location.state?.tipoReserva || 'Ninguno');



    useEffect(() => {
        if (location.state?.tipoReserva) {
            console.log("Useeffect", location.state)
            setTipoReserva(location.state.tipoReserva); // Actualiza tipoReserva cuando cambie el estado
        }
    }, [location.state]);





    console.log('Días recibidos:', diasRegistrados);
    const agregarDia = () => {
        navigate('/login/ReservaClaseP', { state: { diasRegistrados, tipoReserva } });
    };
    const handleDeleteDia = (index) => {
        const updatedDias = diasRegistrados.filter((_, i) => i !== index);  // Elimina el elemento del arreglo
        setDiasRegistrados(updatedDias);  // Actualiza el estado
    };
    const [showModal, setShowModal] = useState(false);  // Estado para controlar el modal
    const mostrar = () => {
        setShowModal(true);
    }

    const handleCancel = () => {
        setShowModal(false);  // Cierra el modal sin hacer nada
    };

    // Función cuando se confirma la cancelación
    const handleConfirmCancel = () => {
        setShowModal(false);  // Cierra el modal
        resetFormulario();

        console.log("Formulario cancelado");
    };



    const options = [
        { value: 'Multimedio', label: 'Multimedio' },
        { value: 'Informática', label: 'Informática' },
        { value: 'Sin recursos adicionales', label: 'Sin recursos adicionales' },
    ];

    const [form, setForm] = useState({
        cantidadAlumnos: '',
        tipoAula: '',
        nombre: '',
        apellido: '',
        nombreCatedra: '',
        email: ''
    });

    const [backendErrors, setBackendErrors] = useState({
        idUsuario: '',
        contrasena: ''
    });


    const [placeholders, setPlaceholders] = useState({
        cantidadAlumnos: "Cantidad de alumnos",
        tipoAula: "Tipo de aula",
        nombre: "Nombre",
        apellido: "Apellido",
        nombreCatedra: "Nombre de la cátedra, seminario o curso",
        email: "Correo electrónico"
    });

    const [errors, setErrors] = useState({
        cantidadAlumnos: false,
        apellido: false,
        nombre: false,
        tipoAula: false,
        nombreCatedra: false,
        email: false
    });
    const [animationClass, setAnimationClass] = useState('');

    const [backendMessage, setBackendMessage] = useState('');

    const resetFormulario = () => {
        setForm({
            cantidadAlumnos: '',
            tipoAula: '',
            nombre: '',
            apellido: '',
            nombreCatedra: '',
            email: ''
        });
        setPlaceholders({
            cantidadAlumnos: "Cantidad de alumnos",
            tipoAula: "Tipo de aula",
            nombre: "Nombre",
            apellido: "Apellido",
            nombreCatedra: "Nombre de la cátedra, seminario o curso",
            email: "Correo electrónico"
        })
        setErrors({
            cantidadAlumnos: false,
            apellido: false,
            nombre: false,
            tipoAula: false,
            nombreCatedra: false,
            email: false

        });
        setBackendMessage('');
    };
    useEffect(() => {
        if (resetForm) {
            resetForm.current = resetFormulario;
        }
    }, [resetForm]);

    const handleChange = (e) => {
        console.log({ ...form })
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
        setErrors({
            ...errors,
            [e.target.name]: false // Resetea el estado de error al cambiar el input
        });
    };


    const handleSubmit = async (e) => {
        e.preventDefault();
        const newErrors = { ...errors };

        // Validaciones locales
        if (!form.cantidadAlumnos) {
            newErrors.cantidadAlumnos = true;
            setPlaceholders(prev => ({ ...prev, cantidadAlumnos: "Completa la cantidad de alumnos." }));
        }
        if (!form.tipoAula) {
            newErrors.tipoAula = true;
        }
        if (!form.apellido || form.apellido.length > 50) {
            newErrors.apellido = true;
            setPlaceholders(prev => ({ ...prev, apellido: "Completa el apellido (máximo 50 caracteres)." }));
        }
        if (!form.nombre || form.nombre.length > 50) {
            newErrors.nombre = true;
            setPlaceholders(prev => ({ ...prev, nombre: "Completa el nombre (máximo 50 caracteres)." }));
        }

        if (!form.email || form.email.length > 70) {
            newErrors.email = true;
            setPlaceholders(prev => ({ ...prev, email: "Completa el correo electrónico (máximo 70 caracteres)." }));
        }
        if (!form.nombreCatedra || form.nombreCatedra.length > 100) {
            newErrors.nombreCatedra = true;
            setPlaceholders(prev => ({ ...prev, nombreCatedra: "Completa el nombre de la catedra, seminario o curso (máximo 100 caracteres)." }));
        }
        if (!diasRegistrados) {
            //logica
        }
        // Actualizar el estado de errores
        setErrors(newErrors);

        // Si hay errores, detener el envío
        if (Object.values(newErrors).some(error => error)) {
            return;
        }

        try {
            const response = await fetch('/reserva/CU13', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(form),
            });

            const result = await response.json(); // Usamos .json() para recibir la respuesta como objeto JSON
            console.log(result); // Ver la estructura del objeto JSON en la consola

            const newBackendErrors = { ...backendErrors };

            setBackendErrors(newBackendErrors);

            // Si no hay errores en el backend, mostrar el mensaje de éxito
            if (!result.error) {
                setBackendMessage("Reserva creada exitosamente.");

                setAnimationClass('fade-in'); // Agregar clase de animación

                setTimeout(() => {
                    setAnimationClass('fade-out'); // Iniciar fade out después de 2 segundos
                    resetFormulario(); // Limpiar formulario
                }, 2000); // Esperar 2 segundos antes de hacer fade out
            }

        } catch (error) {
            console.error('Error en la solicitud:', error);
            setBackendMessage("Ocurrió un error en el servidor. Inténtalo de nuevo.");
        }
    }

    return (
        <div className='conteiner-principal-RRP'>
            <div className='panel-izquierdo-RRP'>
                <div className='panel-izquierdo-arriba-RRP'>
                <button className="back-button" onClick={goBack}>
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
                    <h1>Período</h1>
                    <h4>Ingrese los datos solicitados</h4>
                </div>
                <div className='panel-izquierdo-abajo-RRP'>
                    <h1>{tipoReserva}</h1>
                    <button className='botonRRP' onClick={agregarDia}>Agregar Día</button>
                    <div className="dias-registrados">
                        {diasRegistrados.map((dia, index) => (
                            <div key={index} className="dia-registrado">
                                <div className='dias-registrados-info'>{dia.diaSemana}-{dia.horasMinutos}-{dia.duracion}min</div>
                                <button
                                    className="boton-eliminar"
                                    onClick={() => handleDeleteDia(index)}
                                >
                                    <img src="/eliminar.png" alt="Eliminar" />
                                </button>
                            </div>
                        ))}
                    </div>

                </div>
            </div>
            <form onSubmit={handleSubmit} className='formulario-RRP'>
                <h2>Registrar Reserva</h2>
                <input
                    type="text"
                    name="cantidadAlumnos"
                    placeholder={placeholders.cantidadAlumnos}
                    value={form.cantidadAlumnos}
                    onChange={handleChange}
                    className={`input-RRP ${errors.cantidadAlumnos ? 'input-error-RRP' : ''}`}
                />
                {errors.cantidadAlumnos && <span className="error-message-RRP">Completa la cantidad de alumnos.</span>}
                <select
                    name="tipoAula"
                    value={form.tipoAula}
                    onChange={handleChange}
                    className={`select-RRP ${errors.tipoAula ? 'select-error-RRP' : ''}`}
                >
                    <option value="" disabled>Selecciona un Aula</option>
                    {options.map((option) => (
                        <option key={option.value} value={option.value}>{option.label}</option>
                    ))}
                </select>
                {errors.tipoAula && <span className="error-message-RRP">Selecciona un Aula.</span>}

                <input
                    type="text"
                    name="nombre"
                    placeholder={placeholders.nombre}
                    value={form.nombre}
                    onChange={handleChange}
                    className={`input-RRP ${errors.nombre ? 'input-error-RRP' : ''}`}
                />
                {errors.nombre && <span className="error-message-RRP">Completa el nombre (máximo 50 caracteres).</span>}

                <input
                    type="text"
                    name="apellido"
                    placeholder={placeholders.apellido}
                    value={form.apellido}
                    onChange={handleChange}
                    className={`input-RRP ${errors.apellido ? 'input-error-RRP' : ''}`}
                />
                {errors.apellido && <span className="error-message-RRP">Completa el apellido (máximo 50 caracteres).</span>}

                <input
                    type="text"
                    name="nombreCatedra"
                    placeholder={placeholders.nombreCatedra}
                    value={form.nombreCatedra}
                    onChange={handleChange}
                    className={`input-RRP ${errors.nombreCatedra ? 'input-error-RRP' : ''}`}
                />
                {errors.nombreCatedra && <span className="error-message-RRP">Completa el nombre de la catedra, seminario o curso.</span>}

                <input
                    type="email"
                    name="email"
                    placeholder={placeholders.email}
                    value={form.email}
                    onChange={handleChange}
                    className={`input-RRP ${errors.email ? 'input-error-RRP' : ''}`}
                />
                {errors.email && <span className="error-message">Completa el correo electrónico.</span>}


                <div className='botones-RRP'>
                    <button className='botonRRP' type="submit">Registrar</button>
                    <button className='botonCancelar-RRP' onClick={mostrar}>Cancelar</button>
                </div>
                {backendMessage == "Reserva creada exitosamente." && <div className={`backend-message-exito ${animationClass}`}>{backendMessage}</div>}
            </form>
            {showModal && (
                <CancelarBedel
                    onCancel={handleCancel}
                    onConfirm={handleConfirmCancel}
                />
            )}
        </div>


    );
};

export default RegistrarReservaP;