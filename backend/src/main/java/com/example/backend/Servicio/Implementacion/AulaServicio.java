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
    private final AulaInformaticaDAO aulaInformaticaDAO;
    private final AulaSRADAO aulaSinRecursosAdicionalesDAO;
    private final AulaMultimedioDAO aulaMultimedioDAO;

    @Autowired
    private AulaServicio aulaServicio;
    public String modificarAula(ModificarAulaDTO dto){

        Aula aula = findByNumeroDeAula(dto.getNumeroDeAula());
        
        // Validación del tipo de aula
        if (aula == null) {
            throw new IllegalArgumentException("Este numero de aula no existe");
            
        }
        else if(dto.getCapacidad() < 0){
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0")
        }
        else{
        
            
                if (aula instanceof AulaInformatica) {
                    modificarAulaInformatica(dto,aula);
                } 
                else if (aula instanceof AulaMultimedio) {
                    modificarAulaMultimedio(dto,aula);
                } 
                else if (aula instanceof AulaSinRecursosAdicionales) {
                    modificarAulaSinRecursosAdicionales(dto,aula);
                }
                return "El aula ha sido modificada correctamente";
            
        }
      
        
    }
    public void modificarAulaInformatica(ModificarAulaDTO aulaDTO,Aula aula){
        

        if (aulaDTO.getCantidadPCs() <= 0) {
            throw new IllegalArgumentException("La cantidad de PCs debe ser mayor a cero");
        }

        if (aulaDTO.getCantidadPCs() > aulaDTO.getCapacidad()) {
            throw new IllegalArgumentException("La cantidad de PCs no puede ser mayor a la capacidad");
        }

        // 4. Modificar los datos del aula informática
        aula.setCapacidad(aulaDTO.getCapacidad());
        aula.setCantidadDeComputadoras(aulaDTO.getCantidadDeComputadoras());
        aula.setTipoPizarron(aulaDTO.getTipoPizarron());
        aula.setCanion(aulaDTO.getCanion());
        aula.setAireAcondicionado(aulaDTO.getAireAcondicionado());

        // 5. Guardar los cambios en la base de datos
        aulaInformaticaDAO.save(aula);


    }

    public void modificarAulaMultimedio(ModificarAulaDTO aulaDTO,Aula aula){

        // 4. Modificar los datos del aula multimedio
        aula.setCapacidad(aulaDTO.getCapacidad());
        aula.setTipoPizarron(aulaDTO.getTipoPizarron());
        aula.setCanion(aulaDTO.getCanion());
        aula.setTelevisor(aulaDTO.getTelevisor());
        aula.setVentilador(aulaDTO.getVentilador());
        aula.setComputadora(aulaDTO.getComputadora());
        aula.setAireAcondicionado(aulaDTO.getAireAcondicionado());

        // 5. Guardar los cambios en la base de datos
        aulaMultimedioDAO.save(aula);
    }

    public void modificarAulaSinRecursosAdicionales(ModificarAulaDTO aulaDTO,Aula aula){
        if (aulaDTO.getAireAcondicionado() && aulaDTO.getVentilador()) {
            throw new IllegalArgumentException("Si el aula posee aire acondicionado no puede poseer ventiladores y viceversa.");
        }
        // 4. Modificar los datos del aula sin recursos
        aula.setCapacidad(aulaDTO.getCapacidad());
        aula.setTipoPizarron(aulaDTO.getTipoPizarron());
  
        aula.setVentilador(aulaDTO.getVentilador());
        aula.setAireAcondicionado(aulaDTO.getAireAcondicionado());

        // 5. Guardar los cambios en la base de datos
        aulaSRADAO.save(aula);


    }

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
        dto.setNumeroDeAula(aula.getNumeroDeAula());
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
