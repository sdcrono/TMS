package org.fsoft.tms.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by thehaohcm on 5/18/17.
 */
@Entity
@Table(name="ROLES")
public class Role implements Serializable{

    private Integer id;

    private String name;

    private Set<Permission> permissions;

    private Set<User> users;

    public Role(){

    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "ROLES_PERMISSIONS", joinColumns = { @JoinColumn(name = "ROLE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "PERMISSION_ID", nullable = false, updatable = false) })
    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
