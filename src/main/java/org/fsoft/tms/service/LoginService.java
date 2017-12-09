package org.fsoft.tms.service;

import org.fsoft.tms.entity.Role;
import org.fsoft.tms.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by Isabella on 29-May-2017.
 */
public interface LoginService {

    User getUserByUsername(String username);

    User findUserByUsername(String username);
}
