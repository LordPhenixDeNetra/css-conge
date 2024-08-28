package sn.css.c_s_s_conge.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.css.c_s_s_conge.model.DemandeCongeDTO;
import sn.css.c_s_s_conge.model.NMessageDTO;
import sn.css.c_s_s_conge.service.NMessageService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/messages", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
@RequiredArgsConstructor
public class NMessageResource {

    private final NMessageService messageService;

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMessage(@RequestBody @Valid final NMessageDTO nMessageDTO) {
        final Long createdId = messageService.create(nMessageDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @GetMapping("/{idSalarier}")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<List<NMessageDTO>> findAllMessageBySalarierId(@PathVariable Long idSalarier) {
        return ResponseEntity.ok(messageService.findAllMessageBySalarierId(idSalarier));
    }
}
