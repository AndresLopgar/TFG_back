package andres.art_connect.rest;

import andres.art_connect.model.CompaniaDTO;
import andres.art_connect.service.CompaniaService;
import andres.art_connect.util.ReferencedException;
import andres.art_connect.util.ReferencedWarning;
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
@RequestMapping(value = "/api/companias", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompaniaResource {

    private final CompaniaService companiaService;

    public CompaniaResource(final CompaniaService companiaService) {
        this.companiaService = companiaService;
    }

    @GetMapping
    public ResponseEntity<List<CompaniaDTO>> getAllCompanias() {
        return ResponseEntity.ok(companiaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompaniaDTO> getCompania(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(companiaService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCompania(@RequestBody @Valid final CompaniaDTO companiaDTO) {
        final Long createdId = companiaService.create(companiaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCompania(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final CompaniaDTO companiaDTO) {
        companiaService.update(id, companiaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCompania(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = companiaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        companiaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
