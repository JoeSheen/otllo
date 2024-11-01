package com.shoejs.otllo.api.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoejs.otllo.api.annotation.UsernameValidation;
import com.shoejs.otllo.api.common.AbsBaseEntity;
import com.shoejs.otllo.api.post.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Class encapsulating user entity data
 */
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends AbsBaseEntity implements UserDetails {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Past
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Email
    @Column(unique = true)
    private String email;

    private String phoneNumber;

    @Column(unique = true)
    @UsernameValidation
    private String username;

    @Size(min = 8)
    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String profileImagePath;

    private boolean visible;

    @Size(max = 500)
    private String status;

    @OneToMany
    private Set<Post> posts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
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
