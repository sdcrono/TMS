package org.fsoft.tms.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "PROPERTIES")
public class Property implements java.io.Serializable {

    private Integer id;
    private String name;
    private String description;
    private Set<UserProperty> userProperties = new HashSet<UserProperty>(0);

    public Property() {
    }

    public Property(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Property(String name, String description, Set<UserProperty> userProperties) {
        this.name = name;
        this.description = description;
        this.userProperties = userProperties;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer categoryId) {
        this.id = categoryId;
    }

    @Column(name = "NAME", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DESCRIPTION", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.property")
    public Set<UserProperty> getUserProperties() {
        return this.userProperties;
    }

    public void setUserProperties(Set<UserProperty> userProperties) {
        this.userProperties = userProperties;
    }

}