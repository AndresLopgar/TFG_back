package andres.art_connect.service;

import andres.art_connect.domain.MensajeDirecto;
import andres.art_connect.domain.Usuario;
import andres.art_connect.model.MensajeDirectoDTO;
import andres.art_connect.repos.MensajeDirectoRepository;
import andres.art_connect.repos.UsuarioRepository;
import andres.art_connect.util.NotFoundException;
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
        final List<MensajeDirecto> mensajeDirectoes = mensajeDirectoRepository.findAll(Sort.by("id"));
        return mensajeDirectoes.stream()
                .map(mensajeDirecto -> mapToDTO(mensajeDirecto, new MensajeDirectoDTO()))
                .toList();
    }

    public MensajeDirectoDTO get(final Long id) {
        return mensajeDirectoRepository.findById(id)
                .map(mensajeDirecto -> mapToDTO(mensajeDirecto, new MensajeDirectoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MensajeDirectoDTO mensajeDirectoDTO) {
        final MensajeDirecto mensajeDirecto = new MensajeDirecto();
        mapToEntity(mensajeDirectoDTO, mensajeDirecto);
        return mensajeDirectoRepository.save(mensajeDirecto).getId();
    }

    public void update(final Long id, final MensajeDirectoDTO mensajeDirectoDTO) {
        final MensajeDirecto mensajeDirecto = mensajeDirectoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(mensajeDirectoDTO, mensajeDirecto);
        mensajeDirectoRepository.save(mensajeDirecto);
    }

    public void delete(final Long id) {
        mensajeDirectoRepository.deleteById(id);
    }

    private MensajeDirectoDTO mapToDTO(final MensajeDirecto mensajeDirecto,
            final MensajeDirectoDTO mensajeDirectoDTO) {
        mensajeDirectoDTO.setId(mensajeDirecto.getId());
        mensajeDirectoDTO.setContenido(mensajeDirecto.getContenido());
        mensajeDirectoDTO.setFechaContenido(mensajeDirecto.getFechaContenido());
        mensajeDirectoDTO.setIdUsuario(mensajeDirecto.getIdUsuario() == null ? null : mensajeDirecto.getIdUsuario().getId());
        return mensajeDirectoDTO;
    }

    private MensajeDirecto mapToEntity(final MensajeDirectoDTO mensajeDirectoDTO,
            final MensajeDirecto mensajeDirecto) {
        mensajeDirecto.setContenido(mensajeDirectoDTO.getContenido());
        mensajeDirecto.setFechaContenido(mensajeDirectoDTO.getFechaContenido());
        final Usuario idUsuario = mensajeDirectoDTO.getIdUsuario() == null ? null : usuarioRepository.findById(mensajeDirectoDTO.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("idUsuario not found"));
        mensajeDirecto.setIdUsuario(idUsuario);
        return mensajeDirecto;
    }

}
