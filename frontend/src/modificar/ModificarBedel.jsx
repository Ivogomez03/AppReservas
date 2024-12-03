import { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './ModificarBedel.css';
import CancelarBedel from './../cancelar/CancelarBedel';

const ModificarBedel = ({ resetForm }) => {
    const navigate = useNavigate();
    const location = useLocation();

    const bedel = location.state?.bedel;
    console.log(bedel);
    const [form, setForm] = useState({
        apellido: '',
        nombre: '',
        turnoDeTrabajo: '',
        idUsuario: '',
        idAdminCreador: '1',
        contrasena: '',
        confirmarContrasena: ''
    });

    const [backendErrors, setBackendErrors] = useState({
        idUsuario: '',
        contrasena: ''
    });

    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        if (bedel) {
            setForm({
                apellido: bedel.apellido || '',
                nombre: bedel.nombre || '',
                turnoDeTrabajo: bedel.turnoDeTrabajo || '',
                idUsuario: bedel.idUsuario || '',
                idAdminCreador: bedel.idAdminCreador || '1',
                contrasena: '',
                confirmarContrasena: '',
            });
        }
    }, [bedel]);


    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Validaciones y lógica de envío del formulario...

        try {
            const response = await fetch(`/bedel/CU14/modificarBedel`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(form),
            });

            if (!response.ok) {
                // Procesar mensaje de error del backend
                const errorMessage = await response.text();
                setBackendErrors({ general: errorMessage });
                console.error('Error del servidor:', errorMessage);
                return;
            }

            const result = await response.text(); // Mensaje de éxito
            console.log('Éxito:', result);
            alert(result); // O redirigir si es necesario

        } catch (error) {
            console.error('Error al enviar el formulario:', error);
            setBackendErrors({ general: 'Ocurrió un error inesperado. Por favor, inténtelo de nuevo más tarde.' });
        }
    };

    return (
        <div className="conteiner-mod-bedel">
            <div className="panel-izquierdo">
                <h1>Por favor</h1>
                <h2>Ingrese los datos solicitados</h2>
            </div>
            <form onSubmit={handleSubmit} className="formulario">
                <h2>Modificar Bedel</h2>

                <input
                    type="text"
                    name="apellido"
                    placeholder="Apellido"
                    value={form.apellido}
                    onChange={handleChange}
                />
                <input
                    type="text"
                    name="nombre"
                    placeholder="Nombre"
                    value={form.nombre}
                    onChange={handleChange}
                />
                <select
                    name="turnoDeTrabajo"
                    value={form.turnoDeTrabajo}
                    onChange={handleChange}
                >
                    <option value="Mañana">Mañana</option>
                    <option value="Tarde">Tarde</option>
                    <option value="Noche">Noche</option>
                </select>

                <input
                    type="text"
                    name="idUsuario"
                    placeholder="Identificador de usuario"
                    value={form.idUsuario}
                    onChange={handleChange}
                />

                <input
                    type="password"
                    name="contrasena"
                    placeholder="Contraseña"
                    value={form.contrasena}
                    onChange={handleChange}
                />

                <input
                    type="password"
                    name="confirmarContrasena"
                    placeholder="Confirmar contraseña"
                    value={form.confirmarContrasena}
                    onChange={handleChange}
                />

                <button type="submit">Modificar</button>
                <button onClick={() => setShowModal(true)}>Cancelar</button>
            </form>

            {showModal && (
                <CancelarBedel
                    onCancel={() => setShowModal(false)}
                    onConfirm={() => navigate('/')}
                />
            )}
        </div>
    );
};

export default ModificarBedel;