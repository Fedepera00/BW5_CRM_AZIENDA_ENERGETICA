package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "societa_loghi")
public class SocietaLogo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String imageUrl;

    @JsonIgnore
    @OneToOne
    Cliente cliente;




}
