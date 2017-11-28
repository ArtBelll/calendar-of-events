package ru.korbit.cecommon.domain;

import lombok.Data;
import ru.korbit.cecommon.packet.StatusOfOrganisation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private String legalName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "working_hours")
    private String workingHours;

    private String type;

    @ElementCollection
    private Set<String> address = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private StatusOfOrganisation status;

    @OneToOne(mappedBy = "organisation")
    private User user;

    @OneToMany(mappedBy = "organisation")
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "organisation")
    private Set<Contact> contacts = new HashSet<>();
}
