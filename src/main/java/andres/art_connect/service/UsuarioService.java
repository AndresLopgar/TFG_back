package andres.art_connect.service;

import andres.art_connect.domain.Comentario; 
import andres.art_connect.domain.Compania;
import andres.art_connect.domain.MensajeDirecto;
import andres.art_connect.domain.Publicacion;
import andres.art_connect.domain.Usuario;
import andres.art_connect.model.UsuarioDTO;
import andres.art_connect.repos.ComentarioRepository;
import andres.art_connect.repos.CompaniaRepository;
import andres.art_connect.repos.MensajeDirectoRepository;
import andres.art_connect.repos.PublicacionRepository;
import andres.art_connect.repos.UsuarioRepository;
import andres.art_connect.util.NotFoundException;
import andres.art_connect.util.ReferencedWarning;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CompaniaRepository companiaRepository;
    private final PublicacionRepository publicacionRepository;
    private final MensajeDirectoRepository mensajeDirectoRepository;
    private final ComentarioRepository comentarioRepository;

    public UsuarioService(final UsuarioRepository usuarioRepository,
            final CompaniaRepository companiaRepository,
            final PublicacionRepository publicacionRepository,
            final MensajeDirectoRepository mensajeDirectoRepository,
            final ComentarioRepository comentarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.companiaRepository = companiaRepository;
        this.publicacionRepository = publicacionRepository;
        this.mensajeDirectoRepository = mensajeDirectoRepository;
        this.comentarioRepository = comentarioRepository;
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
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setTipoUsuario("estandar");// Asigna la fecha de registro actual
        return usuarioRepository.save(usuario).getId();
    }

    public void update(final Long id, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
    }

    public void delete(final Long id) {
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        usuarioRepository.findAllByIdSeguidor(usuario)
                .forEach(user -> user.getIdSeguidor().remove(usuario));
        usuarioRepository.delete(usuario);
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
        usuarioDTO.setIdSeguidor(usuario.getIdSeguidor().stream()
                .map(usuarioInt -> usuarioInt.getId())
                .toList());
        usuarioDTO.setIdUsarioCompania(usuario.getIdUsarioCompania() == null ? null : usuario.getIdUsarioCompania().getId());
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
        final List<Usuario> idSeguidor = usuarioRepository.findAllById(
                usuarioDTO.getIdSeguidor() == null ? Collections.emptyList() : usuarioDTO.getIdSeguidor());
        if (idSeguidor.size() != (usuarioDTO.getIdSeguidor() == null ? 0 : usuarioDTO.getIdSeguidor().size())) {
            throw new NotFoundException("one of idSeguidor not found");
        }
        usuario.setIdSeguidor(new HashSet<>(idSeguidor));
        final Compania idUsarioCompania = usuarioDTO.getIdUsarioCompania() == null ? null : companiaRepository.findById(usuarioDTO.getIdUsarioCompania())
                .orElseThrow(() -> new NotFoundException("idUsarioCompania not found"));
        usuario.setIdUsarioCompania(idUsarioCompania);
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

    public boolean idUsarioCompaniaExists(final Long id) {
        return usuarioRepository.existsByIdUsarioCompaniaId(id);
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
        final MensajeDirecto idUsuarioMensajeDirecto = mensajeDirectoRepository.findFirstByIdUsuario(usuario);
        if (idUsuarioMensajeDirecto != null) {
            referencedWarning.setKey("usuario.mensajeDirecto.idUsuario.referenced");
            referencedWarning.addParam(idUsuarioMensajeDirecto.getId());
            return referencedWarning;
        }
        final Comentario idUsuarioComentario = comentarioRepository.findFirstByIdUsuario(usuario);
        if (idUsuarioComentario != null) {
            referencedWarning.setKey("usuario.comentario.idUsuario.referenced");
            referencedWarning.addParam(idUsuarioComentario.getId());
            return referencedWarning;
        }
        return null;
    }

}
