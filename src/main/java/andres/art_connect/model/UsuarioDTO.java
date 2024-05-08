package andres.art_connect.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @UsuarioNombreUnique
    private String nombre;

    @NotNull
    @Size(max = 255)
    @UsuarioContrasenaUnique
    private String contrasena;

    @NotNull
    @Size(max = 255)
    @UsuarioCorreoElectronicoUnique
    private String correoElectronico;

    @NotNull
    private Integer edad;

    @NotNull
    @Size(max = 255)
    private String profesion;

    private LocalDateTime fechaRegistro;

    @Size(max = 255)
    private String fotoPerfil;

    @NotNull
    @Size(max = 255)
    private String tipoUsuario;

    private Long companiaSeguida;

}
