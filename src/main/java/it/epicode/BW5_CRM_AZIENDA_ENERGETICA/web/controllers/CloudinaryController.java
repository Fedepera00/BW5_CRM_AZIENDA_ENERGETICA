package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    @Operation(
            summary = "Carica il logo del cliente",
            description = "Endpoint per caricare il logo dell'azienda."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Immagine caricata "),
            @ApiResponse(responseCode = "400", description = "Richiesta non valida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Errore del server", content = @Content)
    })
    @PostMapping(value = "/clienteLogo/{id}/profile-image", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> uploadProfileImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Nessun file selezionato per l'upload");
        }
        try {

            String imageUrl = cloudinaryService.uploadImage(file);

            return ResponseEntity.ok("Immagine caricata con successo: " + imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante l'upload dell'immagine: " + e.getMessage());
        }
    }
}