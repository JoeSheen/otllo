package com.shoejs.otllo.api.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoejs.otllo.api.common.AbsBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends AbsBaseEntity {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Past
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Email
    private String email;

    private String phoneNumber;

    @Column(unique = true)
    private String username;

    @Size(min = 8)
    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String profileImagePath;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "friends", joinColumns =
    @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    private Set<User> friends;

    private boolean visible;
}
