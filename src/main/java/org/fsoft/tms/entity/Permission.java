package org.fsoft.tms.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by thehaohcm on 5/19/17.
 */
@Entity
@Table(name="PERMISSIONS")
public class Permission implements Serializable{

    private Integer id;

    private String name;

    private String description;


    private Set<Role> roles;

    public Permission(){

    }

    public Permission(String name, String description, Set<Role> roles) {
        this.name = name;
        this.description = description;
        this.roles = roles;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "permissions")
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
