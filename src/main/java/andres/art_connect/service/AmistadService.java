package andres.art_connect.service;

import andres.art_connect.domain.Amistad;
import andres.art_connect.domain.Usuario;
import andres.art_connect.model.AmistadDTO;
import andres.art_connect.repos.AmistadRepository;
import andres.art_connect.repos.UsuarioRepository;
import andres.art_connect.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AmistadService {

    private final AmistadRepository amistadRepository;
    private final UsuarioRepository usuarioRepository;

    public AmistadService(final AmistadRepository amistadRepository,
            final UsuarioRepository usuarioRepository) {
        this.amistadRepository = amistadRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<AmistadDTO> findAll() {
        final List<Amistad> amistads = amistadRepository.findAll(Sort.by("id"));
        return amistads.stream()
                .map(amistad -> mapToDTO(amistad, new AmistadDTO()))
                .toList();
    }

    public AmistadDTO get(final Long id) {
        return amistadRepository.findById(id)
                .map(amistad -> mapToDTO(amistad, new AmistadDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AmistadDTO amistadDTO) {
        final Amistad amistad = new Amistad();
        mapToEntity(amistadDTO, amistad);
        return amistadRepository.save(amistad).getId();
    }

    public void update(final Long id, final AmistadDTO amistadDTO) {
        final Amistad amistad = amistadRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(amistadDTO, amistad);
        amistadRepository.save(amistad);
    }

    public void delete(final Long id) {
        amistadRepository.deleteById(id);
    }
    
    public List<AmistadDTO> findBySeguidor(Long idSeguidor) {
        List<Amistad> amistades = amistadRepository.findByIdSeguidor_Id(idSeguidor);
        return amistades.stream()
                .map(amistad -> mapToDTO(amistad, new AmistadDTO()))
                .collect(Collectors.toList());
    }

    public List<AmistadDTO> findBySeguido(Long idSeguido) {
        List<Amistad> amistades = amistadRepository.findByIdSeguido_Id(idSeguido);
        return amistades.stream()
                .map(amistad -> mapToDTO(amistad, new AmistadDTO()))
                .collect(Collectors.toList());
    }

    private AmistadDTO mapToDTO(final Amistad amistad, final AmistadDTO amistadDTO) {
        amistadDTO.setId(amistad.getId());
        amistadDTO.setIdSeguidor(amistad.getIdSeguidor() == null ? null : amistad.getIdSeguidor().getId());
        amistadDTO.setIdSeguido(amistad.getIdSeguido() == null ? null : amistad.getIdSeguido().getId());
        return amistadDTO;
    }

    private Amistad mapToEntity(final AmistadDTO amistadDTO, final Amistad amistad) {
        final Usuario idSeguidor = amistadDTO.getIdSeguidor() == null ? null : usuarioRepository.findById(amistadDTO.getIdSeguidor())
                .orElseThrow(() -> new NotFoundException("idSeguidor not found"));
        amistad.setIdSeguidor(idSeguidor);
        final Usuario idSeguido = amistadDTO.getIdSeguido() == null ? null : usuarioRepository.findById(amistadDTO.getIdSeguido())
                .orElseThrow(() -> new NotFoundException("idSeguido not found"));
        amistad.setIdSeguido(idSeguido);
        return amistad;
    }

}
