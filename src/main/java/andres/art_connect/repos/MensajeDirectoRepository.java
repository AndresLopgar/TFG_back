package andres.art_connect.repos;

import andres.art_connect.domain.MensajeDirecto;
import andres.art_connect.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MensajeDirectoRepository extends JpaRepository<MensajeDirecto, Long> {

	MensajeDirecto findFirstByRemiteUsuarioOrDestinoUsuario(Usuario remiteUsuario, Usuario destinoUsuario);

}
