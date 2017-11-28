package ru.korbit.cecommon.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
@Data
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
