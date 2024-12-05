import { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './ModificarAulaMultimedio.css';
import CancelarBedel from './../cancelar/CancelarBedel';

const ModificarAulaSRA = () => {
    const navigate = useNavigate();
    const goBack = () => {
        navigate(-1); // Navega hacia la página anterior
    };

    const location = useLocation();
    const aulaSRA = location.state?.aula;
    console.log("El aula es ", aulaSRA);

    const [form, setForm] = useState({
        numeroDeAula: '',
        piso: '',
        capacidad: '',
        tipoPizarron: '',
        aireAcondicionado: false,
        ventilador: false
    });

    const [backendErrors, setBackendErrors] = useState({});
    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        if (aulaSRA) {
            setForm({
                numeroDeAula: aulaSRA.numeroDeAula || '',
                piso: aulaSRA.piso || '',
                capacidad: aulaSRA.capacidad || '',
                tipoPizarron: aulaSRA.tipoPizarron || '',
                aireAcondicionado: aulaSRA.aireAcondicionado || false,
                ventilador: aulaSRA.ventilador || false
            });
        }
    }, [aulaSRA]);

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.type === 'checkbox' ? e.target.checked : e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Validaciones
        if (form.capacidad <= 0) {
            setBackendErrors({ capacidad: 'La capacidad debe ser mayor que 0' });
            return;
        }
        try {
            const response = await fetch(`/modificarAula`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ ...form, tipoAula: aulaSRA.tipoAula }),
            });

            if (!response.ok) {
                const errorMessage = await response.text();
                setBackendErrors({ general: errorMessage });
                console.error('Error del servidor:', errorMessage);
                return;
            }

            const result = await response.text();
            console.log('Éxito:', result);
            alert(result);

        } catch (error) {
            console.error('Error al enviar el formulario:', error);
            setBackendErrors({ general: 'Ocurrió un error inesperado. Por favor, inténtelo de nuevo más tarde.' });
        }
    };

    return (
        <div className="containerModAulaM">
            <div className="panel-izquierdo-mod-aula-m">
                <button className="back-button" onClick={goBack}>
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        fill="currentColor"
                        width="32"
                        height="32"
                    >
                        <path d="M15.41 16.59L10.83 12l4.58-4.59L14 6l-6 6 6 6z" />
                    </svg>
                </button>
                <h1>Modificar Aula Sin Recursos Adicionales</h1>
                <h2>Por favor, ingrese los datos solicitados</h2>
            </div>
            <form onSubmit={handleSubmit} className="formularioModAulaM">
                <h2>Modificar Aula</h2>
                <input
                    type="text"
                    name="numeroDeAula"
                    value={form.numeroDeAula}
                    className="inputModAulaM"
                    placeholder="Número de aula (solo lectura)"
                    readOnly
                />
                <input
                    type="text"
                    name="piso"
                    value={form.piso}
                    className="inputModAulaM"
                    placeholder="Piso donde se ubica (solo lectura)"
                    readOnly
                />
                <input
                    type="number"
                    name="capacidad"
                    value={form.capacidad}
                    onChange={handleChange}
                    className="inputModAulaM"
                    placeholder="Capacidad (cantidad de alumnos)"
                />
                {backendErrors.capacidad && <p className="error">{backendErrors.capacidad}</p>}

                <input
                    type="text"
                    name="tipoPizarron"
                    value={form.tipoPizarron}
                    onChange={handleChange}
                    className="inputModAulaM"
                    placeholder="Tipo de pizarrón"
                />
                <div className="checkbox-container">
                    {["aireAcondicionado", "ventilador"].map((name) => (
                        <button
                            key={name}
                            type="button"
                            className={`checkbox-btn ${form[name] ? "active" : ""}`}
                            onClick={() =>
                                setForm((prevForm) => ({
                                    ...prevForm,
                                    aireAcondicionado: name === "aireAcondicionado" ? !prevForm.aireAcondicionado : false,
                                    ventilador: name === "ventilador" ? !prevForm.ventilador : false,
                                }))
                            }
                        >
                            {name.charAt(0).toUpperCase() + name.slice(1).replace(/([A-Z])/g, " $1")}
                        </button>
                    ))}
                </div>


                <div className="botonesModAulaM">
                    <button type="submit" className="botonModAulaM">Modificar</button>
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
                    onConfirm={() => navigate('/login/bienvenidoBedel/BuscarAulas/ListaAulas')}
                />
            )}
        </div>
    );
};

export default ModificarAulaSRA;
