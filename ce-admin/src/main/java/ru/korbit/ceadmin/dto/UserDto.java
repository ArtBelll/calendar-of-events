package ru.korbit.ceadmin.dto;

import lombok.Data;
import ru.korbit.cecommon.domain.User;
import ru.korbit.cecommon.packet.RoleOfUser;

import java.util.List;
import java.util.UUID;

@Data
public class UserDto {
    private String email;
    private UUID uuid;
    private List<RoleOfUser> roles;

    public UserDto(User user) {
        this.email = user.getEmail();
        this.uuid = user.getUuid();
        this.roles = user.getRoles();
    }
}
