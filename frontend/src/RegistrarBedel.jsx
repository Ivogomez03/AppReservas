import { useState, useEffect } from 'react'
import './RegistrarBedel.css';


const RegistrarBedel = ({ mostrar , resetForm}) => {
  const [form, setForm] = useState({
    apellido: '',
    nombre: '',
    turnoDeTrabajo: '',
    idUsuario: '',
    idAdminCreador: '1',
    contrasena: '',
    confirmarContrasena: ''
  });

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
  };

  useEffect(() => {
    if (resetForm) {
      resetForm.current = resetFormulario;
    }
  }, [resetForm]);

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
    confirmarContrasena: false
  });

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

    // Validaciones
    if (!form.apellido || form.apellido.length > 50) {
      newErrors.apellido = true;
      setPlaceholders(prev => ({ ...prev, apellido: "Completa el apellido (máximo 50 caracteres)." }));
    }
    if (!form.nombre || form.nombre.length > 50) {
      newErrors.nombre = true;
      setPlaceholders(prev => ({ ...prev, nombre: "Completa el nombre (máximo 50 caracteres)." }));
    }
    if (!form.turnoDeTrabajo || form.turnoDeTrabajo.length > 10) {
      newErrors.turnoDeTrabajo = true;
      setPlaceholders(prev => ({ ...prev, turnoDeTrabajo: "Completa el turno de trabajo (máximo 10 caracteres)." }));
    }
    if (!form.idUsuario || form.idUsuario.length > 10) {
      newErrors.idUsuario = true;
      setPlaceholders(prev => ({ ...prev, idUsuario: "Completa el identificador (máximo 10 caracteres)." }));
    }
    if (form.contrasena.length < 8) {
      newErrors.contrasena = true;
      setPlaceholders(prev => ({ ...prev, contrasena: "La contraseña debe tener al menos 8 caracteres." }));
    } else if (!/[!@#$%^&*]/.test(form.contrasena)) {
      newErrors.contrasena = true;
      setPlaceholders(prev => ({ ...prev, contrasena: "La contraseña debe contener al menos un signo especial (@#$%&*)." }));
    } else if (!/[A-Z]/.test(form.contrasena)) {
      newErrors.contrasena = true;
      setPlaceholders(prev => ({ ...prev, contrasena: "La contraseña debe contener al menos una letra mayúscula." }));
    } else if (!/\d/.test(form.contrasena)) {
      newErrors.contrasena = true;
      setPlaceholders(prev => ({ ...prev, contrasena: "La contraseña debe contener al menos un dígito." }));
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
      console.log(newErrors);
      return;
    }
    

    try {

      const response = await fetch('/admin/crear', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(form),
      });


      if (response.status === 200) {
        console.log('Bedel creado exitosamente:', response.data);
      } else {
        console.log('Hubo un error al crear el Bedel');
      }
    } catch (error) {
      console.error('Error en la solicitud:', error);
    }

  }

  return (
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
      {errors.apellido && <span className="error-message">Completa el apellido (máximo 50 caracteres).</span>}

      <input
        type="text"
        name="nombre"
        placeholder={placeholders.nombre}
        value={form.nombre}
        onChange={handleChange}
        className={`inputRegBedel ${errors.nombre ? 'input-error' : ''}`}
      />
      {errors.nombre && <span className="error-message">Completa el nombre (máximo 50 caracteres).</span>}

      <input
        type="text"
        name="turnoDeTrabajo"
        placeholder={placeholders.turnoDeTrabajo}
        value={form.turnoDeTrabajo}
        onChange={handleChange}
        className={`inputRegBedel ${errors.turnoDeTrabajo ? 'input-error' : ''}`}
      />
      {errors.turnoDeTrabajo && <span className="error-message">Completa el turno de trabajo (máximo 10 caracteres).</span>}

      <input
        type="text"
        name="idUsuario"
        placeholder={placeholders.idUsuario}
        value={form.idUsuario}
        onChange={handleChange}
        className={`inputRegBedel ${errors.idUsuario ? 'input-error' : ''}`}
      />
      {errors.idUsuario && <span className="error-message">Completa el identificador (máximo 10 caracteres).</span>}

      <input
        type="password"
        name="contrasena"
        placeholder={placeholders.contrasena}
        value={form.contrasena}
        onChange={handleChange}
        className={`inputRegBedel ${errors.contrasena ? 'input-error' : ''}`}
      />
      {errors.contrasena && <span className="error-message">La contraseña no cumple con los requisitos.</span>}

      <input
        type="password"
        name="confirmarContrasena"
        placeholder={placeholders.confirmarContrasena}
        value={form.confirmarContrasena}
        onChange={handleChange}
        className={`inputRegBedel ${errors.confirmarContrasena ? 'input-error' : ''}`}
      />
      {errors.confirmarContrasena && <span className="error-message">Las contraseñas no coinciden.</span>}

      <div className='BotonesBedel'>
        <button className='botonRegBedel' type="submit">Registrar</button>
        <button className='botonCancelar' onClick={mostrar}>Cancelar</button>
      </div>
    </form>
  );
};

export default RegistrarBedel;