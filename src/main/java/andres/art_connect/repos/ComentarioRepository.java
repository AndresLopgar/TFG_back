package andres.art_connect.repos;

import andres.art_connect.domain.Comentario;
import andres.art_connect.domain.Publicacion;
import andres.art_connect.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    Comentario findFirstByIdUsuario(Usuario usuario);

    Comentario findFirstByIdPublicacion(Publicacion publicacion);

}
