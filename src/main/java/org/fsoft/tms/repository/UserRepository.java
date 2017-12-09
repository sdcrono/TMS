package org.fsoft.tms.repository;

import org.fsoft.tms.entity.Role;
import org.fsoft.tms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by DELL on 5/25/2017.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findAllById(Integer id);
    public User findUserByUsername(String username);
    public List<User> findAllByRole(Role role);
    public List<User> findAllByRoleAndManager(Role role, User user);
    public User findAllByUsername(String username);
    public  int  countByUsername(String usename);
}

