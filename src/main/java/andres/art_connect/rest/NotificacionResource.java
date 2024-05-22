package andres.art_connect.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import andres.art_connect.model.NotificacionDTO;
import andres.art_connect.service.NotificacionService;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(value = "/api/notificaciones", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class NotificacionResource {

    private final NotificacionService notificacionService;

    public NotificacionResource(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> getAllNotificaciones() {
        List<NotificacionDTO> notificaciones = notificacionService.findAll();
        return ResponseEntity.ok(notificaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionDTO> getNotificacion(@PathVariable(name = "id") Long id) {
        NotificacionDTO notificacion = notificacionService.findById(id);
        return ResponseEntity.ok(notificacion);
    }

    @PostMapping
    public ResponseEntity<Long> createNotificacion(@RequestBody @Valid NotificacionDTO notificacionDTO) {
        Long createdId = notificacionService.create(notificacionDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateNotificacion(@PathVariable(name = "id") Long id,
                                                   @RequestBody @Valid NotificacionDTO notificacionDTO) {
        notificacionService.update(id, notificacionDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable(name = "id") Long id) {
        notificacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/remitente/{idUsuarioRemitente}")
    public ResponseEntity<List<NotificacionDTO>> getNotificacionesByUsuarioRemitente(@PathVariable Long idUsuarioRemitente) {
        List<NotificacionDTO> notificaciones = notificacionService.findAllByUsuarioRemitente(idUsuarioRemitente);
        return ResponseEntity.ok(notificaciones);
    }
}

