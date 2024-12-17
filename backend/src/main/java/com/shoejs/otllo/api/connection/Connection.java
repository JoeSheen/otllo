package com.shoejs.otllo.api.connection;

import com.shoejs.otllo.api.common.AbsBaseEntity;
import jakarta.persistence.Entity;
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
}
