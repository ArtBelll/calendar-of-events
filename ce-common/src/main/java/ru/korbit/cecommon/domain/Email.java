package ru.korbit.cecommon.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.korbit.cecommon.packet.TypeOfMail;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Sergey Ignatov on 31/10/16.
 */
@Data
@Entity
@Table(name = "emails")
@NoArgsConstructor
@RequiredArgsConstructor
public class Email {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String subject;

    @NonNull
    private String recipient;

    @NonNull
    private String body;

    private int attempts = 0;

    private LocalDateTime firstAttempt = LocalDateTime.now();

    private LocalDateTime lastAttempt = LocalDateTime.now();

    public void increaseAttempts() {
        attempts++;
    }
}
