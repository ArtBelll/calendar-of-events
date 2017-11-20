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
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private StatusOfOrganisation status;

    @OneToOne(mappedBy = "organisation")
    private User user;
}
