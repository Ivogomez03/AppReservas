import React, { useState } from 'react';
import './App.css';
import RegistrarBedel from './RegistrarBedel';
import CancelarBedel from './CancelarBedel'
const App = () => {

  const [mostrarRegistroBedel, setMostrarRegistroBedel] = useState(true)

  const mostrarCancelarBedel = () => setMostrarRegistroBedel(false)

  const mostrarRegistrarBedel = () => setMostrarRegistroBedel(true)

  return (
    mostrarRegistroBedel ? (
      <div className="container">

        <div className="seccion-bienvenida">
          <h1>Bienvenido</h1>
          <p>Ingrese los datos solicitados</p>
        </div>
        <div className="seccion-form">
          <RegistrarBedel mostrar={mostrarCancelarBedel} />
        </div>


      </div>) : (<CancelarBedel mostrar={mostrarRegistrarBedel} />)

  );
};

export default App;