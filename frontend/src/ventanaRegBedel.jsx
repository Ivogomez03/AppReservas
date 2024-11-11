import React, { useState, useRef } from 'react';
import './ventanaRegBedel.css';
import RegistrarBedel from './RegistrarBedel';
import ModalConfirmacion from './cancelar/CancelarBedel';

const ventanaRegBedel = () => {
  const [mostrarModal, setMostrarModal] = useState(false);
  const formRef = useRef(null); // Referencia para resetear el formulario

  const mostrarCancelarBedel = () => setMostrarModal(true);
  const ocultarModal = () => setMostrarModal(false);

  const confirmarCancelacion = () => {
    setMostrarModal(false);
    if (formRef.current) formRef.current(); // Llama a la función de reset del formulario, si existe
  };

  return (
    <div className="container">
      <div className="seccion-bienvenida">
        <h1>Por favor</h1>
        <p>Ingrese los datos solicitados</p>
      </div>
      <div className="seccion-form">
        <RegistrarBedel mostrar={mostrarCancelarBedel} resetForm={formRef} />
      </div>

      {mostrarModal && (
        <ModalConfirmacion onCancel={ocultarModal} onConfirm={confirmarCancelacion} />
      )}
    </div>
  );
};

export default ventanaRegBedel;