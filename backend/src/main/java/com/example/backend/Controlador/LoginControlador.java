import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend.Servicio.Implementacion.LoginServicio;
import com.example.backend.DTO.LoginDTO;
import com.example.backend.DTO.SalidaLoginDTO;
@RestController
@RequestMapping("/login")
public class LoginControlador {

    @Autowired
    private LoginServicio loginServicio;

    @PostMapping
    public SalidaLoginDTO validarLogin(@RequestBody LoginDTO loginDTO) {
        // Llamar al servicio para validar usuario
        int esValido = loginServicio.validarUsuario(loginDTO);

        // Retornar la respuesta según la validación
        if (esValido == 1) {
            return new SalidaLoginDTO(true, false);
        } else if(esValido == 2){
            return new SalidaLoginDTO(false, true);
        }
        else{
            return new SalidaLoginDTO(false,false);
        }
    }
}