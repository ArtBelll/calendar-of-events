package ru.korbit.ceadmin.dto;

import lombok.Data;

@Data
public class RequestUser {
    private String login;
    private String password;
}
