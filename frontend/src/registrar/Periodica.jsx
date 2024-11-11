import RegistrarReserva from "./RegistrarReserva"

const Periodica = ({ tipo }) => {
    return (
        <div>
            <div>
                <div>
                    <h1>Período</h1>
                    <h4>Ingrese los datos solicitados</h4>
                </div>
                <div>
                    <h1>{tipo}</h1>
                    <button>Agregar Día</button>
                </div>
            </div>

            <RegistrarReserva />
        </div>

    )
}