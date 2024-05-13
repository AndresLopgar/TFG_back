package andres.art_connect.service;

import andres.art_connect.domain.MensajeDirecto;
import andres.art_connect.domain.Usuario;
import andres.art_connect.model.MensajeDirectoDTO;
import andres.art_connect.repos.MensajeDirectoRepository;
import andres.art_connect.repos.UsuarioRepository;
import andres.art_connect.util.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MensajeDirectoService {

    private final MensajeDirectoRepository mensajeDirectoRepository;
    private final UsuarioRepository usuarioRepository;

    public MensajeDirectoService(final MensajeDirectoRepository mensajeDirectoRepository,
            final UsuarioRepository usuarioRepository) {
        this.mensajeDirectoRepository = mensajeDirectoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<MensajeDirectoDTO> findAll() {
        final List<MensajeDirecto> mensajesDirectos = mensajeDirectoRepository.findAll(Sort.by("fechaEnvio"));
        return mensajesDirectos.stream()
                .map(this::mapToDTO)
                .toList();
    }

    public MensajeDirectoDTO get(final Long id) {
        MensajeDirecto mensajeDirecto = mensajeDirectoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return mapToDTO(mensajeDirecto);
    }

    public Long create(final MensajeDirectoDTO mensajeDirectoDTO) {
        MensajeDirecto mensajeDirecto = new MensajeDirecto();
        mapToEntity(mensajeDirectoDTO, mensajeDirecto);
        mensajeDirecto.setFechaEnvio(LocalDateTime.now()); // Se establece la fecha y hora de envío actual
        mensajeDirecto = mensajeDirectoRepository.save(mensajeDirecto);
        return mensajeDirecto.getId();
    }

    public void update(final Long id, final MensajeDirectoDTO mensajeDirectoDTO) {
        MensajeDirecto mensajeDirecto = mensajeDirectoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(mensajeDirectoDTO, mensajeDirecto);
        mensajeDirecto.setFechaEnvio(LocalDateTime.now()); // Se actualiza la fecha y hora de envío al momento de la actualización
        mensajeDirectoRepository.save(mensajeDirecto);
    }

    public void delete(final Long id) {
        mensajeDirectoRepository.deleteById(id);
    }

    private MensajeDirectoDTO mapToDTO(final MensajeDirecto mensajeDirecto) {
        MensajeDirectoDTO mensajeDirectoDTO = new MensajeDirectoDTO();
        mensajeDirectoDTO.setId(mensajeDirecto.getId());
        mensajeDirectoDTO.setContenido(mensajeDirecto.getContenido());
        mensajeDirectoDTO.setFechaEnvio(mensajeDirecto.getFechaEnvio());
        mensajeDirectoDTO.setRemiteUsuarioId(mensajeDirecto.getRemiteUsuario().getId());
        mensajeDirectoDTO.setDestinoUsuarioId(mensajeDirecto.getDestinoUsuario().getId());
        return mensajeDirectoDTO;
    }

    private void mapToEntity(final MensajeDirectoDTO mensajeDirectoDTO, final MensajeDirecto mensajeDirecto) {
        mensajeDirecto.setContenido(mensajeDirectoDTO.getContenido());
        Usuario remitente = usuarioRepository.findById(mensajeDirectoDTO.getRemiteUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuario remitente no encontrado"));
        mensajeDirecto.setRemiteUsuario(remitente);
        Usuario destinatario = usuarioRepository.findById(mensajeDirectoDTO.getDestinoUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuario destinatario no encontrado"));
        mensajeDirecto.setDestinoUsuario(destinatario);
    }
}
