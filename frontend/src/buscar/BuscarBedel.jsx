import { useState, useEffect, useRef } from 'react'
import { HashRouter, useNavigate } from 'react-router-dom';
import './BuscarBedel.css';
import CancelarBedel from './../cancelar/CancelarBedel'
import Select from 'react-select';

const BuscarBedel = ({ resetForm }) => {
    const navigate = useNavigate();

    const goBack = () => {
        navigate(-1); // Navega hacia la página anterior
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
        { value: 'Mañana', label: 'Mañana' },
        { value: 'Tarde', label: 'Tarde' },
        { value: 'Noche', label: 'Noche' },
        { value: null, label: 'Todos' }
    ];

    const [form, setForm] = useState({
        apellido: '',
        turno: ''
    });

    const [placeholders, setPlaceholders] = useState({
        apellido: "Apellido",
        turnoDeTrabajo: "Turno de trabajo"
    });

    const [errors, setErrors] = useState({
        apellido: false,

        turno: false
    });


    const resetFormulario = () => {
        setForm({
            apellido: '',
            turno: ''
        });
        setPlaceholders({
            apellido: "Apellido",
            turnoDeTrabajo: "Turno de trabajo"
        })
        setErrors({
            apellido: false,
            turno: false

        });

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
        if (form.apellido.length > 50) {
            newErrors.apellido = true;
            setPlaceholders(prev => ({ ...prev, apellido: "Máximo 50 caracteres" }));
        }

        if (!form.turno) {
            newErrors.turno = true;
        }

        // Actualizar el estado de errores
        setErrors(newErrors);

        // Si hay errores, detener el envío
        if (Object.values(newErrors).some(error => error)) {
            return;
        }

        try {
            // Construir los parámetros para la URL
            const queryParams = new URLSearchParams({
                apellido: form.apellido || "",
                turno: form.turno
            }).toString();
            console.log("URL construida:", `/bedel/CU16?${queryParams}`);

            const response = await fetch(`/bedel/CU16?${queryParams}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            console.log(response)
            if (response.ok) {
                const data = await response.json();
                // Redirigir a la lista de bedeles y pasar los datos con `state`
                navigate("/login/bienvenidoAdmin/BuscarBedel/ListaBedeles", { state: { bedeles: data } });
                resetFormulario();
            } else {
                console.error('Error en la respuesta:', response.status);
                setBackendMessage("No se pudieron cargar los bedeles.");
            }

        } catch (error) {
            console.error('Error en la solicitud:', error);
            setBackendMessage("Ocurrió un error en el servidor. Inténtalo de nuevo.");
        }
    }
    return (
        <div className='conteiner-principal-busqueda-bedel'>
            <div className="seccion-bienvenida-busqueda-bedel">
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
                <h1>Busqueda de bedel</h1>
                <p>Indique los parametros de busqueda</p>

            </div>
            <div className="conteiner-busqueda-bedel">
                <h1>Parámetros</h1>
                <form onSubmit={handleSubmit} className="form-busqueda-bedel">
                    <input
                        type="text"
                        name="apellido"
                        placeholder={placeholders.apellido}
                        value={form.apellido}
                        onChange={handleChange}
                        className={`input-BB ${errors.apellido ? 'input-error-BB' : ''}`}
                    />
                    {errors.apellido && <span className="error-message-BB">Completa el apellido (máximo 50 caracteres).</span>}
                    <select
                        name="turno"
                        value={form.turno}
                        onChange={handleChange}
                        className={`selectTurnBedel ${errors.turno ? 'select-error' : ''}`}
                    >
                        <option value="" disabled>Selecciona un turno</option>
                        {options.map((option) => (
                            <option key={option.value} value={option.value}>{option.label}</option>
                        ))}
                    </select>
                    {errors.turno && <span className="error-message-BB">Selecciona un turno de trabajo.</span>}

                    <div className='BotonesBusquedaBedel'>
                        <button className='botonBusquedaBedel' type="submit">Buscar</button>
                        <button className='botonCancelarBusq' onClick={mostrar}>Cancelar</button>
                    </div>

                </form>
                {showModal && (
                    <CancelarBedel
                        onCancel={handleCancel}
                        onConfirm={handleConfirmCancel}
                    />
                )}


            </div>
        </div>
    )
}

export default BuscarBedel;