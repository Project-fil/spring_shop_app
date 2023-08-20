package com.github.ratel.services;

public interface SendGridMailService {

    void sendMessage(String toAddress, String subject, String text);

}
