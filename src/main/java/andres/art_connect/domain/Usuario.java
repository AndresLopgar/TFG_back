package andres.art_connect.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    private LocalDateTime fechaRegistro;

    @Column
    private String fotoPerfil;

    @Column(nullable = false)
    private String tipoUsuario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comapania_id", nullable = false, unique = true)
    private Compania idComapania;

    @OneToMany(mappedBy = "idUsuario")
    private Set<Publicacion> idPublicacion;

    @OneToMany(mappedBy = "idUsuario")
    private Set<Comentario> idComentario;

    @OneToMany(mappedBy = "idUsuario")
    private Set<MensajeDirecto> idMensaje;

    @OneToMany(mappedBy = "idSeguidor")
    private Set<Seguidores> idSeguidor;

    @OneToMany(mappedBy = "idSeguido")
    private Set<Seguidores> idSeguido;

}
