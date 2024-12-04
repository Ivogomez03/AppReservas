import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './BuscarReservasDE.css';
import CancelarBedel from '../cancelar/CancelarBedel';
import Select from 'react-select';

const BuscarReservasDE = ({ resetForm }) => {
    const navigate = useNavigate();
    const [showModal, setShowModal] = useState(false);
    const [backendMessage, setBackendMessage] = useState('');

    const [form, setForm] = useState({
        dia: '',
        tipoAula: 'Todas',
        aula: ''
    });

    const [errors, setErrors] = useState({
        dia: false,
    });

    const resetFormulario = () => {
        setForm({
            dia: '',
            tipoAula: 'Todas',
            aula: ''
        });
        setErrors({
            dia: false,
        });
    };

    useEffect(() => {
        if (resetForm) {
            resetForm.current = resetFormulario;
        }
    }, [resetForm]);

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
        setErrors({
            ...errors,
            [e.target.name]: false
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const newErrors = { ...errors };

        if (!form.dia) {
            newErrors.dia = true;
        }

        setErrors(newErrors);

        if (Object.values(newErrors).some(error => error)) {
            return;
        }

        try {
            const queryParams = new URLSearchParams({
                dia: form.dia,
                tipoAula: form.tipoAula,
                aula: form.aula
            }).toString();

            const response = await fetch(`/reservas/CU10?${queryParams}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            if (response.ok) {
                const data = await response.json();
                navigate("/login/bienvenidoBedel/BuscarReservasDE/ListaReservas", { state: { reservas: data } });
                resetFormulario();
            } else {
                setBackendMessage("No se encontraron reservas para ese día.");
            }
        } catch (error) {
            setBackendMessage("Ocurrió un error en el servidor. Inténtalo de nuevo.");
        }
    };

    const mostrar = () => setShowModal(true);

    const handleCancel = () => setShowModal(false);

    const handleConfirmCancel = () => {
        setShowModal(false);
        resetFormulario();
    };

    return (
        <div className='conteiner-principal-busqueda-DE'>
            <div className="seccion-bienvenida-busqueda-DE">
                <h1>Listado de Reservas</h1>
                <p>Seleccione los criterios para obtener el listado.</p>
            </div>
            <div className="conteiner-busqueda-DE">
                <h1>Parámetros</h1>
                <form onSubmit={handleSubmit} className="form-busqueda-DE">
                    <input
                        type="date"
                        name="dia"
                        value={form.dia}
                        onChange={handleChange}
                        className={`input-DE ${errors.dia ? 'input-error-DE' : ''}`}
                    />
                    {errors.dia && <span className="error-message-DE">Seleccione un día.</span>}

                    <select
                        name="tipoAula"
                        value={form.tipoAula}
                        onChange={handleChange}
                        className="input-DE"
                    >
                        <option value="Todas">Todas</option>
                        <option value="Multimedio">Multimedio</option>
                        <option value="Informatica">Informatica</option>
                        <option value="Sin Recursos Adicionales">Sin Recursos Adicionales</option>
                    </select>

                    <input
                        type="text"
                        name="aula"
                        placeholder="Nombre del aula (opcional)"
                        value={form.aula}
                        onChange={handleChange}
                        className="input-DE"
                    />

                    <div className='BotonesBusquedaDE'>
                        <button className='botonBusquedaDE' type="submit">Buscar</button>
                        <button className='botonCancelarBusq' onClick={mostrar}>Cancelar</button>
                    </div>

                    {backendMessage && <p className="error-message-DE">{backendMessage}</p>}
                </form>
                {showModal && (
                    <CancelarBedel
                        onCancel={handleCancel}
                        onConfirm={handleConfirmCancel}
                    />
                )}
            </div>
        </div>
    );
};

export default BuscarReservasDE;
