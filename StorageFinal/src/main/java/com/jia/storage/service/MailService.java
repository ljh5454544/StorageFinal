package com.jia.storage.service;

import org.springframework.amqp.core.Message;

public interface MailService {

    void sendRegistMail(Message message);

    void sendDeleteMail(Message message);
}
