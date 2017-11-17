package ru.korbit.cecommon.domain;

import lombok.Data;
import ru.korbit.cecommon.packet.RoleOfUser;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private RoleOfUser role;
}
