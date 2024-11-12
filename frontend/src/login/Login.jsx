import { useState, useEffect } from 'react'
import { BrowserRouter as Router, Route, Routes, useNavigate } from 'react-router-dom';
import './Login.css'
const Login = ({ resetForm }) => {
    const navigate = useNavigate();
    const [form, setForm] = useState({
        nombre: '',
        contrasena: ''
    });

    const [backendErrors, setBackendErrors] = useState({
        nombre: '',
        contrasena: ''
    });


    const [placeholders, setPlaceholders] = useState({
        nombre: "Nombre",
        contrasena: "Contraseña",
    });

    const [errors, setErrors] = useState({
        nombre: false,
        contrasena: false,
    });


    const [backendMessage, setBackendMessage] = useState('');

    const resetFormulario = () => {
        setForm({
            nombre: '',
            contrasena: '',

        });
        setPlaceholders({
            nombre: "Nombre",
            contrasena: "Contraseña",
        })
        setErrors({
            nombre: false,
            contrasena: false,
        });
        setBackendErrors({
            nombre: '',
            contrasena: ''
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

        if (!form.nombre || form.nombre.length > 50) {
            newErrors.nombre = true;
            setPlaceholders(prev => ({ ...prev, nombre: "Completa el nombre (máximo 50 caracteres)." }));
        }
        if (!form.contrasena) {
            newErrors.contrasena = true;
            setPlaceholders(prev => ({ ...prev, contrasena: "Completa la contraseña." }));
        }

        // Actualizar el estado de errores
        setErrors(newErrors);

        // Si hay errores, detener el envío
        if (Object.values(newErrors).some(error => error)) {
            return;
        }

        try {
            const response = await fetch('/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(form),
            });

            const result = await response.json(); // Usamos .json() para recibir la respuesta como objeto JSON
            console.log(result); // Ver la estructura del objeto JSON en la consola

            const newBackendErrors = { ...backendErrors };

            if (result.errorNombre !== "Id valida") {
                newBackendErrors.nombre = result.errorNombre;
            }
            if (result.errorContrasena !== "Contraseña valida") {
                newBackendErrors.contrasena = result.errorContrasena;
            }

            setBackendErrors(newBackendErrors);

            // Si no hay errores en el backend, mostrar el mensaje de éxito
            if (result.errorNombre === "Nombre valido" && result.errorContrasena === "Contraseña valida") {

                setTimeout(() => {

                    resetFormulario(); // Limpiar formulario
                }, 2000); // Esperar 2 segundos antes de hacer fade out
            }

        } catch (error) {
            console.error('Error en la solicitud:', error);
            setBackendMessage("Ocurrió un error en el servidor. Inténtalo de nuevo.");
        }
    }
    return (
        <div className='conteiner'>

            <img src="/reserved.png" alt="" className='shape' />
            <div className='sub-conteiner'>
                <h1>
                    Inicie sesión
                </h1>
                <input
                    type="text"
                    name="nombre"
                    placeholder={placeholders.nombre}
                    value={form.nombre}
                    onChange={handleChange}
                    className={`inputLogin ${errors.nombre ? 'input-error' : ''}`}
                />
                {errors.nombre && <span className="error-message">Completa el nombre (máximo 50 caracteres).</span>}
                <input
                    type="password"
                    name="contrasena"
                    placeholder={placeholders.contrasena}
                    value={form.contrasena}
                    onChange={handleChange}
                    className={`inputLogin ${errors.contrasena ? 'input-error' : ''}`}
                />
                {errors.contrasena && <span className="error-message">Completa la contraseña.</span>}
                {backendErrors.contrasena && <span className="error-message">{backendErrors.contrasena}</span>}
                <button className='botonLogin' onClick={handleSubmit}>Iniciar sesión</button>
            </div>

        </div>
    )

}
export default Login;