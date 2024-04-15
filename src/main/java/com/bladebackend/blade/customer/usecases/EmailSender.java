package com.bladebackend.blade.customer.usecases;

public interface EmailSender {
    void send(String to, String email);
}
