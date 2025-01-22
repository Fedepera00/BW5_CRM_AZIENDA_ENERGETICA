package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadLogoRequest {
    @NotNull(message = "Il file è obbligatorio")
    private MultipartFile file;

    @NotNull(message = "Il clienteId è obbligatorio")
    private Long clienteId;
}