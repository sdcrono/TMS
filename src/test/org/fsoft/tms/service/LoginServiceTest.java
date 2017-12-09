package org.fsoft.tms.service;

import org.fsoft.tms.entity.Role;
import org.fsoft.tms.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

/**
 * Created by thehaohcm on 5/27/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginService loginService;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Before
//    public void setUp(){
//        given(this.loginService.loadUserByUsername("admin")).willReturn(
//            new UserDetails() {
//                @Override
//                public Collection<? extends GrantedAuthority> getAuthorities() {
//                    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//
//                    Role role = userRepository.findUserByUsername("admin").getRole();
//
//                    grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//                    return grantedAuthorities;
//                }
//
//                @Override
//                public String getPassword() {
//                    return userRepository.findUserByUsername("admin").getPassword();
//                }
//
//                @Override
//                public String getUsername() {
//                    return userRepository.findUserByUsername("admin").getUsername();
//                }
//
//                @Override
//                public boolean isAccountNonExpired() {
//                    return false;
//                }
//
//                @Override
//                public boolean isAccountNonLocked() {
//                    return false;
//                }
//
//                @Override
//                public boolean isCredentialsNonExpired() {
//                    return false;
//                }
//
//                @Override
//                public boolean isEnabled() {
//                    return false;
//                }
//            }
//        );
//    }

    @Test
    public void loadUserByUsernameTest(){

        UserDetails userDetails = loginService.loadUserByUsername("admin");

        UserDetails userDetails1 = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

                Role role = userRepository.findUserByUsername("admin").getRole();

                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
                return grantedAuthorities;
            }

            @Override
            public String getPassword() {
                return userRepository.findUserByUsername("admin").getPassword();
            }

            @Override
            public String getUsername() {
                return userRepository.findUserByUsername("admin").getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                    return false;
                }

                @Override
                public boolean isAccountNonLocked() {
                    return false;
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return false;
                }

                @Override
                public boolean isEnabled() {
                    return false;
            }
        };

        assertEquals(userDetails1.getAuthorities(),userDetails.getAuthorities());
        assertEquals(userDetails1.getPassword(),userDetails.getPassword());
        assertEquals(userDetails1.getUsername(),userDetails.getUsername());
//        this.restTemplate.getForEntity("/testlogin", String.class);

    }

}