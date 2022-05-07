package hkmu.comps380f.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnlineUser implements Serializable {

    private String username;
    private String password;
    private List<String> roles = new ArrayList<>();

    public OnlineUser() {
    }

    public OnlineUser(String username, String password, String[] roles) {
        this.username = username;
        this.password = "{noop}" + password;
        this.roles = new ArrayList<>(Arrays.asList(roles));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
