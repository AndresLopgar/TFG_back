package andres.art_connect.repos;

import andres.art_connect.domain.Compania;
import andres.art_connect.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findFirstByIdSeguidorAndIdNot(Usuario usuario, final Long id);

    Usuario findFirstByIdCompania(Compania compania);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByContrasenaIgnoreCase(String contrasena);

    boolean existsByCorreoElectronicoIgnoreCase(String correoElectronico);

    boolean existsByIdSeguidorId(Long id);

}
