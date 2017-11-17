package ru.korbit.cecommon.packet;

public enum RoleOfUser {
    SUPERUSER(0),
    ADMIN(1),
    MODER(2);

    private int roleIndex;

    RoleOfUser(int roleIndex) {
        this.roleIndex = roleIndex;
    }

    public int getRoleIndex() {
        return roleIndex;
    }
}
