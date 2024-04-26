package andres.art_connect.service;

import andres.art_connect.domain.Comentario;
import andres.art_connect.domain.Compania;
import andres.art_connect.domain.MensajeDirecto;
import andres.art_connect.domain.Publicacion;
import andres.art_connect.domain.Seguidores;
import andres.art_connect.domain.Usuario;
import andres.art_connect.model.UsuarioDTO;
import andres.art_connect.repos.ComentarioRepository;
import andres.art_connect.repos.CompaniaRepository;
import andres.art_connect.repos.MensajeDirectoRepository;
import andres.art_connect.repos.PublicacionRepository;
import andres.art_connect.repos.SeguidoresRepository;
import andres.art_connect.repos.UsuarioRepository;
import andres.art_connect.util.NotFoundException;
import andres.art_connect.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CompaniaRepository companiaRepository;
    private final PublicacionRepository publicacionRepository;
    private final ComentarioRepository comentarioRepository;
    private final MensajeDirectoRepository mensajeDirectoRepository;
    private final SeguidoresRepository seguidoresRepository;

    public UsuarioService(final UsuarioRepository usuarioRepository,
            final CompaniaRepository companiaRepository,
            final PublicacionRepository publicacionRepository,
            final ComentarioRepository comentarioRepository,
            final MensajeDirectoRepository mensajeDirectoRepository,
            final SeguidoresRepository seguidoresRepository) {
        this.usuarioRepository = usuarioRepository;
        this.companiaRepository = companiaRepository;
        this.publicacionRepository = publicacionRepository;
        this.comentarioRepository = comentarioRepository;
        this.mensajeDirectoRepository = mensajeDirectoRepository;
        this.seguidoresRepository = seguidoresRepository;
    }

    public List<UsuarioDTO> findAll() {
        final List<Usuario> usuarios = usuarioRepository.findAll(Sort.by("id"));
        return usuarios.stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .toList();
    }

    public UsuarioDTO get(final Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        return usuarioRepository.save(usuario).getId();
    }

    public void update(final Long id, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
    }

    public void delete(final Long id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setContrasena(usuario.getContrasena());
        usuarioDTO.setCorreoElectronico(usuario.getCorreoElectronico());
        usuarioDTO.setEdad(usuario.getEdad());
        usuarioDTO.setProfesion(usuario.getProfesion());
        usuarioDTO.setFechaRegistro(usuario.getFechaRegistro());
        usuarioDTO.setFotoPerfil(usuario.getFotoPerfil());
        usuarioDTO.setTipoUsuario(usuario.getTipoUsuario());
        usuarioDTO.setIdComapania(usuario.getIdComapania() == null ? null : usuario.getIdComapania().getId());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setCorreoElectronico(usuarioDTO.getCorreoElectronico());
        usuario.setEdad(usuarioDTO.getEdad());
        usuario.setProfesion(usuarioDTO.getProfesion());
        usuario.setFechaRegistro(usuarioDTO.getFechaRegistro());
        usuario.setFotoPerfil(usuarioDTO.getFotoPerfil());
        usuario.setTipoUsuario(usuarioDTO.getTipoUsuario());
        final Compania idComapania = usuarioDTO.getIdComapania() == null ? null : companiaRepository.findById(usuarioDTO.getIdComapania())
                .orElseThrow(() -> new NotFoundException("idComapania not found"));
        usuario.setIdComapania(idComapania);
        return usuario;
    }

    public boolean nombreExists(final String nombre) {
        return usuarioRepository.existsByNombreIgnoreCase(nombre);
    }

    public boolean contrasenaExists(final String contrasena) {
        return usuarioRepository.existsByContrasenaIgnoreCase(contrasena);
    }

    public boolean correoElectronicoExists(final String correoElectronico) {
        return usuarioRepository.existsByCorreoElectronicoIgnoreCase(correoElectronico);
    }

    public boolean idComapaniaExists(final Long id) {
        return usuarioRepository.existsByIdComapaniaId(id);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Publicacion idUsuarioPublicacion = publicacionRepository.findFirstByIdUsuario(usuario);
        if (idUsuarioPublicacion != null) {
            referencedWarning.setKey("usuario.publicacion.idUsuario.referenced");
            referencedWarning.addParam(idUsuarioPublicacion.getId());
            return referencedWarning;
        }
        final Comentario idUsuarioComentario = comentarioRepository.findFirstByIdUsuario(usuario);
        if (idUsuarioComentario != null) {
            referencedWarning.setKey("usuario.comentario.idUsuario.referenced");
            referencedWarning.addParam(idUsuarioComentario.getId());
            return referencedWarning;
        }
        final MensajeDirecto idUsuarioMensajeDirecto = mensajeDirectoRepository.findFirstByIdUsuario(usuario);
        if (idUsuarioMensajeDirecto != null) {
            referencedWarning.setKey("usuario.mensajeDirecto.idUsuario.referenced");
            referencedWarning.addParam(idUsuarioMensajeDirecto.getId());
            return referencedWarning;
        }
        final Seguidores idSeguidorSeguidores = seguidoresRepository.findFirstByIdSeguidor(usuario);
        if (idSeguidorSeguidores != null) {
            referencedWarning.setKey("usuario.seguidores.idSeguidor.referenced");
            referencedWarning.addParam(idSeguidorSeguidores.getId());
            return referencedWarning;
        }
        final Seguidores idSeguidoSeguidores = seguidoresRepository.findFirstByIdSeguido(usuario);
        if (idSeguidoSeguidores != null) {
            referencedWarning.setKey("usuario.seguidores.idSeguido.referenced");
            referencedWarning.addParam(idSeguidoSeguidores.getId());
            return referencedWarning;
        }
        return null;
    }

}
