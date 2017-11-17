package ru.korbit.cecommon.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.korbit.cecommon.packet.RoleOfUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String login;

    @NonNull
    private String password;

    @Column(unique = true)
    private UUID uuid = UUID.randomUUID();

    @NonNull
    @OneToOne
    private Organisation organisation;

    @ElementCollection
    private List<RoleOfUser> roles = new ArrayList<>();
}
