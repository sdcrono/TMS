package org.fsoft.tms.service;

import org.fsoft.tms.entity.Role;

import java.util.List;

/**
 * Created by Isabella on 29-May-2017.
 */
public interface RoleService {

    List<Role> getAllRole();

    void addRole(Role role);

    Role findOneRole(int id);

    void updateRole(Role c);

    void addPermissionForRole();
}
