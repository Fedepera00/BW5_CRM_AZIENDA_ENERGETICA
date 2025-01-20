package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.auth.entities;

import it.epicode.U2J_W4_D5_PROJECT.auth.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
public class AppUser implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Puoi personalizzarlo se necessario
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Puoi personalizzarlo se necessario
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Puoi personalizzarlo se necessario
    }

    @Override
    public boolean isEnabled() {
        return true; // Puoi personalizzarlo se necessario
    }
}


