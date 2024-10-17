import { useState } from 'react'
import './RegistrarBedel.css';


const RegistrarBedel = () => {
  const [form, setForm] = useState({
    apellido: '',
    nombre: '',
    turno: '',
    identificador: '',
    contraseña: '',
    confirmarContraseña: ''
  });

  const handleChange = (e) => {
    console.log({ ...form })
    setForm({
      ...form,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Aquí puedes manejar el envío del formulario
    console.log(form);
  };

  return (
    <form onSubmit={handleSubmit} className='formulario'>
      <h2>Registrar Bedel</h2>

      <input
        className='inputRegBedel'
        type="text"
        name="apellido"
        placeholder="Apellido"
        value={form.apellido}
        onChange={handleChange}
      />

      <input
        className='inputRegBedel'
        type="text"
        name="nombre"
        placeholder="Nombre"
        value={form.nombre}
        onChange={handleChange}
      />

      <input
        className='inputRegBedel'
        type="text"
        name="turno"
        placeholder="Turno de trabajo"
        value={form.turno}
        onChange={handleChange}
      />

      <input
        className='inputRegBedel'
        type="text"
        name="identificador"
        placeholder="Identificador de usuario"
        value={form.identificador}
        onChange={handleChange}
      />

      <input
        className='inputRegBedel'
        type="password"
        name="contraseña"
        placeholder="Contraseña"
        value={form.contraseña}
        onChange={handleChange}
      />

      <input
        className='inputRegBedel'
        type="password"
        name="confirmarContraseña"
        placeholder="Confirmar contraseña"
        value={form.confirmarContraseña}
        onChange={handleChange}
      />

      <button className='botonRegBedel' type="submit">Registrar</button>
    </form>
  );
};

export default RegistrarBedel;

