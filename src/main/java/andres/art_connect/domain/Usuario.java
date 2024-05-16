package andres.art_connect.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

import andres.art_connect.model.UsuarioDTO;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String contrasena;

    @Column(nullable = false, unique = true)
    private String correoElectronico;

    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = false)
    private String profesion;
    
    @Column
    private String fotoPerfil;

    @Column
    private LocalDateTime fechaRegistro;

    @Column(nullable = false)
    private String tipoUsuario;

    @Column
    private Long companiaSeguida;

    @OneToMany(mappedBy = "idUsuario")
    private Set<Publicacion> idUsuarioPublicacion;

    @OneToMany(mappedBy = "idCreador")
    private Set<Compania> idCompania;

    @OneToMany(mappedBy = "idUsuario")
    private Set<Comentario> idUsuarioComentario;

    @OneToMany(mappedBy = "idSeguidor")
    private Set<Amistad> idSeguidor;

    @OneToMany(mappedBy = "idSeguido")
    private Set<Amistad> idSeguido;
}

