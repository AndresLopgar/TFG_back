package andres.art_connect.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import andres.art_connect.domain.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
	List<Notificacion> findByIdUsuarioEmisor_Id(Long idUsuarioEmisor);
    List<Notificacion> findByIdUsuarioRemitente_Id(Long idUsuarioRemitente);

}
