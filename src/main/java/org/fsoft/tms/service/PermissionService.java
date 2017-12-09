package org.fsoft.tms.service;

import org.fsoft.tms.entity.Permission;

import java.util.List;

/**
 * Created by Isabella on 29-May-2017.
 */
public interface PermissionService {

    List<Permission> getAllPermission();

    void addPermission(Permission role);

    Permission findOnePermisison(int id);

    void updatePermission(Permission c);

    void addRole();
}
