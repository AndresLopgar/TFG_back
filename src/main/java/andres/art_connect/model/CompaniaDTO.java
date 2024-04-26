package andres.art_connect.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CompaniaDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @CompaniaNombreUnique
    private String nombre;

    @Size(max = 255)
    private String descripcion;

    @NotNull
    private Long miembros;

    private LocalDateTime fechaCreacion;

    @NotNull
    private Long idUsuario;

}
