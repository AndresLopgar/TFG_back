package andres.art_connect.service;

import andres.art_connect.domain.Comentario;
import andres.art_connect.domain.Publicacion;
import andres.art_connect.domain.Usuario;
import andres.art_connect.model.ComentarioDTO;
import andres.art_connect.repos.ComentarioRepository;
import andres.art_connect.repos.PublicacionRepository;
import andres.art_connect.repos.UsuarioRepository;
import andres.art_connect.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final PublicacionRepository publicacionRepository;

    public ComentarioService(final ComentarioRepository comentarioRepository,
            final UsuarioRepository usuarioRepository,
            final PublicacionRepository publicacionRepository) {
        this.comentarioRepository = comentarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.publicacionRepository = publicacionRepository;
    }

    public List<ComentarioDTO> findAll() {
        final List<Comentario> comentarios = comentarioRepository.findAll(Sort.by("id"));
        return comentarios.stream()
                .map(comentario -> mapToDTO(comentario, new ComentarioDTO()))
                .toList();
    }

    public ComentarioDTO get(final Long id) {
        return comentarioRepository.findById(id)
                .map(comentario -> mapToDTO(comentario, new ComentarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ComentarioDTO comentarioDTO) {
        final Comentario comentario = new Comentario();
        mapToEntity(comentarioDTO, comentario);
        return comentarioRepository.save(comentario).getId();
    }

    public void update(final Long id, final ComentarioDTO comentarioDTO) {
        final Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(comentarioDTO, comentario);
        comentarioRepository.save(comentario);
    }

    public void delete(final Long id) {
        comentarioRepository.deleteById(id);
    }

    private ComentarioDTO mapToDTO(final Comentario comentario, final ComentarioDTO comentarioDTO) {
        comentarioDTO.setId(comentario.getId());
        comentarioDTO.setContenido(comentario.getContenido());
        comentarioDTO.setFechaComentario(comentario.getFechaComentario());
        comentarioDTO.setIdUsuario(comentario.getIdUsuario() == null ? null : comentario.getIdUsuario().getId());
        comentarioDTO.setIdPublicacion(comentario.getIdPublicacion() == null ? null : comentario.getIdPublicacion().getId());
        comentarioDTO.setIsPublicacion(comentario.getIsPublicacion() == null ? null : comentario.getIsPublicacion().getId());
        return comentarioDTO;
    }

    private Comentario mapToEntity(final ComentarioDTO comentarioDTO, final Comentario comentario) {
        comentario.setContenido(comentarioDTO.getContenido());
        comentario.setFechaComentario(comentarioDTO.getFechaComentario());
        final Usuario idUsuario = comentarioDTO.getIdUsuario() == null ? null : usuarioRepository.findById(comentarioDTO.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("idUsuario not found"));
        comentario.setIdUsuario(idUsuario);
        final Publicacion idPublicacion = comentarioDTO.getIdPublicacion() == null ? null : publicacionRepository.findById(comentarioDTO.getIdPublicacion())
                .orElseThrow(() -> new NotFoundException("idPublicacion not found"));
        comentario.setIdPublicacion(idPublicacion);
        final Publicacion isPublicacion = comentarioDTO.getIsPublicacion() == null ? null : publicacionRepository.findById(comentarioDTO.getIsPublicacion())
                .orElseThrow(() -> new NotFoundException("isPublicacion not found"));
        comentario.setIsPublicacion(isPublicacion);
        return comentario;
    }

}
