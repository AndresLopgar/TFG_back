package andres.art_connect.rest;

import andres.art_connect.model.SeguidoresDTO;
import andres.art_connect.service.SeguidoresService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/seguidores", produces = MediaType.APPLICATION_JSON_VALUE)
public class SeguidoresResource {

    private final SeguidoresService seguidoresService;

    public SeguidoresResource(final SeguidoresService seguidoresService) {
        this.seguidoresService = seguidoresService;
    }

    @GetMapping
    public ResponseEntity<List<SeguidoresDTO>> getAllSeguidoress() {
        return ResponseEntity.ok(seguidoresService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeguidoresDTO> getSeguidores(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(seguidoresService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSeguidores(
            @RequestBody @Valid final SeguidoresDTO seguidoresDTO) {
        final Long createdId = seguidoresService.create(seguidoresDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSeguidores(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SeguidoresDTO seguidoresDTO) {
        seguidoresService.update(id, seguidoresDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSeguidores(@PathVariable(name = "id") final Long id) {
        seguidoresService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
