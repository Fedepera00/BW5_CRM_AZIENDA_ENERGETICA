package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadLogoRequest {
    private MultipartFile logoFile;
}