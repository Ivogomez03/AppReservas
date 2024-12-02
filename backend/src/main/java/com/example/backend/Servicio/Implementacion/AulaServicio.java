package com.example.backend.Servicio.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;


import com.example.backend.DTO.BuscarAulaDTO;
import com.example.backend.DTO.SalidaCU9DTO;
import com.example.backend.Modelos.Aula;
import com.example.backend.Modelos.AulaInformatica;
import com.example.backend.Modelos.AulaMultimedio;
import com.example.backend.Modelos.AulaSinRecursosAdicionales;
import com.example.backend.DAO.AulaInformaticaDAO;
import com.example.backend.DAO.AulaMultimedioDAO;
import com.example.backend.DAO.AulaSRADAO;
import com.example.backend.DTO.AulaDTO;
import com.example.backend.Excepciones.ValidationException;




public class AulaServicio implements IAulaServicio {

    @Autowired
    private AulaInformaticaDAO aulaInformaticaDAO;
    private AulaSRADAO aulaSinRecursosAdicionalesDAO;
    private AulaMultimedioDAO aulaMultimedioDAO;

    @Autowired
    private AulaServicio aulaServicio;

    public List<SalidaCU9DTO> buscarAulas(BuscarAulaDTO buscarAulaDTO) {
        List<Aula> aulas = new ArrayList<>();

        // Consultar cada DAO según el criterio
        if (buscarAulaDTO.getTipoAula() == null || buscarAulaDTO.getTipoAula().equalsIgnoreCase("Informatica")) {
            aulas.addAll(aulaInformaticaDAO.buscarAulasPorCriterio(buscarAulaDTO));
        }
        if (buscarAulaDTO.getTipoAula() == null || buscarAulaDTO.getTipoAula().equalsIgnoreCase("Multimedio")) {
            aulas.addAll(aulaMultimedioDAO.buscarAulasPorCriterio(buscarAulaDTO));
        }
        if (buscarAulaDTO.getTipoAula() == null || buscarAulaDTO.getTipoAula().equalsIgnoreCase("SinRecursosAdicionales")) {
            aulas.addAll(aulaSinRecursosAdicionalesDAO.buscarAulasPorCriterio(buscarAulaDTO));
        }
        if (aulas.isEmpty()){
            throw new ValidationException("No se encontro ningún aula con los criterios especificados");
        }

        return aulas.stream()
                .map(this::convertirASalidaCU9DTO)
                .collect(Collectors.toList());
        
    }
    private SalidaCU9DTO convertirASalidaCU9DTO(Aula aula) {
        SalidaCU9DTO dto = new SalidaCU9DTO();
        dto.setNroAula(aula.getNroAula());
        dto.setCapacidad(aula.getCapacidad());
        dto.setTipoAula(aula.getTipoAula());
        dto.setPiso(aula.getPiso());
        dto.setHabilitado(aula.getHabilitado());
        return dto;
    }
    




    public Aula crearAula(AulaDTO aulaDTO) {

        if(aulaDTO.isAulaMultimedia()){
            AulaMultimedio aula = new AulaMultimedio();
            aula.setTelevisor(aulaDTO.isTelevisor());
            aula.setProyector(aulaDTO.isProyector());
            aula.setComputadora(aulaDTO.isComputadora());
            aula.setVentilador(aulaDTO.isVentilador());

            aula.setTipoPizarron(aulaDTO.getTipoPizarron());
            aula.setNumeroDeAula(aulaDTO.getNumeroDeAula());
            aula.setCapacidad(aulaDTO.getCapacidad());
            aula.setPiso(aulaDTO.getPiso());
            aula.setIdAula(aulaDTO.getIdAula());
            aula.setCaracteristicas(aulaDTO.getCaracteristicas());
            aula.setAireAcondicionado(aulaDTO.isAireAcondicionado());
            aula.setHabilitado(aulaDTO.isHabilitado());

            return aula;
        }
        else if(aulaDTO.isAulaInformatica()){
            AulaInformatica aula = new AulaInformatica();
            aula.setCantidadDeComputadoras(aulaDTO.getCantidadDeComputadoras());
            aula.setProyector(aulaDTO.isProyector());
            
            aula.setTipoPizarron(aulaDTO.getTipoPizarron());
            aula.setNumeroDeAula(aulaDTO.getNumeroDeAula());
            aula.setCapacidad(aulaDTO.getCapacidad());
            aula.setPiso(aulaDTO.getPiso());
            aula.setIdAula(aulaDTO.getIdAula());
            aula.setCaracteristicas(aulaDTO.getCaracteristicas());
            aula.setAireAcondicionado(aulaDTO.isAireAcondicionado());
            aula.setHabilitado(aulaDTO.isHabilitado());

            return aula;
        }
        else if(aulaDTO.isAulaSinRecursosAdicionales()){
            AulaSinRecursosAdicionales aula = new AulaSinRecursosAdicionales();
            aula.setVentilador(aulaDTO.isVentilador());

            aula.setTipoPizarron(aulaDTO.getTipoPizarron());
            aula.setNumeroDeAula(aulaDTO.getNumeroDeAula());
            aula.setCapacidad(aulaDTO.getCapacidad());
            aula.setPiso(aulaDTO.getPiso());
            aula.setIdAula(aulaDTO.getIdAula());
            aula.setCaracteristicas(aulaDTO.getCaracteristicas());
            aula.setAireAcondicionado(aulaDTO.isAireAcondicionado());
            aula.setHabilitado(aulaDTO.isHabilitado());

            return aula;
        }
        else{
            throw new ValidationException("Hubo un error con el tipo de reserva");
        }
    }
}
