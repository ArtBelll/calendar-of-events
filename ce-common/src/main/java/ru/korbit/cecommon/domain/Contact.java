package ru.korbit.cecommon.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
@Getter @Setter
@EqualsAndHashCode(exclude = "organisation")
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String type;

    @NonNull
    private String value;

    private String description;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;
}
