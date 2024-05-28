package andres.art_connect.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

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
    
    @Column
    private String portafolio;

    @OneToMany(mappedBy = "idUsuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Publicacion> idUsuarioPublicacion;

    @OneToMany(mappedBy = "idCreador", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Compania> idCompania;

    @OneToMany(mappedBy = "idUsuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comentario> idUsuarioComentario;

    @OneToMany(mappedBy = "idSeguidor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Amistad> idSeguidor;

    @OneToMany(mappedBy = "idSeguido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Amistad> idSeguido;
    
    @OneToMany(mappedBy = "idUsuarioEmisor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notificacion> idUsuarioEmisor;
    
    @OneToMany(mappedBy = "idUsuarioRemitente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notificacion> idUsuarioRemitente;
}

