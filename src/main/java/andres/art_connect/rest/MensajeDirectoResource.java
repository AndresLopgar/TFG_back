package andres.art_connect.rest;

import andres.art_connect.model.MensajeDirectoDTO;
import andres.art_connect.service.MensajeDirectoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/mensajeDirectos", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class MensajeDirectoResource {

    private final MensajeDirectoService mensajeDirectoService;

    public MensajeDirectoResource(final MensajeDirectoService mensajeDirectoService) {
        this.mensajeDirectoService = mensajeDirectoService;
    }

    @GetMapping
    public ResponseEntity<List<MensajeDirectoDTO>> getAllMensajeDirectos() {
        return ResponseEntity.ok(mensajeDirectoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDirectoDTO> getMensajeDirecto(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(mensajeDirectoService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createMensajeDirecto(
            @RequestBody @Valid final MensajeDirectoDTO mensajeDirectoDTO) {
        final Long createdId = mensajeDirectoService.create(mensajeDirectoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateMensajeDirecto(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final MensajeDirectoDTO mensajeDirectoDTO) {
        mensajeDirectoService.update(id, mensajeDirectoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMensajeDirecto(@PathVariable(name = "id") final Long id) {
        mensajeDirectoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
