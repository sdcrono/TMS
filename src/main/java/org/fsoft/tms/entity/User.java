package org.fsoft.tms.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "USERS")
public class User implements java.io.Serializable {

    private Integer id;
    private String username;
    private String password;
    private boolean active;

    private Set<UserProperty> userProperties = new HashSet<UserProperty>(0);

    private Role role;

    private User manager;

    private Set<User> users;

    private Set<Course> courses;

    private Set<Course> traineeCourses;

    private Set<Topic> topics;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Set<UserProperty> userProperties) {
        this.username = username;
        this.password = password;
        this.userProperties = userProperties;
    }

    public User(String username, String password, boolean active, Set<UserProperty> userProperties) {

        this.username = username;
        this.password = password;
        this.active = active;
        this.userProperties = userProperties;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "USERNAME", unique = false, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PASSWORD", unique = false, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "ACTIVE", unique = false, nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user", cascade=CascadeType.ALL)
    public Set<UserProperty> getUserProperties() {
        return this.userProperties;
    }

    public void setUserProperties(Set<UserProperty> userProperties) {
        this.userProperties = userProperties;
    }

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "MANAGER_ID", nullable = true)
    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "manager")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "staff")
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "trainees")
    public Set<Course> getTraineeCourses() {
        return traineeCourses;
    }

    public void setTraineeCourses(Set<Course> traineeCourses) {
        this.traineeCourses = traineeCourses;
    }

    @OneToMany(mappedBy = "trainer")
    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }
}