package andres.art_connect.repos;

import andres.art_connect.domain.Compania;
import andres.art_connect.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompaniaRepository extends JpaRepository<Compania, Long> {

    Compania findFirstByIdCreador(Usuario usuario);

    boolean existsByNombreIgnoreCase(String nombre);

}
