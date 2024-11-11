import React from 'react';
import './CancelarBedel.css';

const CancelarBedel = ({ onCancel, onConfirm }) => {
  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <h1>¿Estás seguro de que deseas cancelar?</h1>
        <p>Si cancelas, perderás todo el progreso hasta ahora.</p>
        <div className="modal-buttons">
          <button className="modal-button volver" onClick={onCancel}>Volver</button>
          <button className="modal-button cancelar" onClick={onConfirm}>Cancelar</button>
        </div>
      </div>
    </div>
  );
};

export default CancelarBedel;