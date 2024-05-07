package andres.art_connect.repos;

import andres.art_connect.domain.Amistad;
import andres.art_connect.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AmistadRepository extends JpaRepository<Amistad, Long> {

    Amistad findFirstByIdSeguidor(Usuario usuario);

    Amistad findFirstByIdSeguido(Usuario usuario);

}
