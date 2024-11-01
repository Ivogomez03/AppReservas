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
    if (form.contrasena != form.confirmarContrasena) {
      newErrors.confirmarContrasena = true;
      setPlaceholders(prev => ({ ...prev, confirmarContrasena: "Las contraseñas no coinciden" }));
      // Limpiar el campo de confirmarContraseña
      setForm(prev => ({ ...prev, confirmarContrasena: "" }));
    }

    // Si hay errores, actualizar el estado de errores y detener el envío
    if (newErrors.apellido || newErrors.nombre || newErrors.turnoDeTrabajo || newErrors.confirmarContrasena) {
      setErrors(newErrors);
      console.log(newErrors)
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

      <input
        className={`inputRegBedel ${errors.nombre ? 'input-error' : ''}`}
        type="text"
        name="nombre"
        placeholder={placeholders.nombre}
        value={form.nombre}
        onChange={handleChange}
      />

      <input
        className={`inputRegBedel ${errors.turnoDeTrabajo ? 'input-error' : ''}`}
        type="text"
        name="turnoDeTrabajo"
        placeholder={placeholders.turnoDeTrabajo}
        value={form.turnoDeTrabajo}
        onChange={handleChange}
      />

      <input
        className={`inputRegBedel ${errors.idUsuario ? 'input-error' : ''}`}
        type="text"
        name="idUsuario"
        placeholder={placeholders.idUsuario}
        value={form.idUsuario}
        onChange={handleChange}
      />

      <input
        className='inputRegBedel'
        type="password"
        name="contrasena"
        placeholder={placeholders.contrasena}
        value={form.contrasena}
        onChange={handleChange}
      />

      <input
        className={`inputRegBedel ${errors.confirmarContrasena ? 'input-error' : ''}`}
        type="password"
        name="confirmarContrasena"
        placeholder={placeholders.confirmarContrasena}
        value={form.confirmarContrasena}
        onChange={handleChange}
      />

      <div className='BotonesBedel'>

        <button className='botonRegBedel' type="submit">Registrar</button>
        <button className='botonCancelar' onClick={mostrar}>Cancelar</button>

      </div>

    </form>
  );
};

export default RegistrarBedel;