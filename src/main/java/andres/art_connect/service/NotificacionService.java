package andres.art_connect.service;

import andres.art_connect.domain.Notificacion;
import andres.art_connect.domain.Usuario;
import andres.art_connect.model.NotificacionDTO;
import andres.art_connect.repos.NotificacionRepository;
import andres.art_connect.repos.UsuarioRepository;
import andres.art_connect.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;

    public NotificacionService(NotificacionRepository notificacionRepository, UsuarioRepository usuarioRepository) {
        this.notificacionRepository = notificacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<NotificacionDTO> findAll() {
        List<Notificacion> notificaciones = notificacionRepository.findAll(Sort.by("id"));
        return notificaciones.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public NotificacionDTO findById(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return mapToDTO(notificacion);
    }

    public Long create(NotificacionDTO notificacionDTO) {
        Notificacion notificacion = mapToEntity(notificacionDTO);
        return notificacionRepository.save(notificacion).getId();
    }

    public void update(Long id, NotificacionDTO notificacionDTO) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        updateEntityFromDto(notificacionDTO, notificacion);
        notificacionRepository.save(notificacion);
    }

    public void delete(Long id) {
        notificacionRepository.deleteById(id);
    }
    
    public List<NotificacionDTO> findAllByUsuarioRemitente(Long idUsuarioRemitente) {
        List<Notificacion> notificaciones = notificacionRepository.findByIdUsuarioRemitente_Id(idUsuarioRemitente);
        return notificaciones.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private NotificacionDTO mapToDTO(Notificacion notificacion) {
        NotificacionDTO notificacionDTO = new NotificacionDTO();
        notificacionDTO.setId(notificacion.getId());
        notificacionDTO.setContenido(notificacion.getContenido());
        notificacionDTO.setTipoNotificacion(notificacion.getTipoNotificacion());
        notificacionDTO.setIdUsuarioEmisor(notificacion.getIdUsuarioEmisor().getId());
        notificacionDTO.setIdUsuarioRemitente(notificacion.getIdUsuarioRemitente().getId());
        notificacionDTO.setFechaNotificacion(notificacion.getFechaNotificacion()); // Añadir la fecha si es relevante
        return notificacionDTO;
    }

    private Notificacion mapToEntity(NotificacionDTO notificacionDTO) {
        Notificacion notificacion = new Notificacion();
        notificacion.setContenido(notificacionDTO.getContenido());
        notificacion.setTipoNotificacion(notificacionDTO.getTipoNotificacion());
        notificacion.setFechaNotificacion(notificacionDTO.getFechaNotificacion()); // Añadir la fecha si es relevante

        Usuario emisor = usuarioRepository.findById(notificacionDTO.getIdUsuarioEmisor())
                .orElseThrow(() -> new NotFoundException("Usuario emisor no encontrado"));
        Usuario remitente = usuarioRepository.findById(notificacionDTO.getIdUsuarioRemitente())
                .orElseThrow(() -> new NotFoundException("Usuario remitente no encontrado"));

        notificacion.setIdUsuarioEmisor(emisor);
        notificacion.setIdUsuarioRemitente(remitente);

        return notificacion;
    }

    private void updateEntityFromDto(NotificacionDTO notificacionDTO, Notificacion notificacion) {
        notificacion.setContenido(notificacionDTO.getContenido());
        notificacion.setTipoNotificacion(notificacionDTO.getTipoNotificacion());
        notificacion.setFechaNotificacion(notificacionDTO.getFechaNotificacion()); // Añadir la fecha si es relevante

        if (notificacion.getIdUsuarioEmisor().getId() != notificacionDTO.getIdUsuarioEmisor()) {
            Usuario emisor = usuarioRepository.findById(notificacionDTO.getIdUsuarioEmisor())
                    .orElseThrow(() -> new NotFoundException("Usuario emisor no encontrado"));
            notificacion.setIdUsuarioEmisor(emisor);
        }

        if (notificacion.getIdUsuarioRemitente().getId() != notificacionDTO.getIdUsuarioRemitente()) {
            Usuario remitente = usuarioRepository.findById(notificacionDTO.getIdUsuarioRemitente())
                    .orElseThrow(() -> new NotFoundException("Usuario remitente no encontrado"));
            notificacion.setIdUsuarioRemitente(remitente);
        }
    }
}
