package org.fsoft.tms.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fsoft.tms.entity.Permission;
import org.fsoft.tms.entity.Role;
import org.fsoft.tms.entity.User;
import org.fsoft.tms.repository.UserRepository;
import org.fsoft.tms.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by thehaohcm on 5/30/17.
 */
@Service
public class AuthenticationServiceImpl implements UserDetailsService,LoginService {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        logger.debug("username nhận được:" + s);
        User user = userRepository.findUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        Role role = user.getRole();

        Set<Permission> permissions = role.getPermissions();

        //logger.debug(permissions.toString());

        for(Permission permission : permissions)
        {
            logger.debug(permission.getName());
            grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
        }
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), grantedAuthorities);

    }

    public User getUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findAllByUsername(username);
    }
}
