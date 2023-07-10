package dev.konstantinou.urlshortener.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.konstantinou.urlshortener.enums.Privileges;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private Set<Privileges> userPrivileges;
    @Column(columnDefinition = "TINYINT(3) NOT NULL DEFAULT TRUE")
    private boolean accountNonExpired;
    @Column(columnDefinition = "TINYINT(3) NOT NULL DEFAULT TRUE")
    private boolean accountNonLocked;
    @Column(columnDefinition = "TINYINT(3) NOT NULL DEFAULT TRUE")
    private boolean credentialsNonExpired;
    @Column(columnDefinition = "TINYINT(3) NOT NULL DEFAULT TRUE")
    private boolean enabled;


    public ApplicationUser(String username, List<String> privileges) {
        this.username = username;
        this.userPrivileges = privileges.stream().map(Privileges::valueOf).collect(Collectors.toSet());
    }

    public ApplicationUser(String username, String password) {
        this.username = username;
        this.password = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8().encode(password);
    }

    public static ApplicationUser CreateNewApplicationUser(String username, String password) {
        ApplicationUser u = new ApplicationUser(username, password);
        u.username = username;
        u.password = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8().encode(password);
        u.accountNonExpired = true;
        u.accountNonLocked = true;
        u.credentialsNonExpired = true;
        u.enabled = true;
        return u;
    }


    private Set<GrantedAuthority> convertPrivilegesToAuthorities() {
        //todo fix this
        if (userPrivileges == null) {
            return new HashSet<>();
        }
        return userPrivileges.stream().map(p -> new SimpleGrantedAuthority(p.toString())).collect(Collectors.toSet());

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return convertPrivilegesToAuthorities();
    }

}
