package com.shoejs.otllo.api.connection;

import com.shoejs.otllo.api.common.AbsBaseEntity;
import com.shoejs.otllo.api.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Connection extends AbsBaseEntity {

    @NotNull
    private ConnectionStatus status;

    @NotNull
    @ManyToOne
    private User firstUser;

    @NotNull
    @ManyToOne
    private User secondUser;
}
