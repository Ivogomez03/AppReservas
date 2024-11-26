import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import Select from 'react-select';
import { HashRouter, useNavigate } from 'react-router-dom';
import './ReservaClaseE.css'
import CancelarBedel from './../cancelar/CancelarBedel'

const ReservaClaseE = ({ resetForm }) => {


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


    const [form, setForm] = useState({
        horasMinutos: '',
        fechaAnio: '',
        duracion: '',
    });

    const [backendErrors, setBackendErrors] = useState({
        idUsuario: '',
        contrasena: ''
    });


    const [placeholders, setPlaceholders] = useState({
        horasMinutos: "Hora:minutos",
        fechaAnio: "Dia del año",
        duracion: "Duración",
    });

    const [errors, setErrors] = useState({
        horasMinutos: false,
        duracion: false,
        fechaAnio: false,
    });
    const [animationClass, setAnimationClass] = useState('');

    const [backendMessage, setBackendMessage] = useState('');

    const resetFormulario = () => {
        setForm({
            horasMinutos: '',
            fechaAnio: '',
            duracion: '',
        });
        setPlaceholders({
            horasMinutos: "Hora:minutos",
            fechaAnio: "Dia del año",
            duracion: "Duración",

        })
        setErrors({
            horasMinutos: false,
            duracion: false,
            fechaAnio: false,
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
        if (!form.horasMinutos) {
            newErrors.horasMinutos = true;
            setPlaceholders(prev => ({ ...prev, horasMinutos: "Completa Hora:minutos." }));
        }
        if (!form.fechaAnio) {
            newErrors.fechaAnio = true;
        }
        if (!form.duracion || form.duracion.length > 6) {
            newErrors.duracion = true;
            setPlaceholders(prev => ({ ...prev, duracion: "Completa el duracion (máximo 6 caracteres)." }));
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
            navigate('/login/RegistrarReservaEsporadica', { state: { diasRegistrados: [...diasRegistrados, form] } });
        }, 0);  // El timeout es solo para asegurarse de que el estado se haya actualizado
        console.log('Actualizados días:', diasRegistrados);

        setForm({
            horasMinutos: '',
            fechaAnio: '',
            duracion: '',
        });
    }

    return (
        <div className='conteiner-principal-RCP'>
            <div className='panel-izquierdo-RCP'>
                <h1>Reserva de clase</h1>
                <h4>Seleccione el día, el horario de la reserva y la duración de la misma</h4>
            </div>
            <form onSubmit={handleSiguiente} className='formulario-RCP'>
                <h1>Esporádica</h1>
                <input
                    type="date"
                    name="fechaAnio"
                    placeholder={placeholders.fechaAnio}
                    value={form.fechaAnio}
                    onChange={handleChange}
                    className={`input-RCP ${errors.fechaAnio ? 'input-error-RCP' : ''}`}
                />
                {errors.fechaAnio && <span className="error-message-RCP">Dia del año</span>}
                <input
                    type="time"
                    name="horasMinutos"
                    placeholder={placeholders.horasMinutos}
                    value={form.horasMinutos}
                    onChange={handleChange}
                    className={`input-RCP ${errors.horasMinutos ? 'input-error-RCP' : ''}`}
                />
                {errors.horasMinutos && <span className="error-message-RCP">Completa horas y minutos</span>}

                <input
                    type="text"
                    name="duracion"
                    placeholder={placeholders.duracion}
                    value={form.duracion}
                    onChange={handleChange}
                    className={`input-RCP ${errors.duracion ? 'input-error-RCP' : ''}`}
                />
                {errors.duracion && <span className="error-message-RCP">Completa la duracion (máximo 6 caracteres).</span>}

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