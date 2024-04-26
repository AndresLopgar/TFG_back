package andres.art_connect.service;

import andres.art_connect.domain.Seguidores;
import andres.art_connect.domain.Usuario;
import andres.art_connect.model.SeguidoresDTO;
import andres.art_connect.repos.SeguidoresRepository;
import andres.art_connect.repos.UsuarioRepository;
import andres.art_connect.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SeguidoresService {

    private final SeguidoresRepository seguidoresRepository;
    private final UsuarioRepository usuarioRepository;

    public SeguidoresService(final SeguidoresRepository seguidoresRepository,
            final UsuarioRepository usuarioRepository) {
        this.seguidoresRepository = seguidoresRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<SeguidoresDTO> findAll() {
        final List<Seguidores> seguidoreses = seguidoresRepository.findAll(Sort.by("id"));
        return seguidoreses.stream()
                .map(seguidores -> mapToDTO(seguidores, new SeguidoresDTO()))
                .toList();
    }

    public SeguidoresDTO get(final Long id) {
        return seguidoresRepository.findById(id)
                .map(seguidores -> mapToDTO(seguidores, new SeguidoresDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SeguidoresDTO seguidoresDTO) {
        final Seguidores seguidores = new Seguidores();
        mapToEntity(seguidoresDTO, seguidores);
        return seguidoresRepository.save(seguidores).getId();
    }

    public void update(final Long id, final SeguidoresDTO seguidoresDTO) {
        final Seguidores seguidores = seguidoresRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(seguidoresDTO, seguidores);
        seguidoresRepository.save(seguidores);
    }

    public void delete(final Long id) {
        seguidoresRepository.deleteById(id);
    }

    private SeguidoresDTO mapToDTO(final Seguidores seguidores, final SeguidoresDTO seguidoresDTO) {
        seguidoresDTO.setId(seguidores.getId());
        seguidoresDTO.setIdSeguidor(seguidores.getIdSeguidor() == null ? null : seguidores.getIdSeguidor().getId());
        seguidoresDTO.setIdSeguido(seguidores.getIdSeguido() == null ? null : seguidores.getIdSeguido().getId());
        return seguidoresDTO;
    }

    private Seguidores mapToEntity(final SeguidoresDTO seguidoresDTO, final Seguidores seguidores) {
        final Usuario idSeguidor = seguidoresDTO.getIdSeguidor() == null ? null : usuarioRepository.findById(seguidoresDTO.getIdSeguidor())
                .orElseThrow(() -> new NotFoundException("idSeguidor not found"));
        seguidores.setIdSeguidor(idSeguidor);
        final Usuario idSeguido = seguidoresDTO.getIdSeguido() == null ? null : usuarioRepository.findById(seguidoresDTO.getIdSeguido())
                .orElseThrow(() -> new NotFoundException("idSeguido not found"));
        seguidores.setIdSeguido(idSeguido);
        return seguidores;
    }

}
