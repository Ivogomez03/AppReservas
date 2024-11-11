const TipoDeReserva = () => {
    return (
        <div>
            <div className="seccion-bienvenida">
                <h1>Tipo de reserva</h1>
                <p>Seleccione el tipo de reserva</p>

            </div>
            <div>
                <h1>Período</h1>
                <div>
                    <button>
                        Primer cuatrimestre
                    </button>
                    <button>
                        Segundo cuatrimestre
                    </button>
                    <button>
                        Anual
                    </button>
                </div>
                <h1>Esporádicas</h1>
                <div>
                    <button>Esporádica</button>
                </div>

            </div>
        </div>
    )
}