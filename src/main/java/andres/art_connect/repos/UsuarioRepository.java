package andres.art_connect.repos;

import andres.art_connect.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByContrasenaIgnoreCase(String contrasena);

    boolean existsByCorreoElectronicoIgnoreCase(String correoElectronico);
    
    @Query("SELECT u.publicacionesLiked FROM Usuario u WHERE u.id = :userId")
    int[] findPublicacionesLikedByUserId(Long userId);

}
