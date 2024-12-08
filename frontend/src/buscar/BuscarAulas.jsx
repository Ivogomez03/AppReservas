import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './BuscarAulas.css';
import CancelarBedel from '../cancelar/CancelarBedel';

const BuscarAulas = ({ resetForm }) => {
    const navigate = useNavigate();
    const [showModal, setShowModal] = useState(false);
    const [backendMessage, setBackendMessage] = useState('');

    const goBack = () => {
        navigate(-1); // Navega hacia la página anterior
      };

    const onlyNumbers = (str) => /^[0-9]*$/.test(str);

    const [form, setForm] = useState({
        numeroDeAula: '',
        tipo: 'Todas',
        capacidad: '',
    });

    const [errors, setErrors] = useState({});

    const resetFormulario = () => {
        setForm({
            numeroDeAula: '',
            tipoAula: 'Todas',
            capacidad: '',
        });
        setErrors({});
    };

    useEffect(() => {
        if (resetForm) {
            resetForm.current = resetFormulario;
        }
    }, [resetForm]);

    const handleChange = (e) => {
        const { name, value } = e.target;

        if (["numeroDeAula","capacidad"].includes(name) && !onlyNumbers(value)) {
            if (!onlyNumbers(value)){
                return;
            }
        }
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
        
        // No hay validaciones estrictas ya que todos los campos son opcionales
        try {
            const queryParams = new URLSearchParams({
                numeroDeAula: form.numeroDeAula || '',
                tipoAula: form.tipoAula || '',
                capacidad: form.capacidad || '',
            }).toString();

            const response = await fetch(`/buscarAula?${queryParams}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            if (response.ok) {
                const data = await response.json();
                navigate("/login/bienvenidoBedel/BuscarAulas/ListaAulas", { state: { aulas: data } });
                resetFormulario();
            } else {
                setBackendMessage("No se encontraron aulas que cumplan con los criterios.");
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
        <div className='conteiner-principal-busqueda-BA'>
            <div className="seccion-bienvenida-busqueda-BA">
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
                <h1>Buscar Aulas</h1>
                <p>Seleccione uno o más criterios para buscar un aula.</p>
            </div>
            <div className="conteiner-busqueda-BA">
                <h1>Parámetros</h1>
                <form onSubmit={handleSubmit} className="form-busqueda-BA">
                    <input
                        type="text"
                        name="numeroDeAula"
                        placeholder="Número de aula (opcional)"
                        value={form.numeroDeAula}
                        onChange={handleChange}
                        className="input-BA"
                    />

                    <select
                        name="tipoAula"
                        value={form.tipoAula}
                        onChange={handleChange}
                        className="input-BA"
                    >
                        <option value="Todas">Todas</option>
                        <option value="Multimedio">Multimedio</option>
                        <option value="Informatica">Informatica</option>
                        <option value="Sin Recursos Adicionales">Sin Recursos Adicionales</option>
                    </select>

                    <input
                        type="number"
                        name="capacidad"
                        placeholder="Capacidad mínima (opcional)"
                        value={form.capacidad}
                        onChange={handleChange}
                        className="input-BA"
                    />

                    <div className='BotonesBusquedaBA'>
                        <button className='botonBusquedaBA' type="submit">Buscar</button>
                        <button className='botonCancelarBusq' onClick={mostrar}>Cancelar</button>
                    </div>

                    {backendMessage && <p className="error-message-BA">{backendMessage}</p>}
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

export default BuscarAulas;
