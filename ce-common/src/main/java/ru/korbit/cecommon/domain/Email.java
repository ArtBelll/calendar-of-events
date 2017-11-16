package ru.korbit.cecommon.domain;

import lombok.Data;
import ru.korbit.cecommon.packet.TypeOfMail;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Sergey Ignatov on 31/10/16.
 */
@Data
@Entity
@Table(name = "emails")
public class Email {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String password;

    private String recipient;

    private int attempts = 0;

    @Enumerated(EnumType.STRING)
    private TypeOfMail type;

    private LocalDateTime firstAttempt = LocalDateTime.now();

    private LocalDateTime lastAttempt = LocalDateTime.now();

    private String body;

    @OneToOne
    @JoinColumn(name = "organisation")
    private Organisation organisation;
}
