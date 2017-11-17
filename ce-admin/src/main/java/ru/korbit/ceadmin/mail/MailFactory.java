package ru.korbit.ceadmin.mail;

import ru.korbit.cecommon.domain.Email;

public abstract class MailFactory {

    private static final String REQUEST_SUBJECT = "Request for rights";

    public static Email getRequestMail(String email) {
        // TODO: change url to url page with list requests for admin rights
        return new Email(REQUEST_SUBJECT, email, "Check page korbit.ru");
    }

    public static Email getAcceptedMail(String email, String password) {
        return new Email(REQUEST_SUBJECT, email,
                String.format("Your request has been approved. Your password %s", password));
    }

    public static Email getRejectedMail(String email, String cause) {
        return new Email(REQUEST_SUBJECT, email,
                String.format("Your request has been rejected. Cause: \n%s", cause));
    }

}
