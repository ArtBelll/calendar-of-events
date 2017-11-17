package ru.korbit.cecommon.domain;

import lombok.Data;
import ru.korbit.cecommon.packet.StatusOfOrganisation;

import javax.persistence.*;

@Data
@Entity
@Table(name = "organisations")
public class Organisation {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private StatusOfOrganisation status;

    @OneToOne(mappedBy = "organisation")
    private User user;
}
