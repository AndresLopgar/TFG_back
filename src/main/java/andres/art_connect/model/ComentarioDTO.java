package andres.art_connect.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ComentarioDTO {

    private Long id;

    @Size(max = 255)
    private String contenido;

    @Size(max = 255)
    private String fechaComentario;

    private Long idUsuario;

    private Long idPublicacion;

}
