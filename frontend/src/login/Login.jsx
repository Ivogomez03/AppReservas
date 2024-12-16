import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';

const Login = ({ resetForm }) => {
    const navigate = useNavigate();

    const goBack = (e) => {
        e.preventDefault();
        navigate(-1);
    };

    const goToBienvenidoBedel = () => {
        navigate('/login/bienvenidoBedel');
    };

    const goToBienvenidoAdmin = () => {
        navigate('/login/bienvenidoAdmin');
    };

    const [form, setForm] = useState({
        idUsuario: '',
        contrasena: ''
    });

    const [placeholders, setPlaceholders] = useState({
        idUsuario: "idUsuario",
        contrasena: "Contraseña",
    });

    const [errors, setErrors] = useState({
        idUsuario: false,
        contrasena: false,
    });

    const resetFormulario = () => {
        setForm({
            idUsuario: '',
            contrasena: '',
        });
        setPlaceholders({
            idUsuario: "idUsuario",
            contrasena: "Contraseña",
        });
        setErrors({
            idUsuario: false,
            contrasena: false,
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
            [e.target.name]: e.target.value,
        });
        setErrors({
            ...errors,
            [e.target.name]: false, // Resetea el error al cambiar el input
        });
        setPlaceholders({
            ...placeholders,
            [e.target.name]: e.target.name === 'idUsuario' ? 'idUsuario' : 'Contraseña', // Limpia el placeholder si había error
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Resetea errores al iniciar el submit
        setErrors({
            idUsuario: false,
            contrasena: false,
        });

        const newErrors = { idUsuario: false, contrasena: false };

        // Validaciones locales
        if (!form.idUsuario) {
            newErrors.idUsuario = true;
            setPlaceholders((prev) => ({ ...prev, idUsuario: "Completa el idUsuario" }));
        }
        if (!form.contrasena) {
            newErrors.contrasena = true;
            setPlaceholders((prev) => ({ ...prev, contrasena: "Completa la contraseña." }));
        }

        // Actualizar el estado de errores
        setErrors(newErrors);

        // Si hay errores, detener el envío
        if (Object.values(newErrors).some((error) => error)) {
            return;
        }

        try {
            const response = await fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(form),
            });

            const result = await response.json();

            if (result.bedel === true) {
                localStorage.setItem('idBedel', result.idUsuario);
                goToBienvenidoBedel();
                resetFormulario();
            } else if (result.admin === true) {
                localStorage.setItem('idAdmin', result.idUsuario);
                goToBienvenidoAdmin();
                resetFormulario();
            } else {
                setErrors({
                    idUsuario: true,
                    contrasena: true,
                });
                setPlaceholders({
                    idUsuario: "Usuario o contraseña incorrectos",
                    contrasena: "Usuario o contraseña incorrectos",
                });
            }
        } catch (error) {
            console.error('Error en la solicitud:', error);
            setErrors({
                idUsuario: true,
                contrasena: true,
            });
            setPlaceholders({
                idUsuario: "Error en la conexión",
                contrasena: "Error en la conexión",
            });
        }
    };

    return (
        <div className="conteiner-login">
            <img src="/reserved.png" alt="" className="shape-login" />
            <div className="sub-conteiner-login">
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

                <h1>Inicie sesión</h1>
                <input
                    type="number"
                    name="idUsuario"
                    placeholder={placeholders.idUsuario}
                    value={form.idUsuario}
                    onChange={handleChange}
                    className={`inputLogin ${errors.idUsuario ? 'input-error-login' : ''}`}
                />

                <input
                    type="password"
                    name="contrasena"
                    placeholder={placeholders.contrasena}
                    value={form.contrasena}
                    onChange={handleChange}
                    className={`inputLogin ${errors.contrasena ? 'input-error-login' : ''}`}
                />
                <button className="botonLogin" onClick={handleSubmit}>
                    Iniciar sesión
                </button>
            </div>
        </div>
    );
};

export default Login;
