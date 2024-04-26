package andres.art_connect.repos;

import andres.art_connect.domain.Seguidores;
import andres.art_connect.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SeguidoresRepository extends JpaRepository<Seguidores, Long> {

    Seguidores findFirstByIdSeguidor(Usuario usuario);

    Seguidores findFirstByIdSeguido(Usuario usuario);

}
