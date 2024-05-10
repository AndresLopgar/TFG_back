package andres.art_connect.repos;

import andres.art_connect.domain.Amistad;
import andres.art_connect.domain.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AmistadRepository extends JpaRepository<Amistad, Long> {

    Amistad findFirstByIdSeguidor(Usuario usuario);

    Amistad findFirstByIdSeguido(Usuario usuario);

	List<Amistad> findByIdSeguidor_Id(Long idSeguidor);

	List<Amistad> findByIdSeguido_Id(Long idSeguido);

}
