import { useState, useEffect, useRef } from 'react'
import { HashRouter, useNavigate } from 'react-router-dom';
import './BuscarReservasPC.css';
import CancelarBedel from '../cancelar/CancelarBedel'
import Select from 'react-select';

const BuscarReservasPC = ({ resetForm }) => {
    const navigate = useNavigate();

    const goBack = () => {
        navigate(-1); // Navega hacia la página anterior
    };

    const onlyNumbers = (str) => /^[0-9]*$/.test(str);

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


    const [form, setForm] = useState({
        curso: '',
        año: ''
    });

    const [placeholders, setPlaceholders] = useState({
        curso: "Curso",
        año: "Año"
    });

    const [errors, setErrors] = useState({
        curso: false,

        año: false
    });


    const resetFormulario = () => {
        setForm({
            curso: '',
            año: ''
        });
        setPlaceholders({
            curso: "Curso",
            año: "Año"
        })
        setErrors({
            curso: false,
            año: false

        });

    };
    useEffect(() => {
        if (resetForm) {
            resetForm.current = resetFormulario;
        }
    }, [resetForm]);

    const handleChange = (e) => {
        const { name, value } = e.target;

        if (["año"].includes(name) && !onlyNumbers(value)) {
            if (!onlyNumbers(value)){
                return;
            }
        }
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
        if (form.curso.length > 50) {
            newErrors.curso = true;
            setPlaceholders(prev => ({ ...prev, curso: "Máximo 50 caracteres" }));
        }

        if (!form.año) {
            newErrors.año = true;
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
                curso: form.curso || "",
                año: form.año
            }).toString();
            console.log("URL construida:", `/reservas/CU18?${queryParams}`);

            const response = await fetch(`/reservas/CU18?${queryParams}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            console.log(response)
            if (response.ok) {
                const data = await response.json();
                // Redirigir a la lista de Reservas y pasar los datos con `state`
                navigate("/login/bienvenidoBedel/BuscarReservasPC/ListaReservas", { state: { reservas: data } });
                resetFormulario();
            } else {
                console.error('Error en la respuesta:', response.status);
                setBackendMessage("No se pudieron cargar las reservas.");
            }

        } catch (error) {
            console.error('Error en la solicitud:', error);
            setBackendMessage("Ocurrió un error en el servidor. Inténtalo de nuevo.");
        }
    }
    return (
        <div className='conteiner-principal-busqueda-PC'>
            <div className="seccion-bienvenida-busqueda-PC">
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
                <h1>Curso y Año</h1>
                <p>Indique los parametros de busqueda</p>

            </div>
            <div className="conteiner-busqueda-PC">
                <h1>Parámetros</h1>
                <form onSubmit={handleSubmit} className="form-busqueda-PC">
                    <input
                        type="text"
                        name="curso"
                        placeholder={placeholders.curso}
                        value={form.curso}
                        onChange={handleChange}
                        className={`input-PC ${errors.curso ? 'input-error-PC' : ''}`}
                    />
                    {errors.curso && <span className="error-message-PC">Completa el curso (máximo 50 caracteres).</span>}
                    <input
                        type="number"
                        name="año"
                        min="1900"
                        max="2100"
                        value={form.año}
                        onChange={handleChange}
                        placeholder={placeholders.año}
                        className={`input-PC ${errors.curso ? 'input-error-PC' : ''}`}
                    />
                    {errors.año && <span className="error-message-PC">Selecciona un año</span>}

                    <div className='BotonesBusquedaPC'>
                        <button className='botonBusquedaPC' type="submit">Buscar</button>
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

export default BuscarReservasPC;