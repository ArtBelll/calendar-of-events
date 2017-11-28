package ru.korbit.cecommon.domain;

import lombok.*;
import ru.korbit.cecommon.packet.StatusOfOrganisation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "organisations")
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"addresses", "contacts"})
public class Organisation {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(unique = true, nullable = false)
    private String legalName;

    @NonNull
    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "working_hours")
    private String workingHours;

    @NonNull
    @Column(nullable = false)
    private String owner;

    @NonNull
    @Column(nullable = false)
    private String type;

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
