package andres.art_connect.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ComentarioDTO {

    private Long id;

    @Size(max = 255)
    private String contenido;

    private LocalDateTime fechaComentario;

    @NotNull
    private Long idUsuario;

    @NotNull
    private Long idPublicacion;

}
