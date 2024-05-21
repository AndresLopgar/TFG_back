package andres.art_connect.rest;

import andres.art_connect.model.UsuarioDTO;
import andres.art_connect.service.UsuarioService;
import andres.art_connect.util.ReferencedException;
import andres.art_connect.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class UsuarioResource {

    private final UsuarioService usuarioService;

    public UsuarioResource(final UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(usuarioService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUusario(
            @RequestBody @Valid final UsuarioDTO usuarioDto) {
        final Long createdId = usuarioService.create(usuarioDto);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Long> updateUsuario(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final UsuarioDTO usuarioDTO) throws IOException {
        usuarioService.update(id, usuarioDTO);
        return ResponseEntity.ok(id);
    }    

    @DeleteMapping("/delete/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUsuario(@PathVariable(name = "id") final Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/update/{id}/companiaSeguida")
    public ResponseEntity<Void> updateCompaniaSeguida(@PathVariable(name = "id") final Long id,
            @RequestParam(name = "companiaSeguida") Long nuevaCompaniaSeguida) {
        usuarioService.updateCompaniaSeguida(id, nuevaCompaniaSeguida);
        return ResponseEntity.ok().build();
    }



}
