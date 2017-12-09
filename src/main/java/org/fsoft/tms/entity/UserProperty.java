package org.fsoft.tms.entity;

import javax.persistence.*;

@Entity
@Table(name = "USERS_PROPERTIES")
@AssociationOverrides({
        @AssociationOverride(name = "pk.user",
                joinColumns = @JoinColumn(name = "USER_ID")),
        @AssociationOverride(name = "pk.property",
                joinColumns = @JoinColumn(name = "PROPERTY_ID")) })
public class UserProperty implements java.io.Serializable {

    private UserPropertyId pk = new UserPropertyId();
    private String value;

    public UserProperty() {
    }

    @EmbeddedId
    public UserPropertyId getPk() {
        return pk;
    }

    public void setPk(UserPropertyId pk) {
        this.pk = pk;
    }

    @Transient
    public User getUser() {
        return getPk().getUser();
    }

    public void setUser(User user) {
        getPk().setUser(user);
    }

    @Transient
    public Property getProperty() {
        return getPk().getProperty();
    }

    public void setProperty(Property property) {
        getPk().setProperty(property);
    }

    @Column(name = "VALUE", nullable = false)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserProperty that = (UserProperty) o;

        if (getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null)
            return false;

        return true;
    }

    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }
}