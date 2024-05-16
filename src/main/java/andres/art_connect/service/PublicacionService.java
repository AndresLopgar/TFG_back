package andres.art_connect.service;

import andres.art_connect.domain.Comentario;
import andres.art_connect.domain.Compania;
import andres.art_connect.domain.Publicacion;
import andres.art_connect.domain.Usuario;
import andres.art_connect.model.PublicacionDTO;
import andres.art_connect.repos.ComentarioRepository;
import andres.art_connect.repos.CompaniaRepository;
import andres.art_connect.repos.PublicacionRepository;
import andres.art_connect.repos.UsuarioRepository;
import andres.art_connect.util.NotFoundException;
import andres.art_connect.util.ReferencedWarning;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


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
    
    public List<PublicacionDTO> getAllPublicacionesByIdUsuario(Long idUsuario) {
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario); // Crea un objeto Usuario con el id proporcionado
        List<Publicacion> publicaciones = publicacionRepository.findAllByIdUsuario(usuario);
        return mapToDTOList(publicaciones);
    }

    public List<PublicacionDTO> getAllPublicacionesByIdCompania(Long idCompania) {
        List<Publicacion> publicaciones = publicacionRepository.findAllByIdCompania(idCompania, Sort.by("idCompania"));
        return mapToDTOList(publicaciones);
    }
    
    private List<PublicacionDTO> mapToDTOList(List<Publicacion> publicaciones) {
        return publicaciones.stream()
            .map(publicacion -> mapToDTO(publicacion, new PublicacionDTO()))
            .collect(Collectors.toList());
    }

    private PublicacionDTO mapToDTO(final Publicacion publicacion,
            final PublicacionDTO publicacionDTO) {
        publicacionDTO.setId(publicacion.getId());
        publicacionDTO.setContenido(publicacion.getContenido());
        publicacionDTO.setFechaPublicacion(publicacion.getFechaPublicacion());
        publicacionDTO.setMeGusta(publicacion.getMeGusta());
        publicacionDTO.setNumMeGustas(publicacion.getNumMeGustas());
        publicacionDTO.setIdUsuario(publicacion.getIdUsuario() == null ? null : publicacion.getIdUsuario().getId());
        return publicacionDTO;
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
