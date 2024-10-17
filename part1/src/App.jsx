import React from 'react';
import './App.css';
import RegistrarBedel from './RegistrarBedel';

const App = () => {
  return (
    <div className="container">
      <div className="seccion-bienvenida">
        <h1>Bienvenido</h1>
        <p>Ingrese los datos solicitados</p>
      </div>
      <div className="seccion-form">
        <RegistrarBedel />
      </div>
    </div>
  );
};

export default App;