package ru.korbit.ceadmin.dto;

import lombok.Data;
import ru.korbit.cecommon.domain.User;
import ru.korbit.cecommon.packet.RoleOfUser;

import java.util.List;
import java.util.UUID;

@Data
public class ResponseUser {
    private String login;
    private UUID uuid;
    private List<RoleOfUser> roles;

    public ResponseUser(User user) {
        this.login = user.getLogin();
        this.uuid = user.getUuid();
        this.roles = user.getRoles();
    }
}
