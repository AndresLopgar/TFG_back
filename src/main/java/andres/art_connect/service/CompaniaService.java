package andres.art_connect.service;

import andres.art_connect.domain.Compania;
import andres.art_connect.domain.Usuario;
import andres.art_connect.model.CompaniaDTO;
import andres.art_connect.repos.CompaniaRepository;
import andres.art_connect.repos.UsuarioRepository;
import andres.art_connect.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CompaniaService {

    private final CompaniaRepository companiaRepository;
    private final UsuarioRepository usuarioRepository;

    public CompaniaService(final CompaniaRepository companiaRepository,
            final UsuarioRepository usuarioRepository) {
        this.companiaRepository = companiaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<CompaniaDTO> findAll() {
        final List<Compania> companias = companiaRepository.findAll(Sort.by("id"));
        return companias.stream()
                .map(compania -> mapToDTO(compania, new CompaniaDTO()))
                .toList();
    }

    public CompaniaDTO get(final Long id) {
        return companiaRepository.findById(id)
                .map(compania -> mapToDTO(compania, new CompaniaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CompaniaDTO companiaDTO) {
        final Compania compania = new Compania();
        mapToEntity(companiaDTO, compania);
        return companiaRepository.save(compania).getId();
    }

    public void update(final Long id, final CompaniaDTO companiaDTO) {
        final Compania compania = companiaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(companiaDTO, compania);
        companiaRepository.save(compania);
    }

    public void delete(final Long id) {
        companiaRepository.deleteById(id);
    }

    private CompaniaDTO mapToDTO(final Compania compania, final CompaniaDTO companiaDTO) {
        companiaDTO.setId(compania.getId());
        companiaDTO.setNombre(compania.getNombre());
        companiaDTO.setDescripcion(compania.getDescripcion());
        companiaDTO.setMiembros(compania.getMiembros());
        companiaDTO.setFechaCreacion(compania.getFechaCreacion());
        companiaDTO.setIdUsuario(compania.getIdUsuario() == null ? null : compania.getIdUsuario().getId());
        return companiaDTO;
    }

    private Compania mapToEntity(final CompaniaDTO companiaDTO, final Compania compania) {
        compania.setNombre(companiaDTO.getNombre());
        compania.setDescripcion(companiaDTO.getDescripcion());
        compania.setMiembros(companiaDTO.getMiembros());
        compania.setFechaCreacion(companiaDTO.getFechaCreacion());
        final Usuario idUsuario = companiaDTO.getIdUsuario() == null ? null : usuarioRepository.findById(companiaDTO.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("idUsuario not found"));
        compania.setIdUsuario(idUsuario);
        return compania;
    }

    public boolean nombreExists(final String nombre) {
        return companiaRepository.existsByNombreIgnoreCase(nombre);
    }

}
