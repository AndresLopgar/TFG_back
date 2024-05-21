package andres.art_connect.service;

import andres.art_connect.domain.Notificacion;
import andres.art_connect.model.NotificacionDTO;
import andres.art_connect.repos.NotificacionRepository;
import andres.art_connect.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
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

    private NotificacionDTO mapToDTO(Notificacion notificacion) {
        NotificacionDTO notificacionDTO = new NotificacionDTO();
        notificacionDTO.setId(notificacion.getId());
        notificacionDTO.setContenido(notificacion.getContenido());
        notificacionDTO.setTipoNotificacion(notificacion.getTipoNotificacion());
        notificacionDTO.setIdUsuarioEmisor(notificacion.getIdUsuarioEmisor().getId());
        notificacionDTO.setIdUsuarioRemitente(notificacion.getIdUsuarioRemitente().getId());
        return notificacionDTO;
    }

    private Notificacion mapToEntity(NotificacionDTO notificacionDTO) {
        Notificacion notificacion = new Notificacion();
        notificacion.setContenido(notificacionDTO.getContenido());
        notificacion.setTipoNotificacion(notificacionDTO.getTipoNotificacion());
        // Asegúrate de configurar correctamente los IDs de los usuarios
        // Dependiendo de cómo esté implementada tu lógica de negocio
        return notificacion;
    }

    private void updateEntityFromDto(NotificacionDTO notificacionDTO, Notificacion notificacion) {
        notificacion.setContenido(notificacionDTO.getContenido());
        notificacion.setTipoNotificacion(notificacionDTO.getTipoNotificacion());
        // Aquí podrías agregar más lógica de actualización según tus necesidades
    }
}
