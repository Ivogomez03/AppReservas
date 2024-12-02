package com.example.backend.Servicio.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;


import com.example.backend.DTO.BuscarAulaDTO;
import com.example.backend.DTO.SalidaCU9DTO;
import com.example.backend.Modelos.Aula;
import com.example.backend.DAO.AulaInformaticaDAO;
import com.example.backend.DAO.AulaMultimedioDAO;
import com.example.backend.DAO.AulaSRADAO;



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
    

}
