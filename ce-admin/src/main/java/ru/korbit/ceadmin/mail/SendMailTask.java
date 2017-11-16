package ru.korbit.ceadmin.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.korbit.cecommon.dao.EmailDao;

@Component
@Slf4j
public class SendMailTask {

    private static final long SLEEP_TIME = 10 * 60 * 1000;  // Ten minutes.

    private static final int MAX_EMAILS_PER_ITERATION = 100;

    private final MailSenderHolder sender;

    private EmailDao emailDao;

    @Autowired
    public SendMailTask(MailSenderHolder sender, EmailDao emailDao) {
        this.sender = sender;
        this.emailDao = emailDao;
    }

    @Scheduled(fixedDelay = SLEEP_TIME)
    @Transactional
    public void run() {
        log.info("Mail task run");
        emailDao.pop(MAX_EMAILS_PER_ITERATION)
                .forEach(email -> {
                    if (sender.sendEmail(email)) {
                        emailDao.delete(email);
                    }
                });
    }
}
