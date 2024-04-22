package andres.art_connect.model;

import jakarta.validation.constraints.NotNull;
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

    @Size(max = 255)
    private String tipoContenido;

    @Size(max = 255)
    private String mediaPath;

    private LocalDateTime fechaPublicacion;

    private Boolean meGusta;

    private Long numMeGustas;

    @NotNull
    private Long idUsuario;

}
