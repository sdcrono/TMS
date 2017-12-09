package org.fsoft.tms.repository;

import org.fsoft.tms.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Created by DELL on 5/24/2017.
 */
public interface PermissionRepository extends JpaRepository<Permission,Integer> {
    public Set<Permission> findPermissionById(Integer id);
}
