package andres.art_connect.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    @Size(max = 255)
    private String contrasena;

    @NotNull
    @Size(max = 255)
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
