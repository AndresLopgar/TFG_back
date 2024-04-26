package andres.art_connect.repos;

import andres.art_connect.domain.Compania;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompaniaRepository extends JpaRepository<Compania, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

}
