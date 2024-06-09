package com.example.Quan.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import com.example.Quan.mo.Users;

/**
 * @author Sampson Alfred
 */

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private Users user;
    private String confirmationUrl;

    public RegistrationCompleteEvent(Users user, String confirmationUrl) {
        super(user);
        this.user = user;
        this.confirmationUrl = confirmationUrl;
    }
}
