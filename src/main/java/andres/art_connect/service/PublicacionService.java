package andres.art_connect.service;

import andres.art_connect.domain.Comentario;
import andres.art_connect.domain.Compania;
import andres.art_connect.domain.Publicacion;
import andres.art_connect.domain.Usuario;
import andres.art_connect.model.ComentarioDTO;
import andres.art_connect.model.PublicacionDTO;
import andres.art_connect.repos.ComentarioRepository;
import andres.art_connect.repos.CompaniaRepository;
import andres.art_connect.repos.PublicacionRepository;
import andres.art_connect.repos.UsuarioRepository;
import andres.art_connect.util.NotFoundException;
import andres.art_connect.util.ReferencedException;
import andres.art_connect.util.ReferencedWarning;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final CompaniaRepository companiaRepository;
    private final ComentarioRepository comentarioRepository;

    public PublicacionService(final PublicacionRepository publicacionRepository,
            final UsuarioRepository usuarioRepository,
            final ComentarioRepository comentarioRepository,
            final CompaniaRepository companiaRepository) {
        this.publicacionRepository = publicacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.comentarioRepository = comentarioRepository;
        this.companiaRepository = companiaRepository;
    }

    public List<PublicacionDTO> findAll() {
        final List<Publicacion> publicacions = publicacionRepository.findAll(Sort.by("id"));
        return publicacions.stream()
        		.map(this::mapToDTO)
                .toList();
    }

    public PublicacionDTO get(final Long id) {
        return publicacionRepository.findById(id)
        		.map(this::mapToDTO)
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
    
    public List<PublicacionDTO> getAllPublicacionesByIdUsuario(Long idUsuario) {
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario); // Crea un objeto Usuario con el id proporcionado
        List<Publicacion> publicaciones = publicacionRepository.findAllByIdUsuario(usuario);
        return mapToDTOList(publicaciones);
    }

    public List<PublicacionDTO> getAllPublicacionesByIdCompania(Long idCompania) {
        List<Publicacion> publicaciones = publicacionRepository.findAllByIdCompania(idCompania);
        return mapToDTOList(publicaciones);
    }
    
    private List<PublicacionDTO> mapToDTOList(List<Publicacion> publicaciones) {
        return publicaciones.stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    
    @Transactional(readOnly = true)
    public List<PublicacionDTO> findAllWithComentarios() {
        return publicacionRepository.findAllWithComentarios().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private PublicacionDTO mapToDTO(final Publicacion publicacion) {
        PublicacionDTO publicacionDTO = new PublicacionDTO();
        publicacionDTO.setId(publicacion.getId());
        publicacionDTO.setContenido(publicacion.getContenido());
        publicacionDTO.setFechaPublicacion(publicacion.getFechaPublicacion());
        publicacionDTO.setMeGusta(publicacion.getMeGusta());
        publicacionDTO.setNumMeGustas(publicacion.getNumMeGustas());
        // Mapea el ID del usuario si está presente
        if (publicacion.getIdUsuario() != null) {
            publicacionDTO.setIdUsuario(publicacion.getIdUsuario().getId());
        }
        // Mapea el ID de la compañía si está presente
        publicacionDTO.setIdCompania(publicacion.getIdCompania());
        // Mapea los comentarios si están presentes
        if (Hibernate.isInitialized(publicacion.getComentarios())) {
            Set<ComentarioDTO> comentariosDTO = publicacion.getComentarios().stream()
                    .map(this::mapComentarioToDTO)
                    .collect(Collectors.toSet());
            publicacionDTO.setComentarios(comentariosDTO);
        }
        return publicacionDTO;
    }


    private ComentarioDTO mapComentarioToDTO(final Comentario comentario) {
        ComentarioDTO comentarioDTO = new ComentarioDTO();
        comentarioDTO.setId(comentario.getId());
        comentarioDTO.setContenido(comentario.getContenido());
        comentarioDTO.setFechaComentario(comentario.getFechaComentario());
        // Mapea el ID del usuario si está presente
        if (comentario.getIdUsuario() != null) {
            comentarioDTO.setIdUsuario(comentario.getIdUsuario().getId());
        }
        return comentarioDTO;
    }


    private Publicacion mapToEntity(final PublicacionDTO publicacionDTO,
            final Publicacion publicacion) {
        publicacion.setContenido(publicacionDTO.getContenido());
        publicacion.setFechaPublicacion(publicacionDTO.getFechaPublicacion());
        publicacion.setMeGusta(publicacionDTO.getMeGusta());
        publicacion.setNumMeGustas(publicacionDTO.getNumMeGustas());
        final Usuario idUsuario = publicacionDTO.getIdUsuario() == null ? null : usuarioRepository.findById(publicacionDTO.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("idUsuario not found"));
        publicacion.setIdUsuario(idUsuario);
        publicacion.setIdCompania(publicacionDTO.getIdCompania());
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
        return null;
    }

}
