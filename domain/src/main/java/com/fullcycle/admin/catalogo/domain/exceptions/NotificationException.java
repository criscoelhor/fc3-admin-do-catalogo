package com.fullcycle.admin.catalogo.domain.exceptions;

import com.fullcycle.admin.catalogo.domain.validation.handler.Notification;

import java.util.List;

public class NotificationException extends DomainException {
    protected NotificationException(String message, Notification notification) {
        super(message, notification.getErrors());
    }
}
