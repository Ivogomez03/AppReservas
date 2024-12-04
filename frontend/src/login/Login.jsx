import { useState, useEffect } from 'react'
import { HashRouter, useNavigate } from 'react-router-dom';
import './Login.css'
const Login = ({ resetForm }) => {

    const navigate = useNavigate();

    const goBack = () => {
        navigate(-1); // Navega hacia la página anterior
      };

    const goToBienvenidoBedel = () => {
        navigate('/login/bienvenidoBedel');
    }

    const goToBienvenidoAdmin = () => {
        navigate('/login/bienvenidoAdmin');
    }

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
        })
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

        if (!form.idUsuario) {
            newErrors.idUsuario = true;
            setPlaceholders(prev => ({ ...prev, idUsuario: "Completa el nombre de idUsuario" }));
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
            const response = await fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(form),
            });

            const result = await response.json(); // Usamos .json() para recibir la respuesta como objeto JSON
            console.log(result); // Ver la estructura del objeto JSON en la consola

            if (result.bedel == true) {
                goToBienvenidoBedel();
                resetFormulario();
            }
            else if (result.admin == true) {

                goToBienvenidoAdmin();
                resetFormulario();

            }
            else {
                setErrors({
                    idUsuario: true,
                    contrasena: true,
                });
            }

        } catch (error) {
            console.error('Error en la solicitud:', error);
        }
    }
    return (
        <div className='conteiner-login'>

            <img src="/reserved.png" alt="" className='shape-login' />
            <div className='sub-conteiner-login'>
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
                
                <h1>
                    Inicie sesión
                </h1>
                <input
                    type="number"
                    name="idUsuario"
                    placeholder={placeholders.idUsuario}
                    value={form.idUsuario}
                    onChange={handleChange}
                    className={`inputLogin${errors.idUsuario ? 'input-error-login' : ''}`}
                />

                <input
                    type="password"
                    name="contrasena"
                    placeholder={placeholders.contrasena}
                    value={form.contrasena}
                    onChange={handleChange}
                    className={`inputLogin ${errors.contrasena ? 'input-error-login' : ''}`}
                />
                <button className='botonLogin' onClick={goToBienvenidoBedel}>Iniciar sesión</button>
            </div>

        </div>
    )

}
export default Login;