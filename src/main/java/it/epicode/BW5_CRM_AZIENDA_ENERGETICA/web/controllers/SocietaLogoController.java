package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.controllers;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.SocietaLogo;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.CloudinaryService;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.SocietaLogoService;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.UserRoleService;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto.UploadLogoRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/societa_loghi")
@PreAuthorize("isAuthenticated()")
public class SocietaLogoController {

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    SocietaLogoService societaLogoService;

    @Autowired
    UserRoleService userRoleService;


    @PostMapping(path = "/upload", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Map> uploadFile(@Valid @ModelAttribute UploadLogoRequest request , @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        if (request.getFile().isEmpty()) {
            throw new IllegalArgumentException("Il file non può essere vuoto");
        }

        String folder = "societa-logo";
        Map result = cloudinaryService.uploader(request.getFile(), folder);
        String imageUrl = (String) result.get("url");

        String username = user.getUsername();

        userRoleService.getUsernameForAll(username);

        societaLogoService.save(request, imageUrl); // Passa l'URL dell'immagine

        return ResponseEntity.ok(result);
    }

}

