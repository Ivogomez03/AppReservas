import { useState, useEffect } from 'react'
import './Registrarreserva.css';
import Select from 'react-select';



const RegistrarReserva = ({ mostrar, resetForm }) => {

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
        <form onSubmit={handleSubmit} className='formulario'>
            <h2>Registrar Reserva</h2>
            <input
                type="text"
                name="cantidadAlumnos"
                placeholder={placeholders.cantidadAlumnos}
                value={form.cantidadAlumnos}
                onChange={handleChange}
                className={`inputRegReserva ${errors.cantidadAlumnos ? 'input-error' : ''}`}
            />
            {errors.cantidadAlumnos && <span className="error-message">Completa la cantidad de alumnos.</span>}
            <select
                name="tipoAula"
                value={form.tipoAula}
                onChange={handleChange}
                className={`selectRegReserva ${errors.tipoAula ? 'select-error' : ''}`}
            >
                <option value="" disabled>Selecciona un Aula</option>
                {options.map((option) => (
                    <option key={option.value} value={option.value}>{option.label}</option>
                ))}
            </select>
            {errors.tipoAula && <span className="error-message">Selecciona un Aula.</span>}

            <input
                type="text"
                name="nombre"
                placeholder={placeholders.nombre}
                value={form.nombre}
                onChange={handleChange}
                className={`inputRegReserva ${errors.nombre ? 'input-error' : ''}`}
            />
            {errors.nombre && <span className="error-message">Completa el nombre (máximo 50 caracteres).</span>}

            <input
                type="text"
                name="apellido"
                placeholder={placeholders.apellido}
                value={form.apellido}
                onChange={handleChange}
                className={`inputRegReserva ${errors.apellido ? 'input-error' : ''}`}
            />
            {errors.apellido && <span className="error-message">Completa el apellido (máximo 50 caracteres).</span>}

            <input
                type="text"
                name="nombreCatedra"
                placeholder={placeholders.nombreCatedra}
                value={form.nombreCatedra}
                onChange={handleChange}
                className={`inputRegReserva ${errors.nombreCatedra ? 'input-error' : ''}`}
            />
            {errors.nombreCatedra && <span className="error-message">Completa el nombre de la catedra, seminario o curso.</span>}

            <input
                type="email"
                name="email"
                placeholder={placeholders.email}
                value={form.email}
                onChange={handleChange}
                className={`inputRegreserva ${errors.email ? 'input-error' : ''}`}
            />
            {errors.email && <span className="error-message">Completa el correo electrónico.</span>}


            <div className='BotonesReserva'>
                <button className='botonRegReserva' type="submit">Registrar</button>
                <button className='botonCancelar' onClick={mostrar}>Cancelar</button>
            </div>
            {backendMessage == "Reserva creada exitosamente." && <div className={`backend-message-exito ${animationClass}`}>{backendMessage}</div>}
        </form>



    );
};

export default RegistrarReserva;