import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import Select from 'react-select';
import { HashRouter, useNavigate } from 'react-router-dom';
import './ReservaClaseE.css'
import CancelarBedel from './../cancelar/CancelarBedel'

const ReservaClaseE = ({ resetForm }) => {

    const goBack = () => {
        navigate(-1); // Navega hacia la página anterior
    };

    const navigate = useNavigate();

    const location = useLocation();

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
    console.log(location.state?.diasRegistrados || [])
    const [diasRegistrados, setDiasRegistrados] = useState(location.state?.diasRegistrados || []);

    const optionsDuracion = [
        { value: 30 , label: '0:30'},
        { value: 60 , label: '1:00'},
        { value: 90 , label: '1:30'},
        { value: 120 , label: '2:00'},
        { value: 150 , label: '2:30'},
        { value: 180 , label: '3:00'},
        { value: 210 , label: '3:30'},
        { value: 240 , label: '4:00'},
    ];


    const [form, setForm] = useState({
        horaInicio: '',
        fecha: '',
        duracion: '',
    });

    const [backendErrors, setBackendErrors] = useState({
        idUsuario: '',
        contrasena: ''
    });


    const [placeholders, setPlaceholders] = useState({
        horaInicio: "Hora:minutos",
        fecha: "Dia del año",
        duracion: "Duración",
    });

    const [errors, setErrors] = useState({
        horaInicio: false,
        duracion: false,
        fecha: false,
    });
    const [animationClass, setAnimationClass] = useState('');

    const [backendMessage, setBackendMessage] = useState('');

    const resetFormulario = () => {
        setForm({
            horaInicio: '',
            fecha: '',
            duracion: '',
        });
        setPlaceholders({
            horaInicio: "Hora:minutos",
            fecha: "Dia del año",
            duracion: "Duración",

        })
        setErrors({
            horaInicio: false,
            duracion: false,
            fecha: false,
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


    const handleSiguiente = async (e) => {
        e.preventDefault();
        const newErrors = { ...errors };

        // Validaciones locales
        if (!form.horaInicio) {
            newErrors.horaInicio = true;
            setPlaceholders(prev => ({ ...prev, horaInicio: "Completa Hora:minutos." }));
        }
        if (!form.fecha) {
            newErrors.fecha = true;
        }
        if (!form.duracion || form.duracion.length > 6) {
            newErrors.duracion = true;
            setPlaceholders(prev => ({ ...prev, duracion: "Completa la duracion (máximo 6 digitos) ." }));
        }
        if (form.duracion % 30 != 0) {
            newErrors.duracion = true;
            setPlaceholders(prev => ({ ...prev, duracion: "La duración debe ser multiplo de 30 minutos" }));
        }
        // Actualizar el estado de errores
        setErrors(newErrors);

        // Si hay errores, detener el envío
        if (Object.values(newErrors).some(error => error)) {
            return;
        }

        setDiasRegistrados(prevDias => {
            const nuevoDia = { ...form };
            console.log("Nuevo dia", nuevoDia)
            console.log("PrevDias", ...prevDias)
            return [...prevDias, nuevoDia];  // Agregar el nuevo día al arreglo
        });
        // Esperamos a que el estado se actualice
        setTimeout(() => {
            // Ahora, hacemos la navegación pasando los días actualizados a la siguiente pantalla
            navigate('/login/RegistrarReservaEsporadica', { state: { dia: [...diasRegistrados, form] } });
        }, 0);  // El timeout es solo para asegurarse de que el estado se haya actualizado
        console.log('Actualizados días:', diasRegistrados);



        setForm({
            horaInicio: '',
            fecha: '',
            duracion: '',
        });
    }

    return (
        <div className='conteiner-principal-RCP'>
            <div className='panel-izquierdo-RCP'>
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

                <h1>Reserva de clase</h1>
                <h4>Seleccione el día, el horario de la reserva y la duración de la misma</h4>
            </div>
            <form onSubmit={handleSiguiente} className='formulario-RCP'>
                <h1>Esporádica</h1>
                <input
                    type="date"
                    name="fecha"
                    placeholder={placeholders.fecha}
                    value={form.fecha}
                    onChange={handleChange}
                    className={`input-RCP ${errors.fecha ? 'input-error-RCP' : ''}`}
                />
                <input
                    type="time"
                    name="horaInicio"
                    placeholder={placeholders.horaInicio}
                    value={form.horaInicio}
                    onChange={handleChange}
                    className={`input-RCP ${errors.horaInicio ? 'input-error-RCP' : ''}`}
                />

                <select
                    name="duracion"
                    value={form.duracion}
                    onChange={handleChange}
                    className={`select-RCP ${errors.duracion ? 'select-error-RCP' : ''}`}
                >
                    <option value="" disabled>Duración</option>
                    {optionsDuracion.map((option) => (
                        <option key={option.value} value={option.value}>{option.label}</option>
                    ))}
                </select>

                <div className='botones-RCP'>
                    <button className='botonRCP' type="submit">Siguiente</button>
                    <button className='botonCancelar-RCP' onClick={mostrar}>Cancelar</button>
                </div>

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

export default ReservaClaseE;