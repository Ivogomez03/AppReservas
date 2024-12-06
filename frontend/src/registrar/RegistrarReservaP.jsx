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
        { value: 'Informatica', label: 'Informatica' },
        { value: 'Sin recursos adicionales', label: 'Sin recursos adicionales' },
    ];

    const [form, setForm] = useState({
        cantidadAlumnos: '',
        tipoAula: '',
        nombreProfesor: '',
        apellidoProfesor: '',
        nombreCatedra: '',
        correo: '',
        esporadica: false,
        periodicaAnual: tipoReserva == "Anual",
        periodicaPrimerCuatrimestre: tipoReserva == "Primer cuatrimestre",
        periodicaSegundoCuatrimestre: tipoReserva == "Segundo cuatrimestre"
    });

    const [backendErrors, setBackendErrors] = useState({
        idUsuario: '',
        contrasena: ''
    });


    const [placeholders, setPlaceholders] = useState({
        cantidadAlumnos: "Cantidad de alumnos",
        tipoAula: "Tipo de aula",
        nombreProfesor: "Nombre profesor",
        apellidoProfesor: "Apellido profesor",
        nombreCatedra: "Nombre de la cátedra, seminario o curso",
        correo: "Correo electrónico"
    });

    const [errors, setErrors] = useState({
        cantidadAlumnos: false,
        apellidoProfesor: false,
        nombreProfesor: false,
        tipoAula: false,
        nombreCatedra: false,
        correo: false
    });
    const [animationClass, setAnimationClass] = useState('');

    const [backendMessage, setBackendMessage] = useState('');

    const resetFormulario = () => {
        setForm({
            cantidadAlumnos: '',
            tipoAula: '',
            nombreProfesor: '',
            apellidoProfesor: '',
            nombreCatedra: '',
            correo: '',
            periodicaAnual: tipoReserva == "Anual",
            periodicaPrimerCuatrimestre: tipoReserva == "Primer cuatrimestre",
            periodicaSegundoCuatrimestre: tipoReserva == "Segundo cuatrimestre"
        });
        setPlaceholders({
            cantidadAlumnos: "Cantidad de alumnos",
            tipoAula: "Tipo de aula",
            nombreProfesor: "Nombre profesor",
            apellidoProfesor: "Apellido profesor",
            nombreCatedra: "Nombre de la cátedra, seminario o curso",
            correo: "Correo electrónico"
        })
        setErrors({
            cantidadAlumnos: false,
            apellidoProfesor: false,
            nombreProfesor: false,
            tipoAula: false,
            nombreCatedra: false,
            correo: false

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
        if (!form.cantidadAlumnos || form.cantidadAlumnos >= 1000) {
            newErrors.cantidadAlumnos = true;
            setPlaceholders(prev => ({ ...prev, cantidadAlumnos: "Completa la cantidad de alumnos (máximo 999)" }));
        }
        if (!form.tipoAula) {
            newErrors.tipoAula = true;
        }
        if (!form.apellidoProfesor || form.apellidoProfesor.length > 50) {
            newErrors.apellidoProfesor = true;
            setPlaceholders(prev => ({ ...prev, apellidoProfesor: "Completa el apellido del profesor (máximo 50 caracteres)." }));
        }
        if (!form.nombreProfesor || form.nombreProfesor.length > 50) {
            newErrors.nombreProfesor = true;
            setPlaceholders(prev => ({ ...prev, nombreProfesor: "Completa el nombre del profesor (máximo 50 caracteres)." }));
        }

        if (!form.correo || form.correo.length > 70) {
            newErrors.correo = true;
            setPlaceholders(prev => ({ ...prev, correo: "Completa el correo electrónico (máximo 70 caracteres)." }));
        }
        if (!form.nombreCatedra || form.nombreCatedra.length > 100) {
            newErrors.nombreCatedra = true;
            setPlaceholders(prev => ({ ...prev, nombreCatedra: "Completa el nombre de la catedra, seminario o curso (máximo 100 caracteres)." }));
        }
        if (!diasRegistrados) {
            alert("Ingrese al menos un dia")
        }
        // Actualizar el estado de errores
        setErrors(newErrors);

        // Si hay errores, detener el envío
        if (Object.values(newErrors).some(error => error)) {
            return;
        }

        try {
            console.log("Asi seria el DTO: ", { ...form, dias: diasRegistrados })
            const response = await fetch('/reserva/registrar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ ...form, dias: diasRegistrados }),

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
                                <div className='dias-registrados-info'>{dia.dia}-{dia.horaInicio}-{dia.duracion}min</div>
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
                    name="nombreProfesor"
                    placeholder={placeholders.nombreProfesor}
                    value={form.nombreProfesor}
                    onChange={handleChange}
                    className={`input-RRP ${errors.nombreProfesor ? 'input-error-RRP' : ''}`}
                />
                {errors.nombreProfesor && <span className="error-message-RRP">Completa el nombre del profesor (máximo 50 caracteres).</span>}

                <input
                    type="text"
                    name="apellidoProfesor"
                    placeholder={placeholders.apellidoProfesor}
                    value={form.apellidoProfesor}
                    onChange={handleChange}
                    className={`input-RRP ${errors.apellidoProfesor ? 'input-error-RRP' : ''}`}
                />
                {errors.apellidoProfesor && <span className="error-message-RRP">Completa el apellido del profesor (máximo 50 caracteres).</span>}

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
                    type="correo"
                    name="correo"
                    placeholder={placeholders.correo}
                    value={form.correo}
                    onChange={handleChange}
                    className={`input-RRP ${errors.correo ? 'input-error-RRP' : ''}`}
                />
                {errors.correo && <span className="error-message">Completa el correo electrónico.</span>}


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