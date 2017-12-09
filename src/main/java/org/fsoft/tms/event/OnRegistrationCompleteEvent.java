package org.fsoft.tms.event;

import org.fsoft.tms.entity.User;
import org.springframework.context.ApplicationEvent;

/**
 * Created by Isabella on 8-Jun-2017.
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent{

    private User user;

    private String email;

    public OnRegistrationCompleteEvent(User user, String email) {
        super(user);
        this.user = user;
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
