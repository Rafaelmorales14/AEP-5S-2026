package aep.SOSsego.models;

import aep.SOSsego.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_user")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Column(unique = true)
    private String cpf;
    private String contact;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == RoleEnum.ADMIN) {
            return List.of(
                    new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_ADMIN"),
                    new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_SERVIDOR_PUBLICO"),
                    new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_CIDADAO")
            );
        } else if (this.role == RoleEnum.SERVIDOR_PUBLICO) {
            return List.of(
                    new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_SERVIDOR_PUBLICO")
            );
        } else {
            return List.of(
                    new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_CIDADAO")
            );
        }
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
