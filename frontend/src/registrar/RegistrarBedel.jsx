import { useState, useEffect, useRef } from 'react'
import { HashRouter, useNavigate } from 'react-router-dom';
import './RegistrarBedel.css';
import CancelarBedel from './../cancelar/CancelarBedel'
import Select from 'react-select';



const RegistrarBedel = ({ resetForm }) => {

  const navigate = useNavigate();

  const onlyLetters = (value) => /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]*$/.test(value);
  const onlyNumbers = (str) => /^[0-9]*$/.test(str);


  const goBack = (e) => {
    e.preventDefault()
    navigate('/login/bienvenidoAdmin'); // Navega hacia la página anterior
  };

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
    goBack();

    console.log("Formulario cancelado");
  };

  const options = [
    { value: 'Mañana', label: 'Mañana' },
    { value: 'Tarde', label: 'Tarde' },
    { value: 'Noche', label: 'Noche' },
  ];

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


  const [placeholders, setPlaceholders] = useState({
    apellido: "Apellido",
    nombre: "Nombre",
    turnoDeTrabajo: "Turno de trabajo",
    idUsuario: "Identificador de usuario",
    contrasena: "Contraseña",
    confirmarContrasena: "Confirmar contraseña"
  });

  const [errors, setErrors] = useState({
    apellido: false,
    nombre: false,
    turnoDeTrabajo: false,
    idUsuario: false,
    contrasena: false,
    confirmarContrasena: false
  });
  const [animationClass, setAnimationClass] = useState('');

  const [backendMessage, setBackendMessage] = useState('');

  const resetFormulario = () => {
    setForm({
      apellido: '',
      nombre: '',
      turnoDeTrabajo: '',
      idUsuario: '',
      idAdminCreador: '1',
      contrasena: '',
      confirmarContrasena: ''
    });
    setPlaceholders({
      apellido: "Apellido",
      nombre: "Nombre",
      turnoDeTrabajo: "Turno de trabajo",
      idUsuario: "Identificador de usuario",
      contrasena: "Contraseña",
      confirmarContrasena: "Confirmar contraseña"
    })
    setErrors({
      apellido: false,
      nombre: false,
      turnoDeTrabajo: false,
      idUsuario: false,
      contrasena: false,
      confirmarContrasena: false

    });
    setBackendErrors({
      idUsuario: '',
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
    const { name, value } = e.target;

    // Validar que ciertos campos acepten solo letras
    if (["nombre", "apellido"].includes(name)) {
      if (!onlyLetters(value)) {
        return; // Salir si el valor contiene caracteres no permitidos
      }
    }

    if (["idUsuario"].includes(name) && !onlyNumbers(value)) {
      if (!onlyNumbers(value)) {
        return;
      }
    }
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
    if (!form.apellido || form.apellido.length > 50) {
      newErrors.apellido = true;
      setPlaceholders(prev => ({ ...prev, apellido: "Completa el apellido (máximo 50 caracteres)." }));
    }
    if (!form.nombre || form.nombre.length > 50) {
      newErrors.nombre = true;
      setPlaceholders(prev => ({ ...prev, nombre: "Completa el nombre (máximo 50 caracteres)." }));
    }
    if (!form.turnoDeTrabajo) {
      newErrors.turnoDeTrabajo = true;
    }
    if (!form.idUsuario || form.idUsuario.length > 10) {
      newErrors.idUsuario = true;
      setPlaceholders(prev => ({ ...prev, idUsuario: "Completa el identificador (máximo 10 caracteres)." }));
    }
    if (!form.contrasena) {
      newErrors.contrasena = true;
      setPlaceholders(prev => ({ ...prev, contrasena: "Completa la contraseña." }));
    }
    if (form.contrasena !== form.confirmarContrasena) {
      newErrors.confirmarContrasena = true;
      setPlaceholders(prev => ({ ...prev, confirmarContrasena: "Las contraseñas no coinciden." }));
      setForm(prev => ({ ...prev, confirmarContrasena: "" })); // Limpiar el campo
    }

    // Actualizar el estado de errores
    setErrors(newErrors);

    // Si hay errores, detener el envío
    if (Object.values(newErrors).some(error => error)) {
      return;
    }

    try {
      const response = await fetch('/bedel/CU13', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(form),
      });
      if (!response.ok) {
        const errorMessage = await response.text();
        alert(errorMessage)

      }
      const result = await response.json(); // Usamos .json() para recibir la respuesta como objeto JSON

      console.log(result); // Ver la estructura del objeto JSON en la consola

      const newBackendErrors = { ...backendErrors };

      if (result.errorId !== "Id valida") {
        newBackendErrors.idUsuario = result.errorId;
      }
      else {
        newBackendErrors.idUsuario = "";
      }
      if (result.errorContrasena !== "Contraseña valida") {
        newBackendErrors.contrasena = result.errorContrasena;
      }
      else {
        newBackendErrors.contrasena = "";
      }

      setBackendErrors(newBackendErrors);

      // Si no hay errores en el backend, mostrar el mensaje de éxito
      if (result.errorId === "Id valida" && result.errorContrasena === "Contraseña valida") {
        setBackendMessage("Bedel creado exitosamente.");

        setAnimationClass('fade-in'); // Agregar clase de animación

        setTimeout(() => {
          setAnimationClass('fade-out'); // Iniciar fade out después de 2 segundos
          resetFormulario(); // Limpiar formulario
        }, 2000); // Esperar 2 segundos antes de hacer fade out
      }

    } catch (error) {
      console.error('Error en la solicitud:', error);
      setBackendMessage("Ocurrió un error en el servidor. Inténtalo de nuevo.");
    }
  }

  return (
    <div className='conteiner-reg-bedel'>
      <div className='panel-izquierdo'>
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

        <h1>Por favor</h1>
        <h2>Ingrese los datos solicitados</h2>
      </div>
      <form onSubmit={handleSubmit} className='formulario'>
        <h2>Registrar Bedel</h2>

        <input
          type="text"
          name="apellido"
          placeholder={placeholders.apellido}
          value={form.apellido}
          onChange={handleChange}
          className={`inputRegBedel ${errors.apellido ? 'input-error' : ''}`}
        />

        <input
          type="text"
          name="nombre"
          placeholder={placeholders.nombre}
          value={form.nombre}
          onChange={handleChange}
          className={`inputRegBedel ${errors.nombre ? 'input-error' : ''}`}
        />

        <select
          name="turnoDeTrabajo"
          value={form.turnoDeTrabajo}
          onChange={handleChange}
          className={`selectRegBedel ${errors.turnoDeTrabajo ? 'select-error' : ''}`}
        >
          <option value="" disabled>Selecciona un turno</option>
          {options.map((option) => (
            <option key={option.value} value={option.value}>{option.label}</option>
          ))}
        </select>

        <input
          type="text"
          name="idUsuario"
          placeholder={placeholders.idUsuario}
          value={form.idUsuario}
          onChange={handleChange}
          className={`inputRegBedel ${errors.idUsuario ? 'input-error' : ''}`}
        />
        {backendErrors.idUsuario && <span className="error-message">{backendErrors.idUsuario}</span>}

        <input
          type="password"
          name="contrasena"
          placeholder={placeholders.contrasena}
          value={form.contrasena}
          onChange={handleChange}
          className={`inputRegBedel ${errors.contrasena ? 'input-error' : ''}`}
        />
        {backendErrors.contrasena && <span className="error-message">{backendErrors.contrasena}</span>}

        <input
          type="password"
          name="confirmarContrasena"
          placeholder={placeholders.confirmarContrasena}
          value={form.confirmarContrasena}
          onChange={handleChange}
          className={`inputRegBedel ${errors.confirmarContrasena ? 'input-error' : ''}`}
        />

        <div className='BotonesBedel'>
          <button className='botonRegBedel' type="submit">Registrar</button>
          <button className='botonCancelar' onClick={mostrar}>Cancelar</button>
        </div>
        {backendMessage == "Bedel creado exitosamente." && <div className={`backend-message-exito ${animationClass}`}>{backendMessage}</div>}
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

export default RegistrarBedel;