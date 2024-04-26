package andres.art_connect.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SeguidoresDTO {

    private Long id;

    @NotNull
    private Long idSeguidor;

    @NotNull
    private Long idSeguido;

}
