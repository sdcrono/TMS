package org.fsoft.tms.validation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Isabella on 2-Jun-2017.
 */
public class UserValidation {

    @Size(min=4, max=35)
    @NotNull
    private String username;

    @Size(min=4, max= 10)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
