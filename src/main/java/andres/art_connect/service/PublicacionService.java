package andres.art_connect.service;

import andres.art_connect.domain.Comentario;
import andres.art_connect.domain.Publicacion;
import andres.art_connect.domain.Usuario;
import andres.art_connect.model.PublicacionDTO;
import andres.art_connect.repos.ComentarioRepository;
import andres.art_connect.repos.PublicacionRepository;
import andres.art_connect.repos.UsuarioRepository;
import andres.art_connect.util.NotFoundException;
import andres.art_connect.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ComentarioRepository comentarioRepository;

    public PublicacionService(final PublicacionRepository publicacionRepository,
            final UsuarioRepository usuarioRepository,
            final ComentarioRepository comentarioRepository) {
        this.publicacionRepository = publicacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.comentarioRepository = comentarioRepository;
    }

    public List<PublicacionDTO> findAll() {
        final List<Publicacion> publicacions = publicacionRepository.findAll(Sort.by("id"));
        return publicacions.stream()
                .map(publicacion -> mapToDTO(publicacion, new PublicacionDTO()))
                .toList();
    }

    public PublicacionDTO get(final Long id) {
        return publicacionRepository.findById(id)
                .map(publicacion -> mapToDTO(publicacion, new PublicacionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PublicacionDTO publicacionDTO) {
        final Publicacion publicacion = new Publicacion();
        mapToEntity(publicacionDTO, publicacion);
        return publicacionRepository.save(publicacion).getId();
    }

    public void update(final Long id, final PublicacionDTO publicacionDTO) {
        final Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(publicacionDTO, publicacion);
        publicacionRepository.save(publicacion);
    }

    public void delete(final Long id) {
        publicacionRepository.deleteById(id);
    }

    private PublicacionDTO mapToDTO(final Publicacion publicacion,
            final PublicacionDTO publicacionDTO) {
        publicacionDTO.setId(publicacion.getId());
        publicacionDTO.setContenido(publicacion.getContenido());
        publicacionDTO.setTipoContenido(publicacion.getTipoContenido());
        publicacionDTO.setMediaPath(publicacion.getMediaPath());
        publicacionDTO.setFechaPublicacion(publicacion.getFechaPublicacion());
        publicacionDTO.setMeGusta(publicacion.getMeGusta());
        publicacionDTO.setNumMeGustas(publicacion.getNumMeGustas());
        publicacionDTO.setIdUsuario(publicacion.getIdUsuario() == null ? null : publicacion.getIdUsuario().getId());
        return publicacionDTO;
    }

    private Publicacion mapToEntity(final PublicacionDTO publicacionDTO,
            final Publicacion publicacion) {
        publicacion.setContenido(publicacionDTO.getContenido());
        publicacion.setTipoContenido(publicacionDTO.getTipoContenido());
        publicacion.setMediaPath(publicacionDTO.getMediaPath());
        publicacion.setFechaPublicacion(publicacionDTO.getFechaPublicacion());
        publicacion.setMeGusta(publicacionDTO.getMeGusta());
        publicacion.setNumMeGustas(publicacionDTO.getNumMeGustas());
        final Usuario idUsuario = publicacionDTO.getIdUsuario() == null ? null : usuarioRepository.findById(publicacionDTO.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("idUsuario not found"));
        publicacion.setIdUsuario(idUsuario);
        return publicacion;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Comentario idPublicacionComentario = comentarioRepository.findFirstByIdPublicacion(publicacion);
        if (idPublicacionComentario != null) {
            referencedWarning.setKey("publicacion.comentario.idPublicacion.referenced");
            referencedWarning.addParam(idPublicacionComentario.getId());
            return referencedWarning;
        }
        final Comentario isPublicacionComentario = comentarioRepository.findFirstByIsPublicacion(publicacion);
        if (isPublicacionComentario != null) {
            referencedWarning.setKey("publicacion.comentario.isPublicacion.referenced");
            referencedWarning.addParam(isPublicacionComentario.getId());
            return referencedWarning;
        }
        return null;
    }

}
