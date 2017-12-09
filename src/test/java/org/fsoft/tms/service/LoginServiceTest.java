package org.fsoft.tms.service;

import org.fsoft.tms.entity.Role;
import org.fsoft.tms.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Isabella on 28-May-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    LoginService loginService;

//    @Test
//    public void loadUserByUsername() throws Exception {
//        UserDetails userDetails = loginService.loadUserByUsername("admin");
//
//        UserDetails userDetails1 = new UserDetails() {
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//                Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//
//                Role role = userRepository.findUserByUsername("admin").getRole();
//
//                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//                return grantedAuthorities;
//            }
//
//            @Override
//            public String getPassword() {
//                return userRepository.findUserByUsername("admin").getPassword();
//            }
//
//            @Override
//            public String getUsername() {
//                return userRepository.findUserByUsername("admin").getUsername();
//            }
//
//            @Override
//            public boolean isAccountNonExpired() {
//                return false;
//            }
//
//            @Override
//            public boolean isAccountNonLocked() {
//                return false;
//            }
//
//            @Override
//            public boolean isCredentialsNonExpired() {
//                return false;
//            }
//
//            @Override
//            public boolean isEnabled() {
//                return false;
//            }
//        };
//
//        assertEquals(userDetails1.getAuthorities(),userDetails.getAuthorities());
//        assertEquals(userDetails1.getPassword(),userDetails.getPassword());
//        assertEquals(userDetails1.getUsername(),userDetails.getUsername());
//    }
//
//    @Test
//    public void getRoleByUser() throws Exception {
//        Role actualRole = loginService.getRoleByUser("admin");
//        Role expectRole = userRepository.findUserByUsername("admin").getRole();
//        assertEquals(expectRole.getName(), actualRole.getName());
//    }

}