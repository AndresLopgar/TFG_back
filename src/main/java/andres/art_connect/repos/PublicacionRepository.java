package andres.art_connect.repos;

import andres.art_connect.domain.Publicacion;
import andres.art_connect.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    Publicacion findFirstByIdUsuario(Usuario usuario);

}
