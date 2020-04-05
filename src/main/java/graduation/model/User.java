package graduation.model;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class User extends AbstractNamedEntity {

    // TODO: 06.04.2020 add DB and jpa annotations etc.
    public static final String DELETE = "User.delete";
    public static final String BY_EMAIL = "User.getByEmail";
    public static final String ALL_SORTED = "User.getAllSorted";

    private String email;
    private String password;
    private Map<Restaurant, LocalDate> votes;

    private boolean enabled = true;
    private Date registered = new Date();
    private Set<Role> roles;

    public User() {
    }

    public User(User u) {
        this(u.getId(), u.getName(), u.getEmail(),  u.getPassword(), u.getVotes(), u.isEnabled(), u.getRegistered(), u.getRoles());
    }

    public User(Integer id, String name, String email,  String password, Map<Restaurant, LocalDate> votes, Role role, Role... roles) {
        this(id, name, email,  password, votes, true, new Date(), EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, Map<Restaurant, LocalDate> votes, boolean enabled, Date registered, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.votes = new ConcurrentHashMap<>();
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles.isEmpty() ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public Map<Restaurant, LocalDate> getVotes() {
        return votes;
    }

    public void setVotes(Restaurant restaurant, LocalDate date) {
        // TODO: 05.04.2020 add check time adding
        this.votes.put(restaurant, date);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email=" + email +
                ", name=" + name +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}