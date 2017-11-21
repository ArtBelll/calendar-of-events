package ru.korbit.cecommon.domain;

import lombok.*;
import ru.korbit.cecommon.packet.RoleOfUser;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@ToString(exclude = {"organisation"})
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private UUID uuid = UUID.randomUUID();

    private LocalDateTime created = LocalDateTime.now();

    private LocalDateTime logged = LocalDateTime.now();

    @OneToOne
    private Organisation organisation;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<RoleOfUser> roles = new ArrayList<>();

    public User(String email, String password, Organisation organisation) {
        this.email = email;
        this.password = password;
        this.organisation = organisation;
    }
}
