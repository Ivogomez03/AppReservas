import { useState, useEffect } from 'react'
import { HashRouter, useNavigate } from 'react-router-dom';
import './Login.css'
const Login = ({ resetForm }) => {

    const navigate = useNavigate();

    const goToBienvenidoBedel = () => {
        navigate('/login/bienvenidoBedel');
    }

    const goToBienvenidoAdmin = () => {
        navigate('/login/bienvenidoAdmin');
    }

    const [form, setForm] = useState({
        usuario: '',
        contrasena: ''
    });


    const [placeholders, setPlaceholders] = useState({
        usuario: "usuario",
        contrasena: "Contraseña",
    });

    const [errors, setErrors] = useState({
        usuario: false,
        contrasena: false,
    });

    const resetFormulario = () => {
        setForm({
            usuario: '',
            contrasena: '',

        });
        setPlaceholders({
            usuario: "usuario",
            contrasena: "Contraseña",
        })
        setErrors({
            usuario: false,
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

        if (!form.usuario) {
            newErrors.usuario = true;
            setPlaceholders(prev => ({ ...prev, usuario: "Completa el nombre de usuario" }));
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
                    usuario: true,
                    contrasena: true,
                });
            }

        } catch (error) {
            console.error('Error en la solicitud:', error);
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
                    type="number"
                    name="usuario"
                    placeholder={placeholders.usuario}
                    value={form.usuario}
                    onChange={handleChange}
                    className={`inputLogin ${errors.usuario ? 'input-error' : ''}`}
                />

                <input
                    type="password"
                    name="contrasena"
                    placeholder={placeholders.contrasena}
                    value={form.contrasena}
                    onChange={handleChange}
                    className={`inputLogin ${errors.contrasena ? 'input-error' : ''}`}
                />
                <button className='botonLogin' onClick={handleSubmit}>Iniciar sesión</button>
            </div>

        </div>
    )

}
export default Login;