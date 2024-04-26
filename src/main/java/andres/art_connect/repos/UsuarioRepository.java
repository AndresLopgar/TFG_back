package andres.art_connect.repos;

import andres.art_connect.domain.Compania;
import andres.art_connect.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findFirstByIdComapania(Compania compania);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByContrasenaIgnoreCase(String contrasena);

    boolean existsByCorreoElectronicoIgnoreCase(String correoElectronico);

}
