package andres.art_connect.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PublicacionDTO {

    private Long id;

    @Size(max = 255)
    private String contenido;

    private LocalDateTime fechaPublicacion;

    private Boolean meGusta;

    private Long numMeGustas;

    private Long idUsuario;
    
    private Long idCompania;

}
