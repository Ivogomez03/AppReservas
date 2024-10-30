import { useState } from 'react'
import './RegistrarBedel.css';


const RegistrarBedel = ({ mostrar }) => {
  const [form, setForm] = useState({
    apellido: '',
    nombre: '',
    turno: '',
    identificador: '',
    contraseña: '',
    confirmarContraseña: ''
  });

  const [placeholders, setPlaceholders] = useState({
    apellido: "Apellido",
    nombre: "Nombre",
    turno: "Turno de trabajo",
    identificador: "Identificador de usuario",
    contraseña: "Contraseña",
    confirmarContraseña: "Confirmar contraseña"
  });

  const [errors, setErrors] = useState({
    apellido: false,
    nombre: false,
    turno: false,
    identificador: false,
    confirmarContraseña: false
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

    if (!form.turno || form.turno.length > 10) {
      newErrors.turno = true;
      setPlaceholders(prev => ({ ...prev, turno: "Completa el turno de trabajo (máximo 10 caracteres)." }));
    }
    if (!form.identificador || form.identificador.length > 10) {
      newErrors.identificador = true;
      setPlaceholders(prev => ({ ...prev, identificador: "Completa el identificador (máximo 10 caracteres)." }));
    }
    if (form.contraseña != form.confirmarContraseña) {
      newErrors.confirmarContraseña = true;
      setPlaceholders(prev => ({ ...prev, confirmarContraseña: "Las contraseñas no coinciden" }));
      // Limpiar el campo de confirmarContraseña
      setForm(prev => ({ ...prev, confirmarContraseña: "" }));
    }

    // Si hay errores, actualizar el estado de errores y detener el envío
    if (newErrors.apellido || newErrors.nombre || newErrors.turno || newErrors.confirmarContraseña) {
      setErrors(newErrors);
      return;
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
        className={`inputRegBedel ${errors.turno ? 'input-error' : ''}`}
        type="text"
        name="turno"
        placeholder={placeholders.turno}
        value={form.turno}
        onChange={handleChange}
      />

      <input
        className={`inputRegBedel ${errors.identificador ? 'input-error' : ''}`}
        type="text"
        name="identificador"
        placeholder={placeholders.identificador}
        value={form.identificador}
        onChange={handleChange}
      />

      <input
        className='inputRegBedel'
        type="password"
        name="contraseña"
        placeholder={placeholders.contraseña}
        value={form.contraseña}
        onChange={handleChange}
      />

      <input
        className={`inputRegBedel ${errors.confirmarContraseña ? 'input-error' : ''}`}
        type="password"
        name="confirmarContraseña"
        placeholder={placeholders.confirmarContraseña}
        value={form.confirmarContraseña}
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